package tn.esprit.tpfoyer;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class HolidayService {
    private final RestTemplate restTemplate;

    public List<Holiday> getPublicHolidays(int year, String countryCode) {
        String url = "https://date.nager.at/api/v3/PublicHolidays/" + year + "/" + countryCode;
        Holiday[] holidays = restTemplate.getForObject(url, Holiday[].class);
        return Arrays.asList(holidays);
    }
}
