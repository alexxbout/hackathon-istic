package fr.istic.app.profil.api.controllers;

import fr.istic.app.profil.api.dtos.ProfilDto;
import fr.istic.app.profil.api.mappers.ProfilDtoMapper;
import fr.istic.app.profil.domain.usecases.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/profils", produces = "application/json", consumes = "application/json")
@RequiredArgsConstructor
@Tag(name = "Profils", description = "Operations about profil")
public class ProfilController {

    private final CreateProfilDtoUseCase createProfilDtoUseCase;
    private final ListProfilsUseCase listProfilsUseCase;
    private final ReadProfilUseCase readProfilUseCase;
    private final UpdateProfilUseCase updateProfilDtoUseCase;
    private final DeleteProfilUseCase deleteProfilUseCase;
    private final ProfilDtoMapper profilDtoMapper;

    @GetMapping
    @Operation(summary = "Get all profils")
    public ResponseEntity<List<ProfilDto>> getProfils() {

        var found = this.listProfilsUseCase.list();
        var mapped = this.profilDtoMapper.toDto(found);

        return ResponseEntity.ok(mapped);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a profil by id")
    public ResponseEntity<ProfilDto> getProfil(@PathVariable Long id) {

        var found = this.readProfilUseCase.read(id);
        var mapped = this.profilDtoMapper.toDto(found);

        return ResponseEntity.ok(mapped);
    }

    @PostMapping
    @Operation(summary = "Create a profil")
    public ResponseEntity<ProfilDto> createProfil(@RequestBody CreateProfilDtoUseCase.CreateProfilDto dto) {

        var created = this.createProfilDtoUseCase.create(dto);
        var mapped = this.profilDtoMapper.toDto(created);

        return ResponseEntity.ok(mapped);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a profil by id")
    public ResponseEntity<ProfilDto> updateProfil(@PathVariable Long id, @RequestBody UpdateProfilUseCase.UpdateProfilUseCaseDto profilDto) {
        var updated = this.updateProfilDtoUseCase.update(id, profilDto);
        var mapped = this.profilDtoMapper.toDto(updated);
        return ResponseEntity.ok(mapped);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a profil by id")
    public ResponseEntity<ProfilDto> deleteProfil(@PathVariable Long id) {

        this.deleteProfilUseCase.delete(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/search")
    @Operation(summary = "Search a profil by competence, experience, date_debut, date_fin")
    public ResponseEntity<List<ProfilDto>> searchProfil(@PathVariable List<Long> competence, @PathVariable Integer experience, @PathVariable String date_debut, @PathVariable String date_fin) {
        //TODO
        return ResponseEntity.status(204).build();
    }


}
