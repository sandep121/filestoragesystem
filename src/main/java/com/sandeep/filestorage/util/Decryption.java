package com.sandeep.filestorage.util;

import com.sandeep.filestorage.exception.InvalidKeyException;

import java.io.*;

public class Decryption {

    private boolean isPalindrome(String s)
    {
        int a=Integer.parseInt(s,2);
        String reverse = new StringBuffer(s).reverse().toString();
        if(a==0)
            return false;
        if (s.equals(reverse))
            return true;
        else
            return false;
    }


    static int getOriginal(long n)
    {
        String s=Long.toBinaryString(n);
        String s1="";
        for(int i=0; i<(s.length())/2; i++)
            s1=s1+s.charAt(i);

        int n1=Integer.parseInt(s1, 2);

        return n1;
    }
    //static long y(long x){return ((long)Math.sqrt(x));}



    private int getCorrectRoot(long m1, long m2, long m3, long m4)
    {
        if(isPalindrome(Long.toBinaryString(m1)))
            return getOriginal(m1);
        if(isPalindrome(Long.toBinaryString(m2)))
            return getOriginal(m2);
        if(isPalindrome(Long.toBinaryString(m3)))
            return getOriginal(m3);
        if(isPalindrome(Long.toBinaryString(m4)))
            return getOriginal(m4);

        return 0;
    }

    private char implementCRT(long c, int a, int b, int p, int q)
    {
        long r=0,s=0,n=(long)p*q;
        long xx=(long)Math.sqrt(n-c),x=0,y=0;
        char decyp;

        r=(long)(Math.pow(c, (p+1)/4)%p);
        s=(long)(Math.pow(c, (q+1)/4)%q);
        x=(a*p*s+b*q*r)%n;
        y=(a*p*s-b*q*r)%n;
        long m1=xx,m2=n-x,m3=y,m4=n-y;
        decyp=(char)getCorrectRoot(m1, m2, m3, m4);
        return decyp;
    }


    public byte[] decrypt(DividedEncryptedFIle v)
    {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
            outputStream.write( v.getByteArrPart1() );
            outputStream.write( v.getByteArrPart2());
            outputStream.write( v.getByteArrPart3() );
        BufferedReader br=new BufferedReader(new InputStreamReader(new ByteArrayInputStream(outputStream.toByteArray())));
        int a=0, b=0, i, j;
        int p=v.getKey1(), q=v.getKey2();
        char decryp;
        StringBuilder stringBuilder = new StringBuilder();
        long c;
        String str;
        for(i=0; i<=100000; i++)
        {
            for(j=0; j<=100000; j++)
            {
                if((i*p-j*q)==1)
                {
                    a=i;
                    b=-j;
                    break;
                }
                if((j*q-i*p)==1)
                {
                    a=-i;
                    b=j;
                    break;
                }
            }
            if(a!=0 || b!=0)
                break;
        }
        while((c=br.read())!=-1)
        {
            c=c-48;
            str=Long.toString(c);
            for(i=0; i<33; i++)
                str=str+(br.read()-48);
            c=Long.parseLong(str, 2);
            decryp=implementCRT(c, a, b, p, q);

            if(decryp==0)
                throw new InvalidKeyException("Please enter the correct key");
            stringBuilder.append(decryp);
        }
        System.out.println(stringBuilder);
        return stringBuilder.toString().getBytes();
        }
        catch(Exception e)
        { throw new InvalidKeyException("Please check your filename and key"); }
    }
}
