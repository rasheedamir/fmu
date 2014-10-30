package se.inera.fmu.infrastructure.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * @author andreaskaltenbach
 */
public class FakeAuthenticationToken extends AbstractAuthenticationToken {

    private FakeCredentials fakeCredentials;

    public FakeAuthenticationToken(FakeCredentials fakeCredentials) {
        super(null);
        this.fakeCredentials = fakeCredentials;
    }

    @Override
    public Object getCredentials() {
        return fakeCredentials;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
