package se.inera.fmu.config;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 * Created by Rasheed on 10/25/14.
 */

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    /*@Bean
    public ServletRegistrationBean dispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }*/

    @Bean(name = "eavrop-ws")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema eavropSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("EavropsPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://inera.ws/fmu/admin/eavrop");
        wsdl11Definition.setSchema(eavropSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema eavropSchema() {
        return new SimpleXsdSchema(new ClassPathResource("ws/eavrop.xsd"));
    }
}
