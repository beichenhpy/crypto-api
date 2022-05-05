/*
 * Copyright 2022 韩鹏宇
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.beichenhpy.cryptoapi.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * AES CBC模式加密工具类
 *
 * @author hubin
 * @since 2020-05-23
 */
public class AES {


    private static final String AES = "AES";
    private static final String AES_CBC_CIPHER = "AES/CBC/PKCS5Padding";

    /**
     * 加密
     *
     * @param data 需要加密的内容
     * @param key  加密密码
     * @return 加密后的字节数组
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, AES);
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, AES);
            Cipher cipher = Cipher.getInstance(AES_CBC_CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(key));
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 解密
     *
     * @param data 待解密内容
     * @param key  解密密钥
     * @return 解密后的字节数组
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, AES);
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, AES);
            Cipher cipher = Cipher.getInstance(AES_CBC_CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(key));
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 加密
     *
     * @param data 需要加密的内容
     * @param key  加密密码
     * @return 加密后的字符串
     */
    public static String encrypt(String data, String key) throws Exception {
        byte[] valueByte = encrypt(data.getBytes(StandardCharsets.UTF_8), key.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(valueByte);
    }

    /**
     * 解密
     *
     * @param data 待解密内容 base64 字符串
     * @param key  解密密钥
     * @return 解密后的字符串
     */
    public static String decrypt(String data, String key) throws Exception {
        byte[] originalData = Base64.getDecoder().decode(data.getBytes());
        byte[] valueByte = decrypt(originalData, key.getBytes(StandardCharsets.UTF_8));
        return new String(valueByte);
    }

    /**
     * 生成一个随机字符串密钥
     */
    public static String generateRandomKey() {
        return get32UUID().substring(0, 16);
    }


    /**
     * 使用ThreadLocalRandom获取UUID获取更优的效果 去掉"-"
     */
    public static String get32UUID() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return new UUID(random.nextLong(), random.nextLong()).toString().replace("-", "");
    }
}
