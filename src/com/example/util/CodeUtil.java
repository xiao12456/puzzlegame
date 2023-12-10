package com.example.util;

import java.util.ArrayList;
import java.util.Random;

public class CodeUtil {

    public static String getCode() {
        ArrayList<Character> list = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list.add((char) ('A' + i));
            list.add((char) ('a' + i));
        }

        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 4; i++) {
            int randomIndex = r.nextInt(list.size());
            Character c = list.get(randomIndex);
            sb.append(c);
        }

        int number = r.nextInt(10);
        sb.append(number);

        char[] chars = sb.toString().toCharArray();
        int index = r.nextInt(chars.length);
        char temp = chars[4];
        chars[4] = chars[index];
        chars[index]= temp;
        String code = new String(chars);
        return code;
    }
}
