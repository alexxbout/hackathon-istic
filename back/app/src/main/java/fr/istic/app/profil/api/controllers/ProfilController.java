package fr.istic.app.profil.api.controllers;

import fr.istic.app.profil.api.dtos.ProfilDto;
import fr.istic.app.profil.api.mappers.ProfilDtoMapper;
import fr.istic.app.profil.domain.usecases.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/profils")
@RequiredArgsConstructor
@Tag(name = "Profils", description = "Operations about profil")
public class ProfilController {

    private final CreateProfilDtoUseCase createProfilDtoUseCase;
    private final ListProfilsUseCase listProfilsUseCase;
    private final ReadProfilUseCase readProfilUseCase;
    private final UpdateProfilUseCase updateProfilDtoUseCase;
    private final DeleteProfilUseCase deleteProfilUseCase;
    private final SearchProfilUseCase searchProfilUseCase;
    private final ProfilDtoMapper profilDtoMapper;
    private final ReadCVUseCase readCVUseCase;
    private final CreateCVUseCase createCVUseCase;

    @GetMapping(produces = "application/json")
    @Operation(summary = "Get all profils")
    public ResponseEntity<List<ProfilDto>> getProfils() {

        var found = this.listProfilsUseCase.list();
        var mapped = this.profilDtoMapper.toDto(found);

        return ResponseEntity.ok(mapped);
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    @Operation(summary = "Get a profil by id")
    public ResponseEntity<ProfilDto> getProfil(@PathVariable Long id) {

        var found = this.readProfilUseCase.read(id);
        var mapped = this.profilDtoMapper.toDto(found);

        return ResponseEntity.ok(mapped);
    }

    @GetMapping(path = "/{id}/cv", produces = MediaType.APPLICATION_PDF_VALUE)
    @Operation(summary = "Get a profil's cv by id")
    public ResponseEntity<byte[]> getProfilCv(@PathVariable Long id) {
        var cv = this.readCVUseCase.read(id);

        return ResponseEntity.ok().body(cv.getFileData());
    }

    @PostMapping(path = "/{id}/cv", consumes = MediaType.APPLICATION_PDF_VALUE)
    @Operation(summary = "Create a profil's cv by id")
    public ResponseEntity<Map<String, Long>> createProfilCv(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {

        var created = this.createCVUseCase.create(file, id);

        Map<String, Long> response = Map.of("id", created.getId());

        return ResponseEntity.ok(response);
    }


    @PostMapping(consumes = "application/json", produces = "application/json")
    @Operation(summary = "Create a profil")
    public ResponseEntity<ProfilDto> createProfil(@RequestBody CreateProfilDtoUseCase.CreateProfilDto dto) {

        var created = this.createProfilDtoUseCase.create(dto);
        var mapped = this.profilDtoMapper.toDto(created);

        return ResponseEntity.ok(mapped);
    }

    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Update a profil by id")
    public ResponseEntity<ProfilDto> updateProfil(@PathVariable Long id, @RequestBody UpdateProfilUseCase.UpdateProfilUseCaseDto profilDto) {
        var updated = this.updateProfilDtoUseCase.update(id, profilDto);
        var mapped = this.profilDtoMapper.toDto(updated);
        return ResponseEntity.ok(mapped);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a profil by id")
    public ResponseEntity<Void> deleteProfil(@PathVariable Long id) {

        this.deleteProfilUseCase.delete(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping(path = "/search", produces = "application/json")
    @Operation(summary = "Search a profil by competence, experience, date_debut, date_fin")
    public ResponseEntity<List<ProfilDto>> searchProfil(@RequestParam(required = false) List<Long> competence, @RequestParam(required = false) Integer experience, @RequestParam(required = false) LocalDate date_debut, @RequestParam(required = false) LocalDate date_fin) {

        var found = this.searchProfilUseCase.search(competence, experience, date_debut, date_fin);
        var mapped = this.profilDtoMapper.toDto(found);
        return ResponseEntity.ok(mapped);
    }


}
