package com.personal.feng.utils.qr_code;

import jp.sourceforge.qrcode.data.QRCodeImage;
import java.awt.image.BufferedImage;

public class MYQRCodeImage  implements QRCodeImage {

   private BufferedImage BufferedImage;

    public MYQRCodeImage(BufferedImage BufferedImage){

        this.BufferedImage = BufferedImage;

    }

    @Override

    public int getHeight() {

        return BufferedImage.getHeight();

    }

    @Override

    public int getPixel(int arg0, int arg1) {

        return BufferedImage.getRGB(arg0, arg1);

    }

    @Override

    public int getWidth() {

        return BufferedImage.getWidth();

    }

}