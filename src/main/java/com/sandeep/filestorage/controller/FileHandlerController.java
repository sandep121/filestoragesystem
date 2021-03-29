package com.sandeep.filestorage.controller;


import com.sandeep.filestorage.entity.entity.FileEntity;
import com.sandeep.filestorage.entity.FileEntityParent;
import com.sandeep.filestorage.entity.entity2.FileEntity2;
import com.sandeep.filestorage.service.DBFileStorageService;
import com.sandeep.filestorage.util.UserFileWithKey;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/fileVault")
@Api
public class FileHandlerController {

    private DBFileStorageService dbFileStorageService;

    public FileHandlerController()
    {}

    @Autowired
    public FileHandlerController(DBFileStorageService dbFileStorageService)
    {
        this.dbFileStorageService=dbFileStorageService;
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/uploadFile")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file, @RequestParam(required = false) Integer key) {
        Integer originalKey=dbFileStorageService.storeFile(file,key);
        if(key == null)
            return ResponseEntity.ok().body("your generated key is "+ originalKey + "\n. Please keep it safe it is non-recoverable!!!");
        else
            return ResponseEntity.ok().body("file added securely");
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileId) {
        // Load file from database
        FileEntity2 dbFile = dbFileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/fetchFile/{fileName}")
    public ResponseEntity<ByteArrayResource> fetchFile(@PathVariable String fileName, @RequestParam Integer key) {
        // Load file from database
        FileEntityParent dbFile = dbFileStorageService.fetchFile(fileName, key);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getFileIdList/")
    public ResponseEntity getFileIdList() {
        // Load file from database
//        FileEntity dbFile = dbFileStorageService.getFile(fileId);

        List fileList = dbFileStorageService.getFileIdList();
        return ResponseEntity.ok()
                .body(fileList);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getFileNameList/")
    public ResponseEntity getFileNameList() {
        // Load file from database
//        FileEntity dbFile = dbFileStorageService.getFile(fileId);

        List fileList = dbFileStorageService.getFileNameList();
        return ResponseEntity.ok()
                .body(fileList);
    }


}
