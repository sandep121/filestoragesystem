package com.sandeep.filestorage.entity.details;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collections;

@Entity
public class FileDetails {
    @Id
    @GeneratedValue
    Integer id;

    @Column
    Integer replicaId;

    @Column
    String location1;

    @Column
    Integer encryptionKey;

    @Column
    String filename;

    @Column
    String fileId;

    public FileDetails() {
    }

    public FileDetails(Integer id, Integer replicaId, String location1, Integer encryptionKey, String filename, String fileId) {
        this.id = id;
        this.replicaId = replicaId;
        this.location1 = location1;
        this.encryptionKey = encryptionKey;
        this.filename = filename;
        this.fileId = fileId;
    }

    public FileDetails(Integer replicaId, String location1, Integer encryptionKey, String filename, String fileId) {
        this.replicaId = replicaId;
        this.location1 = location1;
        this.encryptionKey = encryptionKey;
        this.filename = filename;
        this.fileId = fileId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReplicaId() {
        return replicaId;
    }

    public void setReplicaId(Integer replicaId) {
        this.replicaId = replicaId;
    }

    public String getLocation1() {
        return location1;
    }

    public void setLocation1(String location1) {
        this.location1 = location1;
    }

    public Integer getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(Integer encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
