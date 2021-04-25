package com.sandeep.filestorage.controller;

//import com.costrategix.s3demo.config.s3.MinioAdapter;
import com.sandeep.filestorage.service.MinioAdapter;
import io.minio.messages.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class MinioStorageController {
    @Autowired
    MinioAdapter minioAdapter;

    @GetMapping(path = "/buckets")
    public List<Bucket> listBuckets() {
        return minioAdapter.getAllBuckets();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam(required = false) Integer key) throws IOException {
//        minioAdapter.uploadFile(file.getOriginalFilename(), file.getBytes());
//        Map<String, String> result = new HashMap<>();
//        result.put("key", file.getOriginalFilename());
//        return result;
        minioAdapter.uploadFile(file);
//        if(key == null)
//            return ResponseEntity.ok().body("your generated key is "+ originalKey + "\n. Please keep it safe it is non-recoverable!!!");
//        else
//            return ResponseEntity.ok().body("file added securely");
        return ResponseEntity.ok().body("file added securely");
    }

    @GetMapping(path = "/download")
    public ResponseEntity<ByteArrayResource> uploadFile(@RequestParam(value = "file") String file) throws IOException {
        byte[] data = minioAdapter.getFile(file);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + file + "\"")
                .body(resource);

    }
}
