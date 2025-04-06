package tn.esprit.tpfoyer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "foyer-service", url = "http://localhost:8085")
public interface FoyerClient {
    @GetMapping("/foyer/{foyerId}")
    FoyerDTO getFoyerById(@PathVariable Long foyerId);
}
