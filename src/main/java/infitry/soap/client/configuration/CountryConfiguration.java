package infitry.soap.client.configuration;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import java.util.concurrent.TimeUnit;

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
        httpComponentsMessageSender.setHttpClient(createHttpClient(createHttpClientConnectionManager()));

        return httpComponentsMessageSender;
    }

    private PoolingHttpClientConnectionManager createHttpClientConnectionManager() {
        var poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setMaxTotal(20);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(2);

        return poolingHttpClientConnectionManager;
    }

    private CloseableHttpClient createHttpClient(PoolingHttpClientConnectionManager poolingHttpClientConnectionManager) {
        return HttpClients.custom()
                .addInterceptorFirst(new HttpComponentsMessageSender.RemoveSoapHeadersInterceptor())
                .setConnectionManager(poolingHttpClientConnectionManager)
                .evictExpiredConnections()
                .evictIdleConnections(40, TimeUnit.SECONDS)
                .build();
    }
}