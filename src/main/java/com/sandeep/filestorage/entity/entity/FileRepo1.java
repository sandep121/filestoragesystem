package com.sandeep.filestorage.entity.entity;

import com.sandeep.filestorage.entity.entity1.FileEntity1;
import com.sandeep.filestorage.entity.FileEntityParent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepo1  extends JpaRepository<FileEntity, String> {
    List<FileEntityParent> findAllByFileName(String fileName);
}
