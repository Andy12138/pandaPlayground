package com.zmg.panda.common;


import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Andy
 */
public class AESUtils {


    public static final String algorithm = "AES";
    // AES/CBC/NOPaddin
    // AES 默认模式
    // 使用CBC模式, 在初始化Cipher对象时, 需要增加参数, 初始化向量IV : IvParameterSpec iv = new
    // IvParameterSpec(key.getBytes());
    // NOPadding: 使用NOPadding模式时, 原文长度必须是8byte的整数倍
    public static final String transformation = "AES/CBC/NOPadding";
    public static final String key = "1234567812345678";

    /***
     * 加密
     * @param original 需要加密的参数（注意必须是16位）
     * @return
     * @throws Exception
     */
    public static String encryptByAES(String original) throws Exception {
        original = enformat16(original);
        // 获取Cipher
        Cipher cipher = Cipher.getInstance(transformation);
        // 生成密钥
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), algorithm);
        // 指定模式(加密)和密钥
        // 创建初始化向量
        IvParameterSpec iv = new IvParameterSpec(key.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
        // cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        // 加密
        String hexStr = parseByte2HexStr(original.getBytes());
        byte[] bytes = cipher.doFinal(hexStr.getBytes());
        return Base64Utils.encodeToString(bytes);
    }

    private static String enformat16(String str) {
        byte[] bytes = str.getBytes();
        int length = bytes.length;
        int remainder = length % 8;
        int num = 8 - remainder;
        if (num != 8) {
            for (int i = 0; i < num; i++) {
                str += "0";
            }
        }
        return str;
    }

    private static String deformat16(String str) {
        int length = str.length();
        String flag = str.substring(length - 1);
        if (flag.equals("0")) {
            str = str.substring(0, length -1);
            return deformat16(str);
        } else {
            return str;
        }
    }

    /**
     * 将二进制转换成16进制
     * @method parseByte2HexStr
     * @param buf
     * @return
     * @throws
     * @since v1.0
     */
    private static String parseByte2HexStr(byte buf[]){
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < buf.length; i++){
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     * @method parseHexStr2Byte
     * @param hexStr
     * @return
     * @throws
     * @since v1.0
     */
    private static byte[] parseHexStr2Byte(String hexStr){
        if(hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 解密
     * @param encrypted 需要解密的参数
     * @return
     * @throws Exception
     */
    public static String decryptByAES(String encrypted) throws Exception {
        // 获取Cipher
        Cipher cipher = Cipher.getInstance(transformation);
        // 生成密钥
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), algorithm);
        // 指定模式(解密)和密钥
        // 创建初始化向量
        IvParameterSpec iv = new IvParameterSpec(key.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
        // cipher.init(Cipher.DECRYPT_MODE, keySpec);
        // 解密
        byte[] bytes = cipher.doFinal(Base64Utils.decodeFromString(encrypted));
        byte[] bytes2 = parseHexStr2Byte(new String(bytes));

        String s = new String(bytes2);
        return deformat16(s);
    }

}
