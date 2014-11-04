package se.inera.fmu.interfaces.managing.rest;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import se.inera.fmu.domain.model.authentication.Role;
import se.inera.fmu.domain.model.authentication.User;
import se.inera.fmu.infrastructure.security.FakeCredentials;
import se.inera.fmu.infrastructure.security.FmuUserDetails;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class for testing REST controllers.
 */
public class TestUtil {

    /** MediaType for JSON UTF8 */
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    /**
     * Convert an object to JSON byte array.
     *
     * @param object
     *            the object to convert
     * @return the JSON byte array
     * @throws IOException
     */
    public static byte[] convertObjectToJsonBytes(Object object)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
    
    /**
     * Log in with a fake user with predefined credentials
     */
    public static void loginWithNoActiveRole() {
		User landstingSamordnare = new User();
        landstingSamordnare.setFirstName("Åsa");
        List<Role> roles = new ArrayList<Role>();
        roles.add(Role.LANDSTINGSSAMORDNARE);
        roles.add(Role.UTREDARE);
		landstingSamordnare.setRoles(roles);
		landstingSamordnare.setMiddleAndLastName("Andersson");
		landstingSamordnare.setVardenhetHsaId("IFV1239877878-1049");
		landstingSamordnare.setHsaId("IFV1239877878-1042");
		FmuUserDetails details = new FmuUserDetails(landstingSamordnare);
		FakeCredentials credencial = new FakeCredentials("IFV1239877878-1042", "Åsa", "Andersson", true, "IFV1239877878-1049");
		Authentication authentication = new UsernamePasswordAuthenticationToken(details, credencial);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
