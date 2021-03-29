package com.sandeep.filestorage.entity.entity2;

import com.sandeep.filestorage.entity.entity.FileEntity;
import com.sandeep.filestorage.entity.FileEntityParent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepo3 extends JpaRepository<FileEntity2, String> {
    List<FileEntityParent> findAllByFileName(String fileName);
}

