package com.sandeep.filestorage.entity;

import com.sandeep.filestorage.entity.entity.FileEntity;
import com.sandeep.filestorage.entity.entity1.FileEntity1;
import com.sandeep.filestorage.entity.entity2.FileEntity2;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

public class FileEntityParent {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String fileName;

    private String fileType;

    @Lob
    private byte[] data;

    public FileEntityParent() {

    }

    public String getId() {
        return id;
    }

    public FileEntityParent(String id, String fileName, String fileType, byte[] data) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public FileEntityParent(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

    public FileEntityParent(FileEntity1 fileEntity1)
    {
        this.id = fileEntity1.getId();
        this.fileName = fileEntity1.getFileName();
        this.fileType = fileEntity1.getFileType();
        this.data = fileEntity1.getData();
    }

    public FileEntityParent(FileEntity2 fileEntity1)
    {
        this.id = fileEntity1.getId();
        this.fileName = fileEntity1.getFileName();
        this.fileType = fileEntity1.getFileType();
        this.data = fileEntity1.getData();
    }

    public FileEntityParent(FileEntity fileEntity1)
    {
        this.id = fileEntity1.getId();
        this.fileName = fileEntity1.getFileName();
        this.fileType = fileEntity1.getFileType();
        this.data = fileEntity1.getData();
    }

}
