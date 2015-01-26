package se.inera.fmu.config;

import javax.xml.datatype.DatatypeConfigurationException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import se.inera.fmu.interfaces.managing.ws.BestallareClient;

/**
 * Created by Rasheed on 10/25/14.
 */
@EnableWs
@Configuration
@Slf4j
public class WebServiceConfiguration extends WsConfigurerAdapter implements EnvironmentAware {

    private RelaxedPropertyResolver propertyResolver;

    private Environment environment;

    /**
     * Sets the environment
     * @param environment
     */
    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        this.propertyResolver = new RelaxedPropertyResolver(environment);
    }

    /**
     *
     * @param applicationContext
     * @return
     */
    @Bean
    public ServletRegistrationBean webServiceDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    /**
     *
     * @param eavropSchema
     * @return
     */
    @Bean(name = "eavrop-ws")
    public DefaultWsdl11Definition eavropWsdl11Definition(XsdSchema eavropSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("EavropsPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://inera.ws/fmu/admin/eavrop");
        wsdl11Definition.setSchema(eavropSchema);
        log.info("Registered fmu webservice");
        return wsdl11Definition;
    }

    /**
     *
     * @return
     */
    @Bean
    public XsdSchema eavropSchema() {
        return new SimpleXsdSchema(new ClassPathResource("ws/eavrop.xsd"));
    }

    /**
     * The marshaller is pointed at the collection of generated domain objects and will use them to
     * both serialize and deserialize between XML and POJOs.
     * @return
     */
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("fk.wsdl");
        return marshaller;
    }

    /**
     *
     * @param marshaller
     * @return
     */
    @Bean
    public BestallareClient bestallareClient(Jaxb2Marshaller marshaller) {
        BestallareClient bestallareClient = null;
        
        try {
			bestallareClient = new BestallareClient();
		
	        String fkWsdlUri = propertyResolver.getProperty("fk.wsdl.uri");
	        bestallareClient.setDefaultUri(fkWsdlUri);
	        bestallareClient.setMarshaller(marshaller);
	        bestallareClient.setUnmarshaller(marshaller);
        } catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}
        return bestallareClient;
    }

}
