package br.com.fiap.motos.resource;

import br.com.fiap.motos.dto.request.TipoVeiculoRequest;
import br.com.fiap.motos.dto.response.AcessorioResponse;
import br.com.fiap.motos.dto.response.TipoVeiculoResponse;
import br.com.fiap.motos.entity.Acessorio;
import br.com.fiap.motos.entity.TipoVeiculo;
import br.com.fiap.motos.service.TipoVeiculoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;

@RestController
@RequestMapping(value = "/tipo-veiculo")
public class TipoVeiculoResource implements ResourceDTO<TipoVeiculo, TipoVeiculoRequest, TipoVeiculoResponse>{

    @Autowired
    private TipoVeiculoService service;

    @GetMapping
    public ResponseEntity<Collection<TipoVeiculoResponse>> findAll(
            @RequestParam(name = "nome", required = false) String nome
    ){
        TipoVeiculo tipo = TipoVeiculo.builder()
                .nome(nome)
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains());


        Example<TipoVeiculo> example = Example.of(tipo, matcher);
        var encontrados = service.findAll(example);

        if(encontrados.isEmpty()) return ResponseEntity.notFound().build();

        var resposta = encontrados.stream()
                .map( service::toResponse )
                .toList();

        return ResponseEntity.ok( resposta );

    }

    @GetMapping(value = "{id}")
    @Override
    public ResponseEntity<TipoVeiculoResponse> findById(@PathVariable Long id) {
        var encontrado = service.findById( id );
        if (encontrado == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( encontrado );
        return ResponseEntity.ok( resposta );
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<TipoVeiculoResponse> save(@RequestBody @Valid TipoVeiculoRequest r) {
        var entity = service.toEntity( r );
        var saved = service.save( entity );
        var resposta = service.toResponse( saved );

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path( "/{id}" )
                .buildAndExpand( saved.getId() )
                .toUri();

        return ResponseEntity.created( uri ).body( resposta );
    }
}
