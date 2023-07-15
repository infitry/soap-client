package infitry.soap.client.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

@Configuration
public class CountryConfiguration {
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("infitry.soap.client.wsdl");
        return marshaller;
    }

    @Bean
    public WebServiceTemplate countryWebServiceTemplate(Jaxb2Marshaller marshaller) {
        var webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMessageSender(createMessageSender());
        webServiceTemplate.setMarshaller(marshaller);
        webServiceTemplate.setUnmarshaller(marshaller);
        return webServiceTemplate;
    }

    private HttpComponentsMessageSender createMessageSender() {
        var httpComponentsMessageSender = new HttpComponentsMessageSender();
        httpComponentsMessageSender.setConnectionTimeout(10000);
        httpComponentsMessageSender.setReadTimeout(60000);
        httpComponentsMessageSender.setAcceptGzipEncoding(true);
        httpComponentsMessageSender.setMaxTotalConnections(10);
        return httpComponentsMessageSender;
    }
}