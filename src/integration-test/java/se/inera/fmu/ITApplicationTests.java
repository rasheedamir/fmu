package se.inera.fmu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.inera.fmu.Application;

// Like any other Spring based test, we need the SpringJUnit4ClassRunner so that an application context is created.
@RunWith(SpringJUnit4ClassRunner.class)
// The @SpringApplicationConfiguration annotation is similar to the @ContextConfiguration annotation in that it is used
// to specify which application context(s) that should be used in the test. Additionally, it will trigger logic for
// reading Spring Boot specific configurations, properties, and so on.
@SpringApplicationConfiguration(classes = Application.class)
// @WebAppConfiguration must be present in order to tell Spring that a WebApplicationContext should be loaded for the
// test. It also provides an attribute for specifying the path to the root of the web application.
@WebAppConfiguration
@ActiveProfiles("dev")
// @IntegrationTest is used to instruct Spring Boot that the embedded web server should be started. By providing
// colon- or equals-separated name-value pair(s), any environment variable can be overridden. In this example, the
// "server.port:0" will override the server’s default port setting. Normally, the server would start using the specified
// port number, but the value 0 has a special meaning. When specified, it tells Spring Boot to scan the ports on the
// host environment and start the server on a random, available port. That is useful if you have different services
// occupying different ports on the development machines and the build server that could potentially collide with t
// he application port, in which case the application will not start. Secondly, if you create multiple integration
// tests with different application contexts, they may also collide if the tests are running concurrently.
@IntegrationTest("server.port:0")
public class ITApplicationTests {

	@Test
	public void contextLoads() {
		System.out.println("passed");
	}

}
