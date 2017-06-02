package com.avanzarit.apps.gst.storage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path uploadLocation;
    private final Path exportLocation;
    private final Path downloadLocation;
    private final Path batchLogLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.uploadLocation = Paths.get(properties.getUploadLocation());
        this.exportLocation = Paths.get(properties.getExportLocation());
        this.downloadLocation = Paths.get(properties.getDownloadLocation());
        this.batchLogLocation=Paths.get(properties.getBatchjobLogLocation());

    }

    private Path getPath(String type){
        switch(type){
            case "upload":
                return this.uploadLocation;
            case "export":
                return this.exportLocation;
            case "download":
                return this.downloadLocation;
            case "batchlog":
                return this.batchLogLocation;

        }
        return null;
    }

    @Override
    public String store(String type,MultipartFile file) {
        Path location=getPath(type);
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            try {
                Files.delete(location.resolve(file.getOriginalFilename()));
            } catch (NoSuchFileException exception) {

            }
            Files.copy(file.getInputStream(), location.resolve(file.getOriginalFilename()));

        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
        return "";
    }

    @Override
    public String store(String type,String fileName) throws IOException {
        Path location=getPath(type);
        try {
            Files.delete(location.resolve(fileName));

        } catch (IOException exception) {
            Files.createFile(location.resolve(fileName));
        }

        return "";
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.uploadLocation, 1)
                    .filter(path -> !path.equals(this.uploadLocation))
                    .map(path -> this.uploadLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String type, String filename) {
        Path location=getPath(type);
        return location.resolve(filename);
    }


    @Override
    public Resource loadAsResource(String type, String filename) {
        try {
            Path file = load(type, filename);
            if (file != null) {
                Resource resource = new UrlResource(file.toUri());
                if (resource.exists() || resource.isReadable()) {
                    return resource;
                } else {
                    throw new StorageFileNotFoundException("Could not read file: " + filename);
                }
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
        return null;
    }

    @PostConstruct
    @Override
    public void init() {
        try {
            FileSystemUtils.deleteRecursively(uploadLocation.toFile());
            FileSystemUtils.deleteRecursively(exportLocation.toFile());
            FileSystemUtils.deleteRecursively(downloadLocation.toFile());
            FileSystemUtils.deleteRecursively(batchLogLocation.toFile());
            Files.createDirectory(uploadLocation);
            Files.createDirectory(exportLocation);
            Files.createDirectory(downloadLocation);
            Files.createDirectory(batchLogLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
