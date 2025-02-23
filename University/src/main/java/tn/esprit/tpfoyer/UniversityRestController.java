package tn.esprit.tpfoyer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.Universite;
import tn.esprit.tpfoyer.IUniversiteService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("university")
@Tag(name = "Gestion université")
public class UniversityRestController {
    IUniversiteService universiteService;

    @Operation(description = "récupérer toutes les universités de la base de données")
    @GetMapping("retrieve-all-universities")
    public List<Universite> getAllUniversities(){
        return universiteService.retreiveAllUniversities();
    }

    @Operation(description = "récupérer une université par id ")
    @GetMapping("retrieve-university/{university-id}")
    public Universite getUniversite(@PathVariable("university-id") Long universityId){
        return universiteService.retrieveUniversity(universityId);
    }

    @Operation(description = "Ajouter une université")
    @PostMapping("add-university")
    public Universite addUniversity(@RequestBody Universite universite){
        return universiteService.addUniversity(universite);
    }

    @Operation(description = "retirer une université par id")
    @DeleteMapping("delete-university/{university-id}")
    public void removeUniversity(@PathVariable("university-id") Long univerisityId){
        universiteService.removeUniversity(univerisityId);
    }

    @Operation(description = "mise à jour d'une université")
    @PutMapping("modify-university")
    public Universite modifyUniversity(@RequestBody Universite universite){
        return universiteService.modifyUniversity(universite);
    }

}
