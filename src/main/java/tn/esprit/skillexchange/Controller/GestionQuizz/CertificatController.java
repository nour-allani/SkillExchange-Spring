package tn.esprit.skillexchange.Controller.GestionQuizz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tn.esprit.skillexchange.Entity.GestionQuiz.Certificat;
import tn.esprit.skillexchange.Repository.GestionQuiz.CertificatRepo;
import tn.esprit.skillexchange.Service.Gestionquizz.ICertificatService;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/certificats")
public class CertificatController {

    private final ICertificatService certificatService;
    private final CertificatRepo certificatRepository;

    @Autowired
    public CertificatController(ICertificatService certificatService,
                                CertificatRepo certificatRepository) {
        this.certificatService = certificatService;
        this.certificatRepository = certificatRepository;
    }

    @GetMapping
    public List<Certificat> getAllCertificats() {
        return certificatService.getAllCertificats();
    }

    @GetMapping("/{id}/details")
    public Certificat getCertificatById(@PathVariable Long id) {
        return certificatRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Certificate not found with id: " + id));
    }

    @GetMapping("/{id}/view")
    public ResponseEntity<String> viewCertificateHtml(@PathVariable Long id) {
        Certificat cert = certificatService.getCertificatById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Certificate not found"));

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(cert.getHtmlContent());
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadCertificate(
            @PathVariable Long id,
            @RequestParam String format) {

        Certificat cert = certificatService.getCertificatById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Certificate not found"));

        byte[] content;
        String mediaType;
        String filename;

        switch (format.toLowerCase()) {
            case "pdf":
                content = Base64.getDecoder().decode(cert.getPdfContent());
                mediaType = MediaType.APPLICATION_PDF_VALUE;
                filename = "certificate.pdf";
                break;
            case "jpg":
            case "jpeg":
                content = Base64.getDecoder().decode(cert.getJpgContent());
                mediaType = MediaType.IMAGE_JPEG_VALUE;
                filename = "certificate.jpg";
                break;
            default:
                throw new IllegalArgumentException("Invalid format. Supported formats: pdf, jpg");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mediaType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + filename + "\"")
                .body(content);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Certificat createCertificat(@RequestBody Certificat certificat) {
        return certificatService.createCertificat(certificat);
    }

    @PutMapping("/{id}")
    public Certificat updateCertificat(@PathVariable Long id,
                                       @RequestBody Certificat certificat) {
        return certificatService.updateCertificat(id, certificat);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCertificat(@PathVariable Long id) {
        certificatService.deleteCertificat(id);
    }

    // Exception handler for this controller
    @ExceptionHandler({RuntimeException.class, IllegalArgumentException.class})
    public ResponseEntity<String> handleExceptions(Exception ex) {
        if (ex instanceof IllegalArgumentException) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}