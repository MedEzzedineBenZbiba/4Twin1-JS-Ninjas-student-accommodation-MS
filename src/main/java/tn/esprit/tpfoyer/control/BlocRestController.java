package tn.esprit.tpfoyer.control;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.service.IBlocService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Gestion Bloc")
@RequestMapping("/bloc")
public class BlocRestController {
    IBlocService blocService;

    @PutMapping("desaffectBlocFromFoyer/{blocId}")
    public Bloc desaffectBlocFromFoyer(@PathVariable("blocId") Long blocId ) {
        return blocService.desacffectBlocFromFoyer(blocId);
    }
    @GetMapping("BlocsSansFoyer")
    public List<Bloc> getBlocsSansFoyer() {
        return blocService.retrieveBlocsSansFoyer();
    }

    @PutMapping("affectBlocToFoyer/{foyerId}/{blocId}")
    public Bloc affectBlocToFoyer(@PathVariable("foyerId") Long foyerId, @PathVariable("blocId") Long blocId ) {
        return blocService.AffectBlocToFoyer(foyerId, blocId);
    }

    // if parent exist => Put Else Post
    @PostMapping("AddBlocAndFoyerAndAffect")
    public Bloc AddBlocAndFoyer(@RequestBody Bloc bloc){
        return blocService.AddBlocAndFoyerWithAffectation(bloc);
    }

    @GetMapping("retrieve-all-blocs")
    @Operation(description = "récupérer toutes les blocs de la base de données")
    public List<Bloc> getAllChambres(){
        return blocService.retrieveAllBlocs();
    }

    @GetMapping("/retrieve-bloc/{bloc-id}")
    @Operation(description = "récupérer le bloc par id ")
    public Bloc getBlocById(@PathVariable("bloc-id") Long blocId){
        return blocService.retrieveBloc(blocId);
    }

    @Operation(description = "Ajouter une bloc")
    @PostMapping("/add-bloc")
    public Bloc addBloc(@RequestBody Bloc bloc){
        return blocService.addBloc(bloc);
    }

    @Operation(description = "retirer une bloc par id ")
    @DeleteMapping("/remove-bloc/{bloc-id}")
    public void removeChambre(@PathVariable("bloc-id") Long blocId){
        blocService.removeBloc(blocId);
    }

    @Operation(description = "mise à jour une base de donnée")
    @PutMapping("/modify-bloc")
    public Bloc modifyBloc(@RequestBody Bloc bloc){
        return blocService.modifyBloc(bloc);
    }
}
