package com.ggxueche.utils;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zhujigao on 17/3/27.
 */

public class MD5Util {

    /**
     * 默认的密码字符串组合，用来将字节转换成 16 进制表示的字符,apache校验下载的文件的正确性用的就是默认的这个组合
     */
    protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    protected static MessageDigest messagedigest = null;
    static {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static final String TAG = MD5Util.class.getSimpleName();
    private static final int STREAM_BUFFER_LENGTH = 1024;
    private static final char[] HEX = "0123456789abcdef".toCharArray();

    public static MessageDigest getDigest(final String algorithm) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(algorithm);
    }

    public static String md5(String txt) {
        return byte2Hex(md5(txt.getBytes()));
    }

    public static String md5File(String filePath) throws IOException, NoSuchAlgorithmException {
        FileInputStream fis= new FileInputStream(filePath);
        return byte2Hex(md5(fis));
    }


    private static byte[] md5(byte[] bytes) {
        try {
            MessageDigest digest = getDigest("MD5");
            digest.update(bytes);
            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] md5(InputStream is) throws NoSuchAlgorithmException, IOException {
        return updateDigest(getDigest("MD5"), is).digest();
    }

    private static MessageDigest updateDigest(final MessageDigest digest, final InputStream data) throws IOException {
        final byte[] buffer = new byte[STREAM_BUFFER_LENGTH];
        int read = data.read(buffer, 0, STREAM_BUFFER_LENGTH);

        while (read > -1) {
            digest.update(buffer, 0, read);
            read = data.read(buffer, 0, STREAM_BUFFER_LENGTH);
        }

        return digest;
    }

    /**
     * 将字节数组转成 16 进制的字符串来表示，每个字节采用两个字符表表示
     *
     * @param bytes 需要转换成 16 进制的字节数组
     * @return
     */
    private static String byte2Hex(byte[] bytes) {
        char[] chs = new char[bytes.length * 2];
        for(int i = 0, offset = 0; i < bytes.length; i++) {
            chs[offset++] = HEX[bytes[i] >> 4 & 0xf];
            chs[offset++] = HEX[bytes[i] & 0xf];
        }
        return new String(chs);
    }

    public static String getFileMD5String(File file) {
        try {
            InputStream fis;
            fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int numRead = 0;
            while ((numRead = fis.read(buffer)) > 0) {
                messagedigest.update(buffer, 0, numRead);
            }
            fis.close();

        } catch (IOException e) {
            e.printStackTrace();
            Logger.i("getFileMD5String", "getFileMD5String:   " + e.getMessage());
        }

        return bufferToHex(messagedigest.digest());
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }


    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];// 取字节中高 4 位的数字转换
        // 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同
        char c1 = hexDigits[bt & 0xf];// 取字节中低 4 位的数字转换
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

}
