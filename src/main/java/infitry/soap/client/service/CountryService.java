package infitry.soap.client.service;

import infitry.soap.client.wsdl.GetCountryRequest;
import infitry.soap.client.wsdl.GetCountryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

@Service
public class CountryService {

    private final WebServiceTemplate countryWebServiceTemplate;
    private static final Logger log = LoggerFactory.getLogger(CountryService.class);

    @Autowired
    public CountryService(WebServiceTemplate countryWebServiceTemplate) {
        this.countryWebServiceTemplate = countryWebServiceTemplate;
    }

    public GetCountryResponse getCountry(String country) {
        GetCountryRequest request = new GetCountryRequest();
        request.setName(country);

        log.info("Requesting location for " + country);

        return (GetCountryResponse) countryWebServiceTemplate
                .marshalSendAndReceive("http://localhost:8080/ws/countries", request,
                        new SoapActionCallback("http://infitry.com/web-service"));
    }

}
