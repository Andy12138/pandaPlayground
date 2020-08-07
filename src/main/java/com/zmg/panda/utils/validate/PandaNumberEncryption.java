package com.zmg.panda.utils.validate;

import java.util.*;

/**
 * 自定义数字加密
 * @author Andy
 */
public class PandaNumberEncryption {
    /**
     * 密码映射
     */
    private static Map<Character, List<Character>> PASSWORD_MAP = new HashMap<>(10);

    static {
        PASSWORD_MAP.put('1', new ArrayList<>(Arrays.asList('a', 'k', 'w', 'G', 'S', '2')));
        PASSWORD_MAP.put('2', new ArrayList<>(Arrays.asList('b', 'm', 'x', 'H', 'T', '3')));
        PASSWORD_MAP.put('3', new ArrayList<>(Arrays.asList('c', 'n', 'y', 'J', 'U', '4')));
        PASSWORD_MAP.put('4', new ArrayList<>(Arrays.asList('d', 'p', 'z', 'K', 'V', '5')));
        PASSWORD_MAP.put('5', new ArrayList<>(Arrays.asList('e', 'q', 'A', 'L', 'W', '6')));
        PASSWORD_MAP.put('6', new ArrayList<>(Arrays.asList('f', 'r', 'B', 'M', 'X', '7')));
        PASSWORD_MAP.put('7', new ArrayList<>(Arrays.asList('g', 's', 'C', 'N', 'Y', '8')));
        PASSWORD_MAP.put('8', new ArrayList<>(Arrays.asList('h', 't', 'D', 'P', 'Z', '9')));
        PASSWORD_MAP.put('9', new ArrayList<>(Arrays.asList('i', 'u', 'E', 'Q', '#', '+')));
        PASSWORD_MAP.put('0', new ArrayList<>(Arrays.asList('j', 'v', 'F', 'R', '1', '-')));
    }

    /**
     * 加密（只能加密数字）
     * @param content
     * @return
     */
    public static String encrypt(String content) {
        StringJoiner password = new StringJoiner("");
        char[] chars = content.toCharArray();
        for (char aChar : chars) {
            int index = new Random().nextInt(5);
            List<Character> passwordList = PASSWORD_MAP.get(aChar);
            password.add(String.valueOf(passwordList.get(index)));
        }
        return password.toString();
    }

    /**
     * 解密
     * @param enText
     * @return
     */
    public static String decrypt(String enText) {
        StringJoiner deContent = new StringJoiner("");
        char[] chars = enText.toCharArray();
        for (char aChar : chars) {
            PASSWORD_MAP.forEach((key, list) -> {
                if (list.contains(aChar)) {
                    deContent.add(String.valueOf(key));
                }
            });
        }
        return deContent.toString();
    }

    public static void main(String[] args) {
        String content = "20200904";
        String password = encrypt(content);
        System.out.println(password);
        String decrypt = decrypt(password);
        StringJoiner result = new StringJoiner("-").add(decrypt.substring(0, 4)).add(decrypt.substring(4, 6)).add(decrypt.substring(6));
        System.out.println(decrypt);
        System.out.println(result);
    }
}
