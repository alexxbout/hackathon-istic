package fr.istic.app.projet.api.controllers;


import fr.istic.app.projet.api.dtos.ProjetDto;
import fr.istic.app.projet.api.mappers.ProjetDtoMapper;
import fr.istic.app.projet.domain.model.Projet;
import fr.istic.app.projet.domain.usecases.CreateProjectUseCase;
import fr.istic.app.projet.domain.usecases.GetProjetsByUserMailUseCase;
import fr.istic.app.projet.domain.usecases.ListProjetsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/projets", produces = "application/json", consumes = "application/json")
@RequiredArgsConstructor
@Tag(name = "Projets", description = "Operations about projets")
public class ProjetController {

    private final ListProjetsUseCase listProjetsUseCase;
    private final CreateProjectUseCase createProjectUseCase;
    private final GetProjetsByUserMailUseCase getProjetsByUserMailUseCase;
    private final ProjetDtoMapper projetDtoMapper;

    @GetMapping
    @Operation(summary = "Get all projets; if userMail is provided, get projets by userMail")
    public ResponseEntity<List<ProjetDto>> getProjets(@RequestParam(required = false) String userMail) {

        List<Projet> projets;
        if (userMail == null) {
            projets = this.listProjetsUseCase.list();
        } else{
            projets = this.getProjetsByUserMailUseCase.getProjetsByUserMail(userMail);
        }

        var mapped = this.projetDtoMapper.toDto(projets);

        return ResponseEntity.ok(mapped);
    }

    @PostMapping
    @Operation(summary = "Create a projet")
    public ResponseEntity<ProjetDto> createProjet(@RequestBody CreateProjectUseCase.CreateProjetDto projetDto) {

        var created = this.createProjectUseCase.create(projetDto);
        var mapped = this.projetDtoMapper.toDto(created);
        return ResponseEntity.ok(mapped);
    }

}
