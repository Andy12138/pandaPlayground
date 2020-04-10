package com.zmg20200111.panda.utils.graphics;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.imageio.plugins.png.PNGImageWriter;
import com.zmg20200111.panda.utils.graphics.bean.RegistrationCertificate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
public class DrawJgpImageUtils {

    /**
     * 高度偏移度
     */
    public static final int DEVIATION_TOP = 60;

    public void drawRegistrationCertificate(String imagePath, RegistrationCertificate drawParams) {
        try {
            BufferedImage baseImg = ImageIO.read(new File(imagePath));
            log.info("图片的长：{}，高：{}", baseImg.getWidth(), baseImg.getHeight());
            // 获取画笔对象
            Graphics2D g = baseImg.createGraphics();
            // *****
            baseImg = g.getDeviceConfiguration().createCompatibleImage(baseImg.getWidth(), baseImg.getHeight(), Transparency.TRANSLUCENT);
            g.dispose();
            g = baseImg.createGraphics();

            // 画笔对象写入的文字去除锯齿
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            Color drawColor = Color.BLACK;
            // 开始写总裁机构名
            Font drawFont = new Font(drawParams.getOrganNameFamily(), Font.PLAIN, drawParams.getOrganNameSize());
            g.setColor(drawColor);
            g.setFont(drawFont);
            int organX = getOrganX(drawParams);
            g.drawString(drawParams.getOrganName(), organX, drawParams.getOrganNameTop() + DEVIATION_TOP);
            // 开始社会信用码
            Font drawFont2 = new Font(drawParams.getSocialCreditFamily(), Font.PLAIN, drawParams.getSocialCreditCodeSize());
            g.setColor(drawColor);
            g.setFont(drawFont2);
            g.drawString(drawParams.getSocialCreditCode(), drawParams.getSocialCreditLeft(), drawParams.getSocialCreditTop() + DEVIATION_TOP);
            // 开始写审核机构
            Font drawFont3 = new Font(drawParams.getAuditOrganFamily(), Font.PLAIN, drawParams.getAuditOrganSize());
            g.setColor(drawColor);
            g.setFont(drawFont3);
            g.drawString(drawParams.getAuditOrgan(), drawParams.getAuditOrganLeft(), drawParams.getAuditOrganTop() + DEVIATION_TOP);
            // 开始写审核日期
            Font drawFont4 = new Font(drawParams.getAuditDateFamily(), Font.PLAIN, drawParams.getAuditDateSize());
            g.setColor(drawColor);
            g.setFont(drawFont4);
            g.drawString(drawParams.getAuditDate(), drawParams.getAuditDateLeft(), drawParams.getAuditDateTop() + DEVIATION_TOP);

            // 画笔使用结束
            g.dispose();
            // 输出到一个目录
            OutputStream os = new FileOutputStream("d:\\tmp\\out\\机构登记证.png");
            // 创建编码器，用于编码内存中的图像数据
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(baseImg);
            param.setQuality(1f, true);
            encoder.setJPEGEncodeParam(param);
            encoder.encode(baseImg);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取仲裁机构距离右边的距离
     * @param drawParams
     * @return
     */
    private int getOrganX(RegistrationCertificate drawParams) {
        int organX;
        int boxLeft = drawParams.getOrganNameBoxLeft();
        int boxRight = drawParams.getOrganNameBoxReght();
        int size = drawParams.getOrganNameSize();
        int fontWith = size * drawParams.getOrganName().length();
        int boxWith = boxRight - boxLeft;
        if (fontWith < boxWith) {
            organX = boxLeft + (boxWith - fontWith)/2;
        } else {
            organX = boxLeft - (fontWith - boxWith);
        }
        return organX;
    }

    public static void main(String[] args) {
        DrawJgpImageUtils utils = new DrawJgpImageUtils();
        utils.drawRegistrationCertificate("D:\\tmp\\zjpng.png", utils.rcParams);
    }

    private RegistrationCertificate rcParams = new RegistrationCertificate();

    public DrawJgpImageUtils() {
        rcParams.setOrganName("中山");
        rcParams.setOrganNameFamily("楷体");
        rcParams.setOrganNameSize(65);
        rcParams.setOrganNameTop(180);
        rcParams.setOrganNameBoxLeft(770);
        rcParams.setOrganNameBoxReght(1540);

        rcParams.setSocialCreditCode("GDGZ20200403");
        rcParams.setSocialCreditFamily("宋体");
        rcParams.setSocialCreditCodeSize(65);
        rcParams.setSocialCreditLeft(2365);
        rcParams.setSocialCreditTop(970);

        rcParams.setAuditOrgan("广东省司法厅");
        rcParams.setAuditOrganFamily("宋体");
        rcParams.setAuditOrganSize(65);
        rcParams.setAuditOrganLeft(2585);
        rcParams.setAuditOrganTop(1815);

        rcParams.setAuditDate("2020-04-03");
        rcParams.setAuditDateFamily("宋体");
        rcParams.setAuditDateSize(65);
        rcParams.setAuditDateLeft(2585);
        rcParams.setAuditDateTop(2015);
    }
}
