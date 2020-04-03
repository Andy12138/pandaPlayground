package com.zmg20200111.panda;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.ParseException;
public class TestImage {
    /**
     * @param args
     * @throws ParseException
     */
    public static void main(String[] args) {
        exportImg2("广东省广州市","D:\\tmp\\1.jpg");
    }

    public static void exportImg2(String username,String headImg){
        try {
            //1.jpg是你的 主图片的路径
//            InputStream is = new FileInputStream("D:\\tmp\\yyy.jpg");
            BufferedImage big = ImageIO.read(new File("D:\\\\tmp\\\\real.jpg"));
            Graphics2D g = big.createGraphics();
            //通过JPEG图象流创建JPEG数据流解码器
//            JPEGImageDecoder jpegDecoder = JPEGCodec.createJPEGDecoder(is);
            //解码当前JPEG数据流，返回BufferedImage对象
//            BufferedImage buffImg = jpegDecoder.decodeAsBufferedImage();
            //得到画笔对象
//            Graphics g = buffImg.getGraphics();
            System.out.println("长"+ big.getWidth() + "; 高" + big.getHeight());

            //创建你要附加的图象。
            //小图片的路径
            ImageIcon imgIcon = new ImageIcon(headImg);

            //得到Image对象。
            Image img = imgIcon.getImage();

            //将小图片绘到大图片上。
            //5,300 .表示你的小图片在大图片上的位置。
            g.drawImage(img,400,115,null);

            //设置颜色。
            g.setColor(Color.BLACK);


            //最后一个参数用来设置字体的大小
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            Font f = new Font("宋体",Font.BOLD,60);
            Color mycolor = Color.BLACK;//new Color(0, 0, 255);
            g.setColor(mycolor);
            g.setFont(f);

            //10,20 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
            g.drawString(username,747,1163);

            g.dispose();


            OutputStream os;

            //os = new FileOutputStream("d:/union.jpg");
            String shareFileName = "D:\\tmp\\out\\小胡子.jpg";
            os = new FileOutputStream(shareFileName);
            //创键编码器，用于编码内存中的图象数据。
            JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
            en.encode(big);

//            is.close();
            os.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ImageFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
