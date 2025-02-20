package fr.istic.app.pool.api.controllers;


import fr.istic.app.pool.api.dtos.PoolDto;
import fr.istic.app.pool.api.mappers.PoolDtoMapper;
import fr.istic.app.pool.domain.model.Pool;
import fr.istic.app.pool.domain.usecases.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/pools", produces = "application/json", consumes = "application/json")
@RequiredArgsConstructor
@Tag(name = "Pools", description = "Operations about pools")
public class PoolController {

    private final ListPoolUseCase listPoolUseCase;
    private final ListPoolByMailUseCase listPoolByMailUseCase;
    private final ReadPoolUseCase readPoolUseCase;
    private final CreatePoolUseCase createPoolUseCase;
    private final UpdatePoolUseCase updatePoolUseCase;
    private final DeletePoolUseCase deletePoolUseCase;

    private final PoolDtoMapper poolDtoMapper;

    @GetMapping
    @Operation(summary = "Get all pools; if userMail is provided, get pools by userMail")
    public ResponseEntity<List<PoolDto>> getPools(@PathVariable(required = false) String userMail) {

        List<Pool> pools;
        if (userMail == null) {
            pools = this.listPoolUseCase.list();
        } else{
            pools = this.listPoolByMailUseCase.list(userMail);
        }

        var mapped = this.poolDtoMapper.toDtos(pools);

        return ResponseEntity.ok(mapped);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a pool by id")
    public ResponseEntity<PoolDto> getPool(@PathVariable Long id) {

        var pool = this.readPoolUseCase.read(id);
        var mapped = this.poolDtoMapper.toDto(pool);

        return ResponseEntity.ok(mapped);
    }

    @PostMapping
    @Operation(summary = "Create a pool")
    public ResponseEntity<PoolDto> createPool(@RequestBody CreatePoolUseCase.CreatePoolUseCaseDto poolDto) {

        var created = this.createPoolUseCase.create(poolDto);
        var mapped = this.poolDtoMapper.toDto(created);

        return ResponseEntity.ok(mapped);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a pool by id")
    public ResponseEntity<PoolDto> updatePool(@PathVariable Long id, @RequestBody UpdatePoolUseCase.UpdatePoolUseCaseDto poolDto) {

        var updated = this.updatePoolUseCase.update(id, poolDto);
        var mapped = this.poolDtoMapper.toDto(updated);

        return ResponseEntity.ok(mapped);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a pool by id")
    public ResponseEntity<PoolDto> deletePool(@PathVariable Long id) {

        this.deletePoolUseCase.delete(id);

        return ResponseEntity.ok().build();
    }

}
