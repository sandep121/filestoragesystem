package com.sandeep.filestorage.service;

import com.sandeep.filestorage.entity.details.FileDetails;
import com.sandeep.filestorage.entity.entity.FileEntity;
import com.sandeep.filestorage.entity.entity1.FileEntity1;
import com.sandeep.filestorage.entity.entity2.FileEntity2;
import com.sandeep.filestorage.entity.FileEntityParent;
import com.sandeep.filestorage.exception.FileNotFoundException;
import com.sandeep.filestorage.exception.FileStorageException;
import com.sandeep.filestorage.entity.details.FileDetailsRepository;
import com.sandeep.filestorage.entity.entity.FileRepo1;
import com.sandeep.filestorage.entity.entity1.FileRepo2;
import com.sandeep.filestorage.entity.entity2.FileRepo3;
import com.sandeep.filestorage.exception.InvalidKeyException;
import com.sandeep.filestorage.util.Decryption;
import com.sandeep.filestorage.util.DividedEncryptedFIle;
import com.sandeep.filestorage.util.EncryptedFiles;
import com.sandeep.filestorage.util.Encryption;
import com.sandeep.filestorage.util.UserFileWithKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class DBFileStorageService {
    public DBFileStorageService() {
    }

    @Autowired
    public DBFileStorageService(FileRepo3 dbFileRepo3, FileRepo1 fileRepo1, FileRepo2 fileRepo2, FileDetailsRepository fileDetailsRepository) {
        this.dbFileRepo3 = dbFileRepo3;
        this.fileRepo1 = fileRepo1;
        this.fileRepo2 = fileRepo2;
        this.fileDetailsRepository = fileDetailsRepository;
    }

    private FileRepo3 dbFileRepo3;
    private FileRepo1 fileRepo1;
    private FileRepo2 fileRepo2;
    private FileDetailsRepository fileDetailsRepository;

    private FileDetails getFileLocation(String fileName, Integer partNumber, Integer replica)
    {
        Integer replicaId = partNumber*10+replica;
        FileDetails fileDetails=fileDetailsRepository.findFileByNameAndReplicaId(fileName, replicaId);
        if(fileDetails==null)
        {
            throw new FileNotFoundException("File " + fileName + " not found!!!");
        }
        return fileDetails;
    }

    private FileEntity2 getFileEntity(String id)
    {
        try
        {
            return dbFileRepo3.findAllById(Collections.singleton(id)).get(0);
        } catch (Exception e) {
            System.out.println("not found FileEntity " + id);
        }
        return null;
    }

    private FileEntity getFileEntity1(String id)
    {
        try
        {
            return fileRepo1.findAllById(Collections.singleton(id)).get(0);
        } catch (Exception e) {
            System.out.println("not found FileEntity1 " + id);
        }
        return null;
    }

    private FileEntity1 getFileEntity2(String id)
    {
        try
        {
            return fileRepo2.findAllById(Collections.singleton(id)).get(0);
        } catch (Exception e) {
            System.out.println("not found FileEntity2 " + id);
        }
        return null;
    }

    private FileEntityParent getFilePart(String fileName, Integer partNumber)
    {

        FileDetails fileDetails = getFileLocation(fileName, partNumber, 1);
        if(partNumber == 1)
        {
            FileEntity2 fileEntity = getFileEntity(fileDetails.getFileId());
            if(fileEntity != null)
            {
                return new FileEntityParent(fileEntity);
            }
            else
            {
                fileDetails = getFileLocation(fileName, partNumber, 2);
                FileEntity fileEntity1 = getFileEntity1(fileDetails.getFileId());
                return new FileEntityParent(fileEntity1);
            }
        }
        else if( partNumber == 2 )
        {
            FileEntity fileEntity1 = getFileEntity1(fileDetails.getFileId());
            if(fileEntity1 != null)
            {
                return new FileEntityParent(fileEntity1);
            }
            else
            {
                fileDetails = getFileLocation(fileName, partNumber, 2);
                FileEntity1 fileEntity2 = getFileEntity2(fileDetails.getFileId());
                return new FileEntityParent(fileEntity2);
            }
        }
        else
        {
            FileEntity2 fileEntity = getFileEntity(fileDetails.getFileId());
            if(fileEntity != null)
            {
                return new FileEntityParent(fileEntity);
            }
            else
            {
                fileDetails = getFileLocation(fileName, partNumber, 2);
                FileEntity1 fileEntity2 = getFileEntity2(fileDetails.getFileId());
                return new FileEntityParent(fileEntity2);
            }
        }
    }


    public FileEntityParent fetchFile(String fileName, Integer key1)
    {
        Integer key2 = fileDetailsRepository.findFileByNameAndReplicaId(fileName,11).getEncryptionKey();
        if(!isKeyPrime(key1) || key2 == null)
        {
            throw new FileStorageException("File "+ fileName + " not found  Please try again!", null);
        }

        FileEntityParent fileEntityParent = getFilePart(fileName, 1);

        DividedEncryptedFIle dividedEncryptedFIle = new DividedEncryptedFIle(key1,key2,
                fileEntityParent.getData(),
                getFilePart(fileName, 2).getData(),
                getFilePart(fileName, 3).getData());
        Decryption decryption = new Decryption();
        fileEntityParent.setData(decryption.decrypt(dividedEncryptedFIle));
        return fileEntityParent;
    }



    public Integer storeFile(MultipartFile file, Integer key1) {
        // Normalize file name
        if(key1!=null && (key1 < 17000 || key1 > 30000 || !this.isKeyPrime(key1)))
            throw new InvalidKeyException("Sorry you entered a wrong key");
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String contentType=file.getContentType();
        if(fileName.contains("..")) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
        }
        Encryption encryption = new Encryption();
        DividedEncryptedFIle dividedEncryptedFIle;
        try
        {
             dividedEncryptedFIle=encryption.encrypt(new UserFileWithKey((key1==null)?0:key1,file.getBytes()));

        }
        catch (Exception e)
        {
            throw new FileStorageException("File not supported");
        }

        key1=dividedEncryptedFIle.getKey1();
        Integer key = dividedEncryptedFIle.getKey2();
        try {
                FileEntity2 dbFile = new FileEntity2(fileName, contentType, dividedEncryptedFIle.getByteArrPart1());
                dbFileRepo3.save(dbFile);
                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/downloadFile/")
                        .path(dbFile.getId().toString())
                        .toUriString();
                FileDetails fileDetailsReplica1 = new FileDetails(11, fileName, key, fileDownloadUri, dbFile.getId());
                fileDetailsRepository.save(fileDetailsReplica1);
                FileEntity fileEntity1 = new FileEntity(fileName, contentType, dividedEncryptedFIle.getByteArrPart1());
                fileRepo1.save(fileEntity1);
                String fileDownloadUri1 = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/downloadFile/")
                        .path(fileEntity1.getId().toString())
                        .toUriString();
                FileDetails fileDetailsReplica2 = new FileDetails(12, fileName, key, fileDownloadUri1, fileEntity1.getId());
                fileDetailsRepository.save(fileDetailsReplica2);



                FileEntity dbFile1 = new FileEntity(fileName, contentType, dividedEncryptedFIle.getByteArrPart2());
                fileRepo1.save(dbFile1);
                fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/downloadFile/")
                        .path(dbFile1.getId().toString())
                        .toUriString();
                fileDetailsReplica1 = new FileDetails(21,  fileName, key, fileDownloadUri, dbFile1.getId());
                fileDetailsRepository.save(fileDetailsReplica1);
                FileEntity1 fileEntity2 = new FileEntity1(fileName, contentType, dividedEncryptedFIle.getByteArrPart2());
                fileRepo2.save(fileEntity2);
                fileDownloadUri1 = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/downloadFile/")
                        .path(fileEntity2.getId().toString())
                        .toUriString();
                fileDetailsReplica2 = new FileDetails(22,fileName, key, fileDownloadUri1, fileEntity2.getId());
                fileDetailsRepository.save(fileDetailsReplica2);


                dbFile = new FileEntity2(fileName, contentType, dividedEncryptedFIle.getByteArrPart3());
                dbFileRepo3.save(dbFile);
                fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/downloadFile/")
                        .path(dbFile.getId().toString())
                        .toUriString();
                fileDetailsReplica1 = new FileDetails(31, fileName, key, fileDownloadUri, dbFile.getId());
                fileDetailsRepository.save(fileDetailsReplica1);
                fileEntity2 = new FileEntity1(fileName, contentType, dividedEncryptedFIle.getByteArrPart3());
                fileRepo2.save(fileEntity2);
                fileDownloadUri1 = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/downloadFile/")
                        .path(fileEntity2.getId().toString())
                        .toUriString();
                fileDetailsReplica2 = new FileDetails(32, fileName, key, fileDownloadUri1, fileEntity2.getId());
                fileDetailsRepository.save(fileDetailsReplica2);
        }
        catch (Exception ex)
        {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }

            return key1;
    }

    public static boolean isKeyPrime(Integer key) {
        if (key <= 1)
            return false;
        if (key <= 3)
            return true;
        if (key % 2 == 0 || key % 3 == 0)
            return false;

        for (int i = 5; i * i <= key; i += 6)
            if (key % i == 0 || key % (i + 2) == 0)
                return false;
        return true;
    }

    public FileEntity2 getFile(String fileId) {

        return dbFileRepo3.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
    }

    public List getFileIdList() {
        List<FileEntityParent> fileEntities = new ArrayList(dbFileRepo3.findAll());
        List<String> fileList = new ArrayList<>();
        for (int i = fileEntities.size(); i > 0; i--) {
            fileList.add(fileEntities.get(i - 1).getId());
        }
        fileList.add(Integer.toString(fileList.size()));
        return fileList;
    }
    public List getFileNameList()
    {
//        this.fetchFile("asdfgh.docx");
        List<FileEntityParent> fileEntities = new ArrayList(dbFileRepo3.findAll());
        List<String> fileList = new ArrayList<>();
        for(int i=fileEntities.size(); i>0; i--)
        {
            fileList.add(fileEntities.get(i-1).getFileName());
        }
        fileList.add(Integer.toString(fileList.size()));
        return fileList;
    }




}