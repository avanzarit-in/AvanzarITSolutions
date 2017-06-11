package com.avanzarit.apps.gst.storage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path uploadLocation;
    private final Path exportLocation;
    private final Path downloadLocation;
    private final Path batchLogLocation;
    private final Path attachmentLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.uploadLocation = Paths.get(properties.getUploadLocation());
        this.exportLocation = Paths.get(properties.getExportLocation());
        this.downloadLocation = Paths.get(properties.getDownloadLocation());
        this.batchLogLocation = Paths.get(properties.getBatchjobLogLocation());
        this.attachmentLocation = Paths.get(properties.getAttachmentLocation());
    }

    private Path getPath(String type) {
        Path path = null;

        switch (type) {
            case "upload":
                path = this.uploadLocation;
                break;
            case "export":
                path = this.exportLocation;
                break;
            case "download":
                path = this.downloadLocation;
                break;
            case "batchlog":
                path = this.batchLogLocation;
                break;
            case "attachment":
                path = this.attachmentLocation;
                break;
        }
        return path;
    }

    @Override
    public String store(String type, String path, MultipartFile file) {
        return store(type, path, null, file);
    }

    @Override
    public String store(String type, MultipartFile file) {
        return store(type, null, null, file);
    }

    @Override
    public String store(String type, String path, String fileName, MultipartFile file) {

        String originalFileName = file.getOriginalFilename();

        if (fileName != null) {
            originalFileName = fileName + originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
        }
        Path location = getPath(type);

        try {
            if (path != null) {
                location = Paths.get(location + "/" + path);
                Files.createDirectories(location);
            }
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + originalFileName);
            }
            try {
                Files.delete(location.resolve(originalFileName));

            } catch (NoSuchFileException exception) {

            }
            Files.copy(file.getInputStream(), location.resolve(originalFileName));

        } catch (IOException e) {
            throw new StorageException("Failed to store file " + originalFileName, e);
        }
        return "";
    }

    @Override
    public String store(String type, String fileName) throws IOException {
        return store(type, null, fileName);
    }


    @Override
    public String store(String type, String path, String fileName) throws IOException {

        Path location = getPath(type);
        try {
            if (path != null) {
                Files.createDirectories(location);
            }
            Files.delete(location.resolve(fileName));
            Files.createFile(location.resolve(fileName));
        } catch (IOException exception) {
            Files.createFile(location.resolve(fileName));
        }

        return location.toString() + "/" + fileName;
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
    public Path load(String type, String fileName) {
        return load(type, null, fileName);
    }

    @Override
    public Path load(String type, String path, String fileName) {
        Path location = getPath(type);
        if (path != null) {
            location = Paths.get(location + "/" + path);
        }
        return location.resolve(fileName);
    }

    @Override
    public Resource loadAsResource(String type, String fileName) {
        return loadAsResource(type, null, fileName);
    }

    @Override
    public File loadAsFile(String type, String path) {
        Path filePath = load(type, path);
        if (filePath != null) {
            List<String> files = fileList(filePath);
            if (files != null && !files.isEmpty()) {
                filePath = Paths.get(files.get(0));
            }
            return new File(filePath.toUri());
        }
        return null;
    }

    public static List<String> fileList(Path directory) {
        List<String> fileNames = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory)) {
            for (Path path : directoryStream) {
                fileNames.add(path.toString());
            }
        } catch (IOException ex) {
        }
        return fileNames;
    }

    @Override
    public Resource loadAsResource(String type, String path, String fileName) {
        try {
            Path file = load(type, path, fileName);

            if (file != null) {
                Resource resource = new UrlResource(file.toUri());
                if (resource.exists() || resource.isReadable()) {
                    return resource;
                } else {
                    throw new StorageFileNotFoundException("Could not read file: " + fileName);
                }
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + fileName, e);
        }
        return null;
    }

    @Override
    public void deleteAll(String type, String path) {
        Path pathToFolder = getPath(type);
        pathToFolder = Paths.get(pathToFolder + "/" + path);
        FileSystemUtils.deleteRecursively(pathToFolder.toFile());
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
            try {
                Files.createDirectory(attachmentLocation);
            } catch (Exception e) {

            }
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
