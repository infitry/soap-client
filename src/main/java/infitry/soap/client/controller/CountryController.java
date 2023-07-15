package infitry.soap.client.controller;

import infitry.soap.client.service.CountryService;
import infitry.soap.client.wsdl.GetCountryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/country")
@RestController
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public GetCountryResponse getCountry(String countryName) {
        return countryService.getCountry(countryName);
    }
}
