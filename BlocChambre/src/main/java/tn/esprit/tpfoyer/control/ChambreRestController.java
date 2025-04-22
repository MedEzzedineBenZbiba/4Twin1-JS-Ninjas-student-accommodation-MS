package tn.esprit.tpfoyer.control;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.service.IChambreService;

import java.util.List;

@RestController
@AllArgsConstructor

@RequestMapping("/chambre")
@Tag(name = "Gestion Chambre")
public class ChambreRestController {
    IChambreService chambreService;

    @Operation(description = "récupérer toutes les chambres de la base de données")
    @GetMapping("")
    public List<Chambre> getChambres() {
        List<Chambre> listChambres = chambreService.retrieveAllChambres();
        return listChambres;
    }
    @GetMapping("{chambre-id}")
    @Operation(description = "récupérer la chambre par id ")
    public Chambre retrieveChambre(@PathVariable("chambre-id") Long chId) {
        Chambre chambre = chambreService.retrieveChambreById(chId);
        return chambre;
    }
    @PostMapping("")
    @Operation(description = "Ajouter une chambre dans la base de données")
    public Chambre addChambre(@RequestBody Chambre c) {
        Chambre chambre = chambreService.addChambre(c);
        return chambre;
    }
    @DeleteMapping("{chambre-id}")
    @Operation(description = "retirer une chambre par id")
    public void removeChambre(@PathVariable("chambre-id") Long chId) {

        chambreService.removeChambre(chId);
    }
    @PutMapping("")
    @Operation(description = "mise à jour une chambre de la base de données")
    public Chambre modifyChambre(@RequestBody Chambre c) {
        Chambre chambre = chambreService.modifyChambre(c);
        return chambre;
    }

    @GetMapping("/getChambreByTypeChambre/{chambreType}")
    public List<Chambre> getChambresByTypeChambre(@PathVariable("chambreType") TypeChambre typeC) {
        return chambreService.retriveAllChambresByTypeChambre(typeC);
    }

    @GetMapping("/getChambreByNum/{chambreNum}")
    public Chambre getChambresByTypeChambre(@PathVariable("chambreNum") Long numC) {
        return chambreService.retrieveChambreByNumberChambre(numC);
    }

}
