package com.btec.cms.api;

import com.btec.cms.model.FileInfo;
import com.btec.cms.model.ResponseMessage;
import com.btec.cms.service.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
public class FilesUploadApi {

  final FilesStorageService storageService;

  @Autowired
  public FilesUploadApi(FilesStorageService storageService) {
    this.storageService = storageService;
  }

  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
    String message;
    try {
      String path = storageService.save(file, "fileName");

      System.out.println("file name: " + path);

      message = "Uploaded the file successfully: " + file.getOriginalFilename();

      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
      message = "Could not upload the file: " + file.getOriginalFilename() + e;
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
          .body(new ResponseMessage(message));
    }
  }

  @GetMapping("/files")
  public ResponseEntity<List<FileInfo>> getListFiles() {
    List<FileInfo> fileInfos =
        storageService
            .loadAll()
            .map(
                path -> {
                  String filename = path.getFileName().toString();
                  String url =
                      MvcUriComponentsBuilder.fromMethodName(
                              FilesUploadApi.class, "getFile", path.getFileName().toString())
                          .build()
                          .toString();

                  return new FileInfo(filename, url);
                })
            .collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
  }

  /**
   * Get file by name <br>
   * Link: <code>/files/{filename}</code> <br>
   * Method: GET
   *
   * @param filename name of file
   * @return file found
   */
  @GetMapping("/files/{filename:.+}")
  @ResponseBody
  public ResponseEntity<Resource> getFile(@PathVariable String filename) {
    Resource file = storageService.load(filename);
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"");
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType("application/pdf"))
        .headers(headers)
        .body(file);
  }
}
