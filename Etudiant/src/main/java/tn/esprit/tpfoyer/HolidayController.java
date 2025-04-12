package tn.esprit.tpfoyer;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/holidays")
@AllArgsConstructor
public class HolidayController {

    private final HolidayService holidayService;

    @GetMapping("/{year}/{countryCode}")
    public List<Holiday> getHolidays(@PathVariable int year, @PathVariable String countryCode) {
        return holidayService.getPublicHolidays(year, countryCode);
    }
}
