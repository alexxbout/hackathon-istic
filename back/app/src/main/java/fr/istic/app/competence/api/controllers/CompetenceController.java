package fr.istic.app.competence.api.controllers;

import fr.istic.app.competence.api.dtos.CompetenceDto;
import fr.istic.app.competence.api.mappers.CompetenceDtoMapper;
import fr.istic.app.competence.domain.usecases.CreateCompetenceUseCase;
import fr.istic.app.competence.domain.usecases.DeleteCompetenceUseCase;
import fr.istic.app.competence.domain.usecases.ListCompetenceUseCase;
import fr.istic.app.rest.documentation.ApiResponseDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/competences", produces = "application/json", consumes = "application/json")
@RequiredArgsConstructor
@Tag(name = "Competences", description = "Operations about competences")
@ApiResponseDocumentation
public class CompetenceController {

    private final ListCompetenceUseCase listCompetenceUseCase;
    private final CompetenceDtoMapper competenceDtoMapper;
    private final CreateCompetenceUseCase createCompetenceUseCase;
    private final DeleteCompetenceUseCase deleteCompetenceUseCase;

    @GetMapping
    @Operation(summary = "Get all competences")
    @ApiResponseDocumentation
    public ResponseEntity<List<CompetenceDto>> getCompetences() {
        var found = this.listCompetenceUseCase.list();
        var mapped = this.competenceDtoMapper.toDto(found);
        return ResponseEntity.ok(mapped);
    }


    @PostMapping
    @Operation(summary = "Create a competence")
    @ApiResponseDocumentation
    public ResponseEntity<CompetenceDto> createCompetence(@RequestBody CreateCompetenceUseCase.CreateCompetenceDto competenceDto) {

        var created = this.createCompetenceUseCase.create(competenceDto);
        var mapped = this.competenceDtoMapper.toDto(created);
        return ResponseEntity.ok(mapped);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a competence by id")
    @ApiResponseDocumentation
    public ResponseEntity<Void> deleteCompetence(@PathVariable Long id) {

        this.deleteCompetenceUseCase.delete(id);
        return ResponseEntity.ok().build();
    }


}
