package com.sandeep.filestorage.util;

import com.sandeep.filestorage.service.DBFileStorageService;

import java.io.*;
import java.util.*;

public class Encryption {

    private long getCypherText(long n, int c){return (n-(((long)c*c)%n));}

    private int genPrime()
    {
        int num=4;
        Random ran= new Random();

        while(!DBFileStorageService.isKeyPrime(num))
            num=ran.nextInt(13000)+17000;
        return num;
    }

    public DividedEncryptedFIle encrypt(UserFileWithKey bv)
    {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
            outputStream.write( bv.byteArray );
            BufferedReader br=new BufferedReader(new InputStreamReader(new ByteArrayInputStream(outputStream.toByteArray())));

            StringBuilder stringBuilder=new StringBuilder();

            int num, p = bv.getKey(), q;
            long c, n, cypher;
            String s;

            if (p == 0) {
                p = genPrime();
                q = genPrime();
                while (p == q)
                    q = genPrime();
            } else {
                q = genPrime();
                while (p == q)
                    q = genPrime();
            }

            n = p * q;

            while ((c = br.read()) != -1) {

                s = Long.toBinaryString(c);
                s = s + new StringBuilder(s).reverse().toString();
                num = Integer.parseInt(s, 2);
                cypher = getCypherText(n, num);
                s = Long.toBinaryString(cypher);

                while (s.length() < 34)
                    s = "0" + s;
                stringBuilder.append(s);
            }
          DividedEncryptedFIle v1 = new DividedEncryptedFIle(p, q,
                  stringBuilder.toString().substring(0,stringBuilder.length()/3).getBytes(),
                  stringBuilder.toString().substring(stringBuilder.length()/3,(stringBuilder.length()/3)*2).getBytes(),
                  stringBuilder.toString().substring((stringBuilder.length()/3)*2).getBytes());
            return v1;
        }
        catch (Exception e){return null;}

    }
}
