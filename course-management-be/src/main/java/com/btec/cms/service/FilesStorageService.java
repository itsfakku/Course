package com.btec.cms.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FilesStorageService {

  String save(MultipartFile file, String path);

  Resource load(String filename);

  Stream<Path> loadAll();
}
