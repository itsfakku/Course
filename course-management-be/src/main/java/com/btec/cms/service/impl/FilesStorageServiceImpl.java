package com.btec.cms.service.impl;

import com.btec.cms.service.FilesStorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {
  private final Path root = Paths.get("uploads");

  @Override
  public String save(MultipartFile file, String path) {

    final Path docsPath = Paths.get("uploads/docs");

    try {
      String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());

      String fileLast = path + "." + fileExtension;

      Files.copy(file.getInputStream(), docsPath.resolve(fileLast));

      return fileLast;
    } catch (Exception e) {
      throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
    }
  }

  @Override
  public Resource load(String filename) {
    final Path docsPath = Paths.get("uploads/docs");

    try {
      Path file = docsPath.resolve(filename);
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new RuntimeException("Could not read the file!");
      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("Error: " + e.getMessage());
    }
  }

  @Override
  public Stream<Path> loadAll() {
    try {
      return Files.walk(this.root, 2)
          .filter(path -> !path.equals(this.root))
          .map(this.root::relativize);
    } catch (IOException e) {
      throw new RuntimeException("Could not load the files!");
    }
  }
}
