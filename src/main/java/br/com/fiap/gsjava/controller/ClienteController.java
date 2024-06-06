package br.com.fiap.gsjava.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

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

import br.com.fiap.gsjava.model.Cliente;
import br.com.fiap.gsjava.repository.ClienteRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;


@RestController 
@RequestMapping("cliente")
@CacheConfig(cacheNames = "cliente")
@Tag(name = "cliente", description = "Endpoint relacionado com os clientes")
@Slf4j
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping
    @Cacheable
    @Operation(summary = "Listar todos os clientes cadastrados no sistema", description = "Endpoint que retorna um array de obejtos do tipo cliente com todos os dados dos clientes atuais")
    public List<Cliente> index(){
        return clienteRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @CacheEvict(allEntries = true)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Erro de validação de cliente"),
        @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso")
    })
    public Cliente create(@RequestBody @Valid Cliente cliente){
        log.info("Cliente cadastrado com sucesso: {}", cliente);
        return clienteRepository.save(cliente);
    }

    @GetMapping("{id}")
    public ResponseEntity<Cliente> get(@PathVariable Long id){
        log.info("Buscar por id: {}");
        return clienteRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    @CacheEvict(allEntries = true)
    public Cliente update(@PathVariable Long id, @RequestBody Cliente cliente) {
        log.info("atualizando cadastro de cliente id {} para {}", id, cliente );

        cliente.setId(id);
        return clienteRepository.save(cliente);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void destroy(@PathVariable Long id) {
        
        verificarRegistroExistenteCliente(id);
        clienteRepository.deleteById(id);


    }

    private void verificarRegistroExistenteCliente(Long id) {
        clienteRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }




}
