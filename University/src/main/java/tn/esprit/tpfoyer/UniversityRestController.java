package tn.esprit.tpfoyer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.Universite;
import tn.esprit.tpfoyer.IUniversiteService;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("university")


@Tag(name = "Gestion université")
public class UniversityRestController {
    IUniversiteService universiteService;

    @Operation(description = "récupérer toutes les universités de la base de données")
    @GetMapping("")
    public List<Universite> getAllUniversities(){
        return universiteService.retreiveAllUniversities();
    }

    @Operation(description = "récupérer une université par id ")
    @GetMapping("retrieve-university/{university-id}")
    public Universite getUniversite(@PathVariable("university-id") Long universityId){
        return universiteService.retrieveUniversity(universityId);
    }

    @Operation(description = "Ajouter une université")
    @PostMapping("")
    public Universite addUniversity(@RequestBody Universite universite){
        return universiteService.addUniversity(universite);
    }

    @Operation(description = "retirer une université par id")
    @DeleteMapping("{university-id}")
    public void removeUniversity(@PathVariable("university-id") Long univerisityId){
        universiteService.removeUniversity(univerisityId);
    }

    @Operation(description = "mise à jour d'une université")
    @PutMapping("")
    public Universite modifyUniversity(@RequestBody Universite universite){
        return universiteService.modifyUniversity(universite);
    }




    @GetMapping("/{nomUniversite}/foyer")
    public ResponseEntity<UniversiteWithFoyerDTO> getFoyerByUniversiteName(
            @PathVariable String nomUniversite
    ) {
        return ResponseEntity.ok(universiteService.getFoyerByNomUniversite(nomUniversite));
    }



    @GetMapping("/stats/adresse")
    public ResponseEntity<Map<String, Long>> getStatsByAdresse() {
        return ResponseEntity.ok(universiteService.getUniversitesCountByAdresse());
    }

}
