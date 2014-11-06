package se.inera.fmu.infrastructure.security;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static se.inera.fmu.domain.model.authentication.SakerhetstjanstAssertion.ENHET_HSA_ID_ATTRIBUTE;
import static se.inera.fmu.domain.model.authentication.SakerhetstjanstAssertion.ENHET_HSA_ID_ATTRIBUTE;
import static se.inera.fmu.domain.model.authentication.SakerhetstjanstAssertion.FORNAMN_ATTRIBUTE;
import static se.inera.fmu.domain.model.authentication.SakerhetstjanstAssertion.HSA_ID_ATTRIBUTE;
import static se.inera.fmu.domain.model.authentication.SakerhetstjanstAssertion.MEDARBETARUPPDRAG_TYPE;
import static se.inera.fmu.domain.model.authentication.SakerhetstjanstAssertion.MEDARBETARUPPDRAG_ID;
import static se.inera.fmu.domain.model.authentication.SakerhetstjanstAssertion.MELLAN_OCH_EFTERNAMN_ATTRIBUTE;
import static se.inera.fmu.domain.model.authentication.SakerhetstjanstAssertion.TITEL_ATTRIBUTE;
import static se.inera.fmu.domain.model.authentication.SakerhetstjanstAssertion.VARD_OCH_BEHANDLING;

import org.joda.time.DateTime;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.AuthnContext;
import org.opensaml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.impl.AssertionBuilder;
import org.opensaml.saml2.core.impl.AttributeBuilder;
import org.opensaml.saml2.core.impl.AttributeStatementBuilder;
import org.opensaml.saml2.core.impl.AuthnContextBuilder;
import org.opensaml.saml2.core.impl.AuthnContextClassRefBuilder;
import org.opensaml.saml2.core.impl.AuthnStatementBuilder;
import org.opensaml.saml2.core.impl.NameIDBuilder;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.schema.impl.XSStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.providers.ExpiringUsernameAuthenticationToken;
import org.springframework.security.saml.SAMLAuthenticationProvider;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author andreaskaltenbach
 */
public class FakeAuthenticationProvider  implements AuthenticationProvider {

    private static DocumentBuilder documentBuilder;
    private boolean forcePrincipalAsString = false;
    
    private SAMLUserDetailsService userDetails;

    static {
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Failed to instantiate DocumentBuilder", e);
        }
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        FakeAuthenticationToken token = (FakeAuthenticationToken) authentication;

        SAMLCredential credential = createSamlCredential(token);

        Object user = userDetails.loadUserBySAML(credential);
        Object principal = getPrincipal(credential, user);
        Collection<? extends GrantedAuthority> entitlements = getEntitlements(credential, user);

        Date expiration = getExpirationDate(credential);
        
        ExpiringUsernameAuthenticationToken result = new ExpiringUsernameAuthenticationToken(expiration, principal,
                credential, entitlements);
        result.setDetails(user);

        return result;
    }
    
    protected Collection<? extends GrantedAuthority> getEntitlements(SAMLCredential credential, Object userDetail) {
        if (userDetail instanceof UserDetails) {
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.addAll(((UserDetails) userDetail).getAuthorities());
            return authorities;
        } else {
            return Collections.emptyList();
        }
    }
    
    protected Date getExpirationDate(SAMLCredential credential) {
        List<AuthnStatement> statementList = credential.getAuthenticationAssertion().getAuthnStatements();
        DateTime expiration = null;
        for (AuthnStatement statement : statementList) {
            DateTime newExpiration = statement.getSessionNotOnOrAfter();
            if (newExpiration != null) {
                if (expiration == null || expiration.isAfter(newExpiration)) {
                    expiration = newExpiration;
                }
            }
        }
        return expiration != null ? expiration.toDate() : null;
    }

    
    protected Object getPrincipal(SAMLCredential credential, Object userDetail) {
        if (isForcePrincipalAsString()) {
            return credential.getNameID().getValue();
        } else if (userDetail != null) {
            return userDetail;
        } else {
            return credential.getNameID();
        }
    }
    
    public boolean isForcePrincipalAsString() {
        return forcePrincipalAsString;
    }

    @Override
    public boolean supports(Class authentication) {
        return FakeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private SAMLCredential createSamlCredential(FakeAuthenticationToken token) {
        FakeCredentials fakeCredentials = (FakeCredentials) token.getCredentials();

        Assertion assertion = new AssertionBuilder().buildObject();
        AttributeStatement attributeStatement = new AttributeStatementBuilder().buildObject();
        assertion.getAttributeStatements().add(attributeStatement);

        attributeStatement.getAttributes().add(createAttribute(HSA_ID_ATTRIBUTE, fakeCredentials.getHsaId()));
        attributeStatement.getAttributes().add(createAttribute(FORNAMN_ATTRIBUTE, fakeCredentials.getFornamn()));
        attributeStatement.getAttributes().add(createAttribute(MELLAN_OCH_EFTERNAMN_ATTRIBUTE, fakeCredentials.getEfternamn()));
        attributeStatement.getAttributes().add(createAttribute(ENHET_HSA_ID_ATTRIBUTE, fakeCredentials.getEnhetId()));

        if (fakeCredentials.isLakare()) {
            attributeStatement.getAttributes().add(createAttribute(TITEL_ATTRIBUTE, "Läkare"));
        }

        NameID nameId = new NameIDBuilder().buildObject();
        nameId.setValue(token.getCredentials().toString());
        return new SAMLCredential(nameId, assertion, "fake-idp", "webcert");
    }

    private Attribute createAttribute(String name, String value) {

        Attribute attribute = new AttributeBuilder().buildObject();
        attribute.setName(name);

        Document doc = documentBuilder.newDocument();
        Element element = doc.createElement("element");
        element.setTextContent(value);

        XMLObject xmlObject = new XSStringBuilder().buildObject(new QName("ns", "local"));
        xmlObject.setDOM(element);
        attribute.getAttributeValues().add(xmlObject);

        return attribute;
    }
    public void setUserDetails(SAMLUserDetailsService userDetails) {
        this.userDetails = userDetails;
    }
}
