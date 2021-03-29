package com.sandeep.filestorage.entity.details;

import com.sandeep.filestorage.entity.details.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDetailsRepository  extends JpaRepository<FileDetails, String> {
    @Query(value = "SELECT * FROM FileDetails WHERE location1 = :name and replicaId = :id", nativeQuery = true)
    FileDetails findFileByNameAndReplicaId( @Param("name") String name, @Param("id") Integer id );

}
