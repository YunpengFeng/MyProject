package com.personal.feng.utils.qr_code;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import jp.sourceforge.qrcode.QRCodeDecoder;

import jp.sourceforge.qrcode.exception.DecodingFailedException;

public class ReaderQRCode {
    public static void main(String[] args) {


        File file = new File("C:/Users/Administrator/Desktop/testcode/code/qrcode.png");
        String result = null;

        try {

            BufferedImage bufferedImage = null;
            try {
                bufferedImage = ImageIO.read(file);

            } catch (IOException e) {
                e.printStackTrace();
            }

            QRCodeDecoder qrCodeDecoder = new QRCodeDecoder();
            /*logo设置在图二维码的太大，会导致解析不了*/
            result = new String(qrCodeDecoder.decode(new MYQRCodeImage(bufferedImage)), "utf-8");

        } catch (DecodingFailedException e) {

            e.printStackTrace();

        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }


        System.out.println("结果是： " + result);

        System.out.println("读取成功！！！");


    }

}

