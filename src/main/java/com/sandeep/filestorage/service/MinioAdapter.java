package com.sandeep.filestorage.service;

import com.sandeep.filestorage.entity.details.FileDetails;
import com.sandeep.filestorage.entity.entity.FileEntity;
import com.sandeep.filestorage.entity.entity1.FileEntity1;
import com.sandeep.filestorage.entity.entity2.FileEntity2;
import com.sandeep.filestorage.exception.FileStorageException;
import com.sandeep.filestorage.util.DividedEncryptedFIle;
import com.sandeep.filestorage.util.Encryption;
import com.sandeep.filestorage.util.UserFileWithKey;
import com.sun.corba.se.pept.encoding.OutputObject;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//import org.xmlpull.v1.XmlPullParserException;


import javax.annotation.PostConstruct;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class MinioAdapter {

    @Autowired
    MinioClient minioClient;

    @Value("${spring.minio.bucket}")
    String defaultBucketName;

    @Value("${minio.default.folder}")
    String defaultBaseFolder;

    public List<Bucket> getAllBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }


//    public void uploadFile(String name, byte[] content) {
//        File file = new File("/tmp/" + name);
//        file.canWrite();
//        file.canRead();
//        try {
//            FileOutputStream iofs = new FileOutputStream(file);
//            iofs.write(content);
//            minioClient.putObject(defaultBucketName, defaultBaseFolder + name, file.getAbsolutePath());
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage());
//        }
//
//    }


//    public Integer storeFile(MultipartFile file, Integer key1) {
//        // Normalize file name
//        if(key1!=null && (key1 < 17000 || key1 > 30000 || !this.isKeyPrime(key1)))
//            throw new InvalidKeyException("Sorry you entered a wrong key");
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        String contentType=file.getContentType();
//        if(fileName.contains("..")) {
//            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
//        }
//        Encryption encryption = new Encryption();
//        DividedEncryptedFIle dividedEncryptedFIle;
//        try
//        {
//            dividedEncryptedFIle=encryption.encrypt(new UserFileWithKey((key1==null)?0:key1,file.getBytes()));
//
//        }
//        catch (Exception e)
//        {
//            throw new FileStorageException("File not supported");
//        }
//
//        key1=dividedEncryptedFIle.getKey1();
//        Integer key = dividedEncryptedFIle.getKey2();
//        try {
//            FileEntity2 dbFile = new FileEntity2(fileName, contentType, dividedEncryptedFIle.getByteArrPart1());
//            minioClient.putObject();
//            dbFileRepo3.save(dbFile);
//            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                    .path("/downloadFile/")
//                    .path(dbFile.getId().toString())
//                    .toUriString();
//            FileDetails fileDetailsReplica1 = new FileDetails(11, fileName, key, fileDownloadUri, dbFile.getId());
//            fileDetailsRepository.save(fileDetailsReplica1);
//            FileEntity fileEntity1 = new FileEntity(fileName, contentType, dividedEncryptedFIle.getByteArrPart1());
//            fileRepo1.save(fileEntity1);
//            String fileDownloadUri1 = ServletUriComponentsBuilder.fromCurrentContextPath()
//                    .path("/downloadFile/")
//                    .path(fileEntity1.getId().toString())
//                    .toUriString();
//            FileDetails fileDetailsReplica2 = new FileDetails(12, fileName, key, fileDownloadUri1, fileEntity1.getId());
//            fileDetailsRepository.save(fileDetailsReplica2);
//
//
//
//            FileEntity dbFile1 = new FileEntity(fileName, contentType, dividedEncryptedFIle.getByteArrPart2());
//            fileRepo1.save(dbFile1);
//            fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                    .path("/downloadFile/")
//                    .path(dbFile1.getId().toString())
//                    .toUriString();
//            fileDetailsReplica1 = new FileDetails(21,  fileName, key, fileDownloadUri, dbFile1.getId());
//            fileDetailsRepository.save(fileDetailsReplica1);
//            FileEntity1 fileEntity2 = new FileEntity1(fileName, contentType, dividedEncryptedFIle.getByteArrPart2());
//            fileRepo2.save(fileEntity2);
//            fileDownloadUri1 = ServletUriComponentsBuilder.fromCurrentContextPath()
//                    .path("/downloadFile/")
//                    .path(fileEntity2.getId().toString())
//                    .toUriString();
//            fileDetailsReplica2 = new FileDetails(22,fileName, key, fileDownloadUri1, fileEntity2.getId());
//            fileDetailsRepository.save(fileDetailsReplica2);
//
//
//            dbFile = new FileEntity2(fileName, contentType, dividedEncryptedFIle.getByteArrPart3());
//            dbFileRepo3.save(dbFile);
//            fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                    .path("/downloadFile/")
//                    .path(dbFile.getId().toString())
//                    .toUriString();
//            fileDetailsReplica1 = new FileDetails(31, fileName, key, fileDownloadUri, dbFile.getId());
//            fileDetailsRepository.save(fileDetailsReplica1);
//            fileEntity2 = new FileEntity1(fileName, contentType, dividedEncryptedFIle.getByteArrPart3());
//            fileRepo2.save(fileEntity2);
//            fileDownloadUri1 = ServletUriComponentsBuilder.fromCurrentContextPath()
//                    .path("/downloadFile/")
//                    .path(fileEntity2.getId().toString())
//                    .toUriString();
//            fileDetailsReplica2 = new FileDetails(32, fileName, key, fileDownloadUri1, fileEntity2.getId());
//            fileDetailsRepository.save(fileDetailsReplica2);
//        }
//        catch (Exception ex)
//        {
//            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
//        }

//        return key1;
//    }

    public void uploadFile(MultipartFile file)
    {
        try{
            InputStream inputStream = file.getInputStream();
            minioClient.putObject(
                            PutObjectArgs.builder().bucket(defaultBucketName).object("my-objectname").stream(
                                    inputStream, -1, 10485760)
                                    .contentType("txt")
                                    .build());
        }catch (Exception e)
        {
            e.printStackTrace();
        }
//            try {
//                try {
//                    minioClient.putObject(
//                            PutObjectArgs.builder().bucket(defaultBucketName).object("my-objectname").stream(
//                                    file.getInputStream(), -1, 10485760)
//                                    .contentType("txt")
//                                    .build());
//                } catch (ErrorResponseException e) {
//                    e.printStackTrace();
//                } catch (InsufficientDataException e) {
//                    e.printStackTrace();
//                } catch (InternalException e) {
//                    e.printStackTrace();
//                } catch (InvalidKeyException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (NoSuchAlgorithmException e) {
//                    e.printStackTrace();
//                }
//            } catch (InvalidResponseException e) {
//                e.printStackTrace();
//            } catch (ServerException e) {
//                e.printStackTrace();
//            } catch (XmlParserException e) {
//                e.printStackTrace();
//            }
////                    uploadObject(
////                            UploadObjectArgs.builder()
////                                    .bucket("my-bucketname")
////                                    .object("my-objectname")
////                                    .filename("person.json").build());
//
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

    public byte[] getFile(String key) {
//        try {
//            InputStream obj = minioClient.getObject(defaultBucketName, defaultBaseFolder + "/" + key);
//
//            byte[] content = IOUtils.toByteArray(obj);
//            obj.close();
//            return content;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return null;
    }

    @PostConstruct
    public void init() {
    }
}

