package br.com.fiap.motos.resource;

import br.com.fiap.motos.dto.request.FabricanteRequest;
import br.com.fiap.motos.dto.response.AcessorioResponse;
import br.com.fiap.motos.dto.response.FabricanteResponse;
import br.com.fiap.motos.entity.Fabricante;
import br.com.fiap.motos.entity.Foto;
import br.com.fiap.motos.repository.FabricanteRepository;
import br.com.fiap.motos.service.FabricanteService;
import br.com.fiap.motos.service.FotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping(value = "/fabricante")
public class FabricanteResource implements ResourceDTO<Fabricante, FabricanteRequest, FabricanteResponse>{

    @Autowired
    private FabricanteService service;
    @Autowired
    private FotoService fotoService;

    @GetMapping
    public ResponseEntity<Collection<FabricanteResponse>> findAll(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "nomeFantasia", required = false) String nomeFantasia
    ){
        Fabricante fabricante = Fabricante.builder()
                .nome(nome)
                .nomeFantasia(nomeFantasia)
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("nomeFantasia", ExampleMatcher.GenericPropertyMatchers.contains());

        Example<Fabricante> example = Example.of(fabricante, matcher);
        var encontrados = service.findAll(example);

        if(encontrados.isEmpty()) return ResponseEntity.notFound().build();

        var resposta = encontrados.stream()
                .map( service::toResponse )
                .toList();

        return ResponseEntity.ok( resposta );
    }

    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<FabricanteResponse> findById(@PathVariable Long id) {
        var encontrado = service.findById( id );
        if (encontrado == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( encontrado );
        return ResponseEntity.ok( resposta );
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<FabricanteResponse> save(@RequestBody @Valid FabricanteRequest r) {
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

    @Transactional
    @PostMapping(value = "/{id}/fotos")
    public ResponseEntity<?> upload(@RequestPart("foto")MultipartFile file, @PathVariable Long id){

        var entity = service.findById(id);

        if (Objects.isNull(entity)) return ResponseEntity.notFound().build();

        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        Foto foto = Foto.builder()
                .src(entity.getClass()
                        .getSimpleName()
                        .toLowerCase()
                            + "_" + entity.getId()
                            +"_"+ UUID.randomUUID().toString()
                            +"."+extension
                )
                .build();
        if (!fotoService.upload(file, foto)) return ResponseEntity.badRequest().build();

        entity.setLogo(foto);
        var response = service.toResponse(entity);
        var uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(entity.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }
}
