package com.manoj.urlShortener.util;

import java.util.Random;

public class Base62 {

    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final Integer BASE = ALPHABET.length();
    private static final Random  RANDOM = new Random();

    private Base62() {}

    //        convert Long ID -> short string
    public static String encode(Long number){
        if(number == 0){return "0";}
        StringBuilder sb = new StringBuilder();
        while(number>0){
            sb.append(ALPHABET.charAt((int)(number % BASE)));
            number /= BASE;
        }
        return sb.reverse().toString();
    }

//    for collision avoidance fallback: random 7-8 char code
    public static String randomCode(int length){
        StringBuilder sb = new StringBuilder(length);
        for(int i = 0; i < length; i++){
            sb.append(ALPHABET.charAt(RANDOM.nextInt(BASE)));
        }
        return sb.toString();
    }

}
