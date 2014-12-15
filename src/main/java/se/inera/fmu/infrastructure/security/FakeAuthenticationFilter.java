package se.inera.fmu.infrastructure.security;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author andreaskaltenbach
 */
@Slf4j
public class FakeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    protected FakeAuthenticationFilter() {
        super("/fake");
        log.error("FakeAuthentication enabled. DO NOT USE IN PRODUCTION");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding("UTF-8");
        }
        String parameter = request.getParameter("userJsonDisplay");
        // we manually encode the json parameter
        String json = URLDecoder.decode(parameter, "UTF-8");

        try {
            FakeCredentials fakeCredentials = new ObjectMapper().readValue(json, FakeCredentials.class);
            log.info("Detected fake credentials " + fakeCredentials);
            return getAuthenticationManager().authenticate(new FakeAuthenticationToken(fakeCredentials));
        } catch (IOException e) {
            String message = "Failed to parse JSON: " + json;
            log.error(message, e);
            throw new RuntimeException(message, e);
        }
    }
}
