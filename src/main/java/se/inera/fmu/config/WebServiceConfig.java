package se.inera.fmu.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
public class WebServiceConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean webServiceDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

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

    @Bean
    public BestallareClient bestallareClient(Jaxb2Marshaller marshaller) {
        BestallareClient bestallareClient = new BestallareClient();
        // ToDo: This must be read from resources
        bestallareClient.setDefaultUri("http://localhost:9090/ws/bestallare-ws");
        bestallareClient.setMarshaller(marshaller);
        bestallareClient.setUnmarshaller(marshaller);
        return bestallareClient;
    }

}
