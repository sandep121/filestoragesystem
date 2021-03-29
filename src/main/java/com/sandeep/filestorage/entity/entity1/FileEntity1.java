package com.sandeep.filestorage.entity.entity1;

import com.sandeep.filestorage.entity.entity2.FileEntity2;
import com.sandeep.filestorage.entity.FileEntityParent;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class FileEntity1  extends FileEntityParent {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column
    private String fileName;

    @Column
    private String fileType;

    @Lob
    private byte[] data;


    public FileEntity1() {
        super();
    }

    public FileEntity1(String id, String fileName, String fileType, byte[] data) {
        super(id, fileName, fileType, data);
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

    public FileEntity1(String fileName, String fileType, byte[] data) {
        super(fileName, fileType, data);
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getFileType() {
        return fileType;
    }

    @Override
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public byte[] getData() {
        return data;
    }

    @Override
    public void setData(byte[] data) {
        this.data = data;
    }

    public FileEntity1(FileEntity1 fileEntity1) {
        super(fileEntity1);
    }

    public FileEntity1(FileEntity2 fileEntity1) {
        super(fileEntity1);
    }
}
