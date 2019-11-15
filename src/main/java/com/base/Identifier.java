package com.base;

import java.util.UUID;

/**
 * 生成ID号
 * @author Administrator
 */
public class Identifier {

    private static final String[] CHAR_CONSTANT = { "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X", "Y", "Z" };

    /**
     * 生成主键ID
     * @return
     */
    public static String getUUid(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
