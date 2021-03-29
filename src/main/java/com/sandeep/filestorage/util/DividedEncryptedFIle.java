package com.sandeep.filestorage.util;

public class DividedEncryptedFIle {

    int key1, key2;
    byte[] byteArrPart1;
    byte[] byteArrPart2;
    byte[] byteArrPart3;

    public DividedEncryptedFIle(int k1, int k2, byte[] b1, byte[] b2, byte[] b3)
    {
        key1=k1;
        key2=k2;
        byteArrPart1=b1;
        byteArrPart2=b2;
        byteArrPart3=b3;
    }

    public int getKey1() {
        return key1;
    }

    public void setKey1(int key1) {
        this.key1 = key1;
    }

    public int getKey2() {
        return key2;
    }

    public void setKey2(int key2) {
        this.key2 = key2;
    }

    public byte[] getByteArrPart1() {
        return byteArrPart1;
    }

    public void setByteArrPart1(byte[] byteArrPart1) {
        this.byteArrPart1 = byteArrPart1;
    }

    public byte[] getByteArrPart2() {
        return byteArrPart2;
    }

    public void setByteArrPart2(byte[] byteArrPart2) {
        this.byteArrPart2 = byteArrPart2;
    }

    public byte[] getByteArrPart3() {
        return byteArrPart3;
    }

    public void setByteArrPart3(byte[] byteArrPart3) {
        this.byteArrPart3 = byteArrPart3;
    }
}
