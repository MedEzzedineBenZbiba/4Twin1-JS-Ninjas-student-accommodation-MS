package tn.esprit.tpfoyer.control;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.dto.WeatherResponse;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.service.IBlocService;
import tn.esprit.tpfoyer.service.WeatherService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Tag(name = "Gestion Bloc")
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/bloc")
public class BlocRestController {
    private final IBlocService blocService;
    private final WeatherService weatherService;


    public BlocRestController(IBlocService blocService,WeatherService weatherService) {
        this.blocService = blocService;
        this.weatherService= weatherService;
    }

    @GetMapping("/{id}/meteo")
    public ResponseEntity<?> getBlocWeather(@PathVariable Long id) {
        Optional<Bloc> blocOpt = Optional.ofNullable(blocService.retrieveBloc(id));
        if (blocOpt.isPresent()) {
            Bloc bloc = blocOpt.get();
            WeatherResponse weather = weatherService.getWeather(bloc.getLatitude(), bloc.getLongitude());

            Map<String, Object> result = new HashMap<>();
            result.put("lieu", weather.getName());
            result.put("température", weather.getMain().getTemp() + " °C");
            result.put("humidité", weather.getMain().getHumidity() + " %");
            result.put("description", weather.getWeather().get(0).getDescription());

            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bloc non trouvé");
        }
    }

    @GetMapping("/retrieve-all-blocs")
    @Operation(summary = "Récupérer toutes les blocs de la base de données")
    public ResponseEntity<List<Bloc>> getAllBlocs() {
        return ResponseEntity.ok(blocService.retrieveAllBlocs());
    }

    @GetMapping("/retrieve-bloc/{blocId}")
    @Operation(summary = "Récupérer un bloc par son ID")
    public ResponseEntity<Bloc> getBlocById(@PathVariable Long blocId) {
        Bloc bloc = blocService.retrieveBloc(blocId);
        return ResponseEntity.ok(bloc);
    }

    @PostMapping("/add-bloc")
    @Operation(summary = "Ajouter un nouveau bloc")
    public ResponseEntity<Bloc> addBloc(@RequestBody Bloc bloc) {
        Bloc newBloc = blocService.addBloc(bloc);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBloc);
    }

    @DeleteMapping("/remove-bloc/{blocId}")
    @Operation(summary = "Supprimer un bloc par son ID")
    public ResponseEntity<Void> removeBloc(@PathVariable Long blocId) {
        blocService.removeBloc(blocId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/modify-bloc")
    @Operation(summary = "Mettre à jour un bloc existant")
    public ResponseEntity<Bloc> modifyBloc(@RequestBody Bloc bloc) {
        Bloc updatedBloc = blocService.modifyBloc(bloc);
        return ResponseEntity.ok(updatedBloc);
    }

    @GetMapping("/find-by-capacite-minimum/{capaciteMin}")
    @Operation(summary = "Récupérer les blocs par capacité minimale")
    public ResponseEntity<List<Bloc>> findBlocsByCapaciteMinimum(
            @PathVariable Long capaciteMin) {
        if (capaciteMin < 0) {
            throw new IllegalArgumentException("La capacité minimale ne peut pas être négative");
        }
        List<Bloc> blocs = blocService.findBlocsByCapaciteMinimum(capaciteMin);
        return ResponseEntity.ok(blocs);
    }

}