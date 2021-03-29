package com.sandeep.filestorage.util;

import org.springframework.context.annotation.Bean;


public class UserFileWithKey
{
    int key;
    byte[] byteArray;
    public UserFileWithKey()
    {}

    public UserFileWithKey(int k, byte[] b)
    {
        key=k;
        byteArray=b;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public byte[] getbyteArray() {
        return byteArray;
    }

    public void setbyteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }
}
