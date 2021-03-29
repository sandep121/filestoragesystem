package com.sandeep.filestorage.entity.entity1;

import com.sandeep.filestorage.entity.entity2.FileEntity2;
import com.sandeep.filestorage.entity.FileEntityParent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepo2  extends JpaRepository<FileEntity1, String> {
    List<FileEntityParent> findAllByFileName(String fileName);
}
