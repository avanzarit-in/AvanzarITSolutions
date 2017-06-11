package com.avanzarit.apps.gst.storage;

/**
 * Created by SPADHI on 5/16/2017.
 */


import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    String store(String type,MultipartFile file);

    String store(String type, String path, MultipartFile file);

    String store(String type, String path, String fileName, MultipartFile file);

    String store(String type, String path, String fileName) throws IOException;

    String store(String type,String fileName) throws IOException;

    Stream<Path> loadAll();

    Path load(String type, String path, String filename);

    Path load(String type, String filename);

    Resource loadAsResource(String type, String path, String filename);

    File loadAsFile(String type, String path);

    Resource loadAsResource(String type, String filename);

    void deleteAll(String type, String path);

}

