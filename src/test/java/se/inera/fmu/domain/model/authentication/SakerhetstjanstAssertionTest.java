package se.inera.fmu.domain.model.authentication;

import javax.xml.transform.stream.StreamSource;

import static org.junit.Assert.assertEquals;

import org.apache.cxf.helpers.XMLUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opensaml.DefaultBootstrap;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallerFactory;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;


/**
 * A POJO carrying an assertions attributes
 * @author andreaskaltenbach
 */
public class SakerhetstjanstAssertionTest {

    private static Assertion assertionWithEnhet;
    private static Assertion assertionWithoutEnhet;
    private static Assertion newAssertion;

    @BeforeClass
    public static void readSamlAssertions() throws Exception {
        DefaultBootstrap.bootstrap();

        UnmarshallerFactory unmarshallerFactory = Configuration.getUnmarshallerFactory();
        Unmarshaller unmarshaller = unmarshallerFactory.getUnmarshaller(Assertion.DEFAULT_ELEMENT_NAME);
        
        Document doc = (Document) XMLUtils.fromSource(new StreamSource(new ClassPathResource(
                "SakerhetstjanstAssertionTest/saml-assertion-with-enhet.xml").getInputStream()));
        assertionWithEnhet = (Assertion) unmarshaller.unmarshall(doc.getDocumentElement());

        doc = (Document) XMLUtils.fromSource(new StreamSource(new ClassPathResource(
                "SakerhetstjanstAssertionTest/saml-assertion-without-enhet.xml").getInputStream()));
        assertionWithoutEnhet = (Assertion) unmarshaller.unmarshall(doc.getDocumentElement());
        
        doc = (Document) XMLUtils.fromSource(new StreamSource(new ClassPathResource(
                "SakerhetstjanstAssertionTest/new-assertion.xml").getInputStream()));
        newAssertion = (Assertion) unmarshaller.unmarshall(doc.getDocumentElement());        
    }

    @Test
    public void testAssertionWithEnhetAndVardgivare() {

        SakerhetstjanstAssertion assertion = new SakerhetstjanstAssertion(assertionWithEnhet);

        assertEquals("204010", assertion.getTitelKod());
        assertEquals("8787878", assertion.getForskrivarkod());
        assertEquals("TST5565594230-106J", assertion.getHsaId());
        assertEquals("Markus", assertion.getFornamn());
        assertEquals("Gran", assertion.getMellanOchEfternamn());
        assertEquals("IFV1239877878-103H", assertion.getEnhetHsaId());
        assertEquals("VårdEnhet2A", assertion.getEnhetNamn());
        assertEquals("IFV1239877878-0001", assertion.getVardgivareHsaId());
        assertEquals("IFV Testdata", assertion.getVardgivareNamn());
    }
    
    @Test
    public void testNewAssertion() {

        SakerhetstjanstAssertion assertion = new SakerhetstjanstAssertion(newAssertion);
        
        System.out.println(assertion.getFornamn());
    }  

    @Test
    public void testAssertionWithoutEnhetAndVardgivare() {

        SakerhetstjanstAssertion assertion = new SakerhetstjanstAssertion(assertionWithoutEnhet);

        assertEquals("T_SERVICES_SE165565594230-106X", assertion.getHsaId());
    }
}
