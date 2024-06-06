package br.com.fiap.gsjava.controller;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import br.com.fiap.gsjava.model.Coral;
import br.com.fiap.gsjava.repository.CoralRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("coral")
@CacheConfig(cacheNames = "coral")
@Tag(name = "coral", description = "Endpoint relacionados com corais")
@Slf4j
public class CoralController {

    @Autowired
    CoralRepository coralRepository;

    @GetMapping
    @Cacheable
    @Operation(summary = "Listar todos os corais cadastrados no sistema", description = "Endpoint que retorna um array do tipo coral com todos os corias dacastrados atuaias")
    public List<Coral> index() {
        return coralRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @CacheEvict(allEntries = true)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Erro de validação de coral"),
        @ApiResponse(responseCode = "201", description = "Coral cadastrado com sucesso")
    })
    public Coral create(@RequestBody @Valid Coral coral) {
        log.info("cadastro de coral: {}", coral);
        return coralRepository.save(coral);
    }

    @GetMapping("{id}")
    public ResponseEntity<Coral> get(@PathVariable Long id) {
        log.info("Buscar por id: {}", id);

        return coralRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void destroy(@PathVariable Long id) {
        log.info("apagando registro de coral");

        verificarRegistroExistenteCoral(id);
        coralRepository.deleteById(id);
    }

    @PutMapping("{id}")
    @CacheEvict(allEntries = true)
    public Coral update(@PathVariable Long id, @RequestBody Coral coral) {
        log.info("atualizando o coral id {} para {}", id, coral);
        
        verificarRegistroExistenteCoral(id);

        coral.setId(id);
        return coralRepository.save(coral);
    }

    private void verificarRegistroExistenteCoral(Long id) {
        coralRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coral não encontrado"));
    }



}
