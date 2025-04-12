package tn.esprit.tpfoyer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import tn.esprit.tpfoyer.service.DeepLTranslationService;
import tn.esprit.tpfoyer.service.QrCodeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@AllArgsConstructor
@RequestMapping("/foyer")
@CrossOrigin(origins = "http://localhost:3000") // Ajoutez cette annotation

@Tag(name = "Gestion Foyer")
public class FoyerRestController {

    private final IFoyerService foyerService;
    private final DeepLTranslationService translationService;
    private final QrCodeService qrCodeService;

    @Operation(description = "Récupérer toutes les foyers")
    @GetMapping("/retrieve-all-foyer")
    public List<Foyer> getAllFoyer() {
        return foyerService.retrieveAllFoyers();
    }

    @Operation(description = "Récupérer une foyer par id")
    @GetMapping("/retrieve-foyer/{foyer-id}")
    public Foyer getFoyer(@PathVariable("foyer-id") Long foyerId) {
        return foyerService.retrieveFoyer(foyerId);
    }

    @Operation(description = "Ajouter une foyer")
    @PostMapping("/add-foyer")
    public Foyer addFoyer(@RequestBody Foyer foyer) {
        return foyerService.addFoyer(foyer);
    }

    @Operation(description = "Supprimer une foyer")
    @DeleteMapping("/remove-foyer/{foyer-id}")
    public void removeFoyer(@PathVariable("foyer-id") Long foyerId) {
        foyerService.removeFoyer(foyerId);
    }

    @Operation(description = "Modifier une foyer")
    @PutMapping("/modify-foyer")
    public Foyer modifyFoyer(@RequestBody Foyer foyer) {
        return foyerService.modifyFoyer(foyer);
    }

    @Operation(description = "Récupérer les détails complets avec traduction et QR code")
    @GetMapping("/{id}/details")
    public Mono<ResponseEntity<Map<String, Object>>> getFoyerDetails(
            @PathVariable Long id,
            @RequestParam(defaultValue = "FR") String lang) {

        return Mono.fromCallable(() -> {
                    Foyer foyer = foyerService.retrieveFoyer(id);
                    return foyer;
                })
                .flatMap(foyer -> translationService.translate(foyer.getNomFoyer(), lang)
                        .map(translatedNom -> {
                            try {
                                String qrData = String.format(
                                        "Nom: %s | Capacité: %d",
                                        translatedNom,
                                        foyer.getCapaciteFoyer()
                                );

                                String qrCode = qrCodeService.generateQrCode(qrData);

                                Map<String, Object> response = new HashMap<>();
                                response.put("foyer", foyer);
                                response.put("translatedNom", translatedNom);
                                response.put("qrCode", qrCode);

                                return ResponseEntity.ok(response);
                            } catch (Exception e) {
                                throw new RuntimeException("Failed to generate QR code", e);
                            }
                        })
                        .onErrorResume(e -> {
                            System.err.println("Error in getFoyerDetails: " + e.getMessage());
                            return Mono.just(ResponseEntity.internalServerError().build());
                        }));
    }

    @Operation(description = "Recherche et tri des foyers")
    @GetMapping("/search")
    public ResponseEntity<Page<Foyer>> searchFoyers(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "nomFoyer") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir,
            Pageable pageable) {

        Page<Foyer> result = foyerService.searchFoyers(keyword, sortBy, sortDir, pageable);
        return ResponseEntity.ok(result);
    }


}
