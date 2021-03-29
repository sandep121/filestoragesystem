package com.sandeep.filestorage.util;

import org.springframework.web.multipart.MultipartFile;

/*
* This class is used to send and receive the multipart files
* of the parent file with key.
*/

public class EncryptedFiles {
    Integer key;
    MultipartFile part1;
    MultipartFile part2;
    MultipartFile part3;

    public EncryptedFiles() {
    }

    public EncryptedFiles(Integer key, MultipartFile part1, MultipartFile part2, MultipartFile part3) {
        this.key = key;
        this.part1 = part1;
        this.part2 = part2;
        this.part3 = part3;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public MultipartFile getPart1() {
        return part1;
    }

    public void setPart1(MultipartFile part1) {
        this.part1 = part1;
    }

    public MultipartFile getPart2() {
        return part2;
    }

    public void setPart2(MultipartFile part2) {
        this.part2 = part2;
    }

    public MultipartFile getPart3() {
        return part3;
    }

    public void setPart3(MultipartFile part3) {
        this.part3 = part3;
    }
}
