package br.com.fiap.motos.service;

import br.com.fiap.motos.entity.Foto;
import br.com.fiap.motos.repository.FotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FotoService {

    private final String IMAGEM_FOLDER = System.getProperty("user.dir") + "file_server";

    @Autowired
    private FotoRepository repo;

    public Foto save(Foto foto, MultipartFile imagem){
        Foto saved = null;
        if(upload(imagem, foto)) saved = repo.save(foto);
        return saved;
    }

    public boolean upload(MultipartFile file, Foto foto) {

        if(file.isEmpty()) throw new RuntimeException("Empty file");

        Path destino = Paths.get(IMAGEM_FOLDER)
                .resolve(foto.getSrc())
                .normalize()
                .toAbsolutePath();

        try {
            Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

}
