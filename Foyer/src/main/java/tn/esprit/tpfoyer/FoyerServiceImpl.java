package tn.esprit.tpfoyer;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.service.DeepLTranslationService;
import tn.esprit.tpfoyer.service.EmailService;
import tn.esprit.tpfoyer.service.QrCodeService;

 import java.util.*;

@Service
@AllArgsConstructor
public class FoyerServiceImpl implements IFoyerService {

    private final FoyerRepository foyerRepository;
    private final DeepLTranslationService translationService;
    private final QrCodeService qrCodeService;

    @Override
    public List<Foyer> retrieveAllFoyers() {
        return foyerRepository.findAll();
    }

    @Override
    public Foyer retrieveFoyer(Long foyerId) {
        return foyerRepository.findById(foyerId)
                .orElseThrow(() -> new RuntimeException("Foyer not found"));
    }
    private final EmailService emailService;

    @Override
    public Foyer addFoyer(Foyer foyer) {
        Foyer savedFoyer = foyerRepository.save(foyer);

        try {
            String message = String.format(
                    "Un nouveau foyer a été créé:\n\nNom: %s\nCapacité: %d",
                    savedFoyer.getNomFoyer(),
                    savedFoyer.getCapaciteFoyer()
            );

            emailService.sendSimpleMessage(
                    "chourabiaziz007@gmail.com",
                    "Nouveau foyer créé - " + savedFoyer.getNomFoyer(),
                    message
            );
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi de l'email: " + e.getMessage());
        }

        return savedFoyer;
    }
    @Override
    public void removeFoyer(Long foyerId) {
        foyerRepository.deleteById(foyerId);
    }

    @Override
    public Foyer modifyFoyer(Foyer foyer) {
        if (!foyerRepository.existsById(foyer.getIdFoyer())) {
            throw new RuntimeException("Foyer not found");
        }
        return foyerRepository.save(foyer);
    }

    @Override
    public Page<Foyer> searchFoyers(String keyword, String sortBy, String sortDir, Pageable pageable) {
        Specification<Foyer> spec = (root, query, cb) -> {
            if (keyword == null || keyword.isEmpty()) {
                return cb.conjunction();
            }

            String likePattern = "%" + keyword.toLowerCase() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("nomFoyer")), likePattern),
                    cb.like(root.get("capaciteFoyer").as(String.class), likePattern)
            );
        };

        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                sort
        );

        return foyerRepository.findAll(spec, sortedPageable);
    }

    @Override
    public Map<String, Object> getFoyerDetails(Long id, String lang) {
        Foyer foyer = retrieveFoyer(id);

        String translatedNom = String.valueOf(translationService.translate(foyer.getNomFoyer(), lang));

        String qrData = String.format(
                "Foyer: %s | Capacité: %d",
                translatedNom,
                foyer.getCapaciteFoyer()
        );

        String qrCode = null;
        try {
            qrCode = qrCodeService.generateQrCode(qrData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Map<String, Object> details = new HashMap<>();
        details.put("foyer", foyer);
        details.put("translatedNom", translatedNom);
        details.put("qrCode", qrCode);

        return details;
    }
}