package com.personal.feng.utils.qr_code;

import com.swetake.util.Qrcode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class WriteQrcode {
    public static void main(String[] args) throws IOException {


        String content = "https://www.baidu.com" ;

        CreateZxing(content);

        BufferedImage image = addLogo();

        ImageIO.write(image, "png", new File("C:/Users/Administrator/Desktop/testcode/code/111.png"));

        System.out.println("生成成功！！！");

    }


    /**
     * 创建二维码
     *
     * @param content
     * @throws UnsupportedEncodingException
     * @throws IOException
     */

    private static void CreateZxing(String content) throws UnsupportedEncodingException, IOException {

        // 整体思路：是利用java的绘图工具进行画制的

        // 1、创建qrcode对象，主要是为了进行解析内容成boolean类型的数组，进行画制

        Qrcode qrcode = new Qrcode();

        qrcode.setQrcodeErrorCorrect('L');// 纠错等级

        qrcode.setQrcodeEncodeMode('B');// N代表数字，A代表a-Z，B代表其他的字符

        //设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大

        //版本号代表你生成的二维码的像素的大小

        //版本1是21*21的，版本号每增加1，边长增加4。也就是说版本7的大小是45 * 45的。版本号最大值是40

        //另外，版本7的编码的字节数如果超过了119，那么将无法编码

        qrcode.setQrcodeVersion(7);// 版本


        // 67+12*(版本号-1) 固定公式 11011111

        int width = 67 + 12 * (7 - 1);

        int height = 67 + 12 * (7 - 1);

        // 2、创建Graphics2D 画图对象

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Graphics2D java 绘图的方法

        Graphics2D graphics2d = bufferedImage.createGraphics();

        graphics2d.setBackground(Color.WHITE);

        graphics2d.setColor(Color.BLACK);

        graphics2d.clearRect(0, 0, width, height);// 设置二维码形状

        // 偏移量。设置的是到顶和到左边的距离，一般设置成2-3，如果设置比较大的话，不能完全显示

        int pixoff = 2;

        // 3、进行画制

        byte[] d = content.getBytes("utf-8");// 处理有汉字的

        //限制最大字节数是119
        System.out.println("d.length"+d.length);
        if (d.length > 0 && d.length < 300) {

            // 将字符串内容转换成boolean类型的数组

            boolean[][] s = qrcode.calQrcode(d);


            for (int i = 0; i < s.length; i++) {

                for (int j = 0; j < s.length; j++) {

                //注意boolean类型的数组坐标，是j i，按照行来写的

                    if (s[j][i]) {

                // fillrect 充满矩形 前两个参数是坐标x、y 后两个宽度，长度表示的是显示的黑点的宽度和和长度

                        graphics2d.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);

                    }

                }

            }

        }

        graphics2d.dispose();

        bufferedImage.flush();

// 可以写入到文件和流中

        ImageIO.write(bufferedImage, "png", new File("C:/Users/Administrator/Desktop/testcode/code/qrcode.png"));

    }


    /**
     * 添加logo到二维码上面去
     *
     * @return
     * @throws IOException
     */

    private static BufferedImage addLogo() throws IOException {

        BufferedImage image = ImageIO.read(new File("C:/Users/Administrator/Desktop/testcode/code/qrcode.png"));

//画笔

        Graphics2D gs = image.createGraphics();

        //读logo

        BufferedImage logo = ImageIO.read(new File("C:/Users/Administrator/Desktop/testcode/code/1.jpg"));


        int widthLogo = logo.getWidth() > image.getWidth() * 3 / 40 ? (image.getWidth() * 3 / 40) : logo.getWidth();

        int heightLogo = logo.getHeight() > image.getHeight() * 3 / 40 ? (image.getHeight() * 3 / 40) : logo.getHeight();

        //设置成中心位置

        int x = (image.getWidth() - widthLogo) / 2;

        int y = (image.getHeight() - heightLogo) / 2;


        //设置成右下角

//        int x = (image.getWidth() - widthLogo);

//        int y = (image.getHeight() - heightLogo);

        //设置透明度

//        gs.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,1.0f));

        //旋转，生城个性二维码

//        gs.rotate(Math.toRadians(45), x, y);


        //logo画到二维码上面去

        gs.drawImage(logo, x, y, widthLogo, heightLogo, null);

        //填充图形周边圆角

        gs.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);

        //stroke属性控制线条的宽度、笔形样式、线段连接方式或短划线图案。

        gs.setStroke(new BasicStroke(2));

        gs.setColor(Color.WHITE);

        //logo位置

        gs.drawRect(x, y, widthLogo, heightLogo);

        gs.dispose();

        logo.flush();

        image.flush();

        return image;

    }
}
