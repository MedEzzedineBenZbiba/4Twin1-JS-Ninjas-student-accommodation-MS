package tn.esprit.tpfoyer.control;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.service.IFoyerService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/foyer")
@Tag(name = "Gestion Foyer")
public class FoyerRestController {
    IFoyerService foyerService;


    @Operation(description = "récupérer toutes les foyers de la base de données")
    @GetMapping("retrieve-all-foyer")
    public List<Foyer> getAllFoyer() {
        return foyerService.retrieveAllFoyers();
    }

    @Operation(description = "récupérer une foyer par id ")
    @GetMapping("retrieve-foyer/{foyer-id}")
    public Foyer getFoyer(@PathVariable("foyer-id") Long foyerId){
        return foyerService.retrieveFoyer(foyerId);
    }

    @Operation(description = "Ajouter une foyer ")
    @PostMapping("/add-foyer")
    public Foyer addFoyer(@RequestBody Foyer foyer){
        return foyerService.addFoyer(foyer);
    }

    @Operation(description = "retirer une foyer de la base ")
    @DeleteMapping("/remove-foyer/{foyer-id}")
    public void removeFoyer(@PathVariable("foyer-id") Long foyerId) {
        foyerService.removeFoyer(foyerId);
    }

    @Operation(description = "mise à jour d'une foyer")
    @PutMapping("modify-foyer")
    public Foyer modifyFoyer(@RequestBody Foyer foyer) {
        return foyerService.modifyFoyer(foyer);
    }
}
