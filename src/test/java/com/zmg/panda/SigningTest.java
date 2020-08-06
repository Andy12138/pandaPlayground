package com.zmg.panda;

import com.zmg.panda.utils.pdfbox.sign.PdfboxSignManager;
import com.zmg.panda.utils.pdfbox.sign.bean.SignatureAlgorithm;
import com.zmg.panda.utils.pdfbox.sign.bean.SignatureAppearance;
import com.zmg.panda.utils.pdfbox.sign.bean.SignatureInfo;
import org.junit.Test;

import java.io.*;

public class SigningTest {

	
	private static final String PDF_FILE_PATH = "D:\\tmp\\sign\\test2.pdf";

	@Test
	public void testX() throws Exception {
		// 初始化
		SignatureInfo signInfo = initSignatureInfo();

		PdfboxSignManager pdfboxSign = new PdfboxSignManager(signInfo);

		ByteArrayOutputStream outputStream = pdfboxSign.signPDF(signInfo);
		
		File outputFile = new File("D:\\tmp\\sign\\zmgSignTest.pdf");
		FileOutputStream fos = new FileOutputStream(outputFile);
		outputStream.writeTo(fos);
		fos.close();
	}

	/**
	 * 初始化签章信息
	 * @return
	 */
	private SignatureInfo initSignatureInfo() throws FileNotFoundException {
		SignatureInfo signInfo = new SignatureInfo();
		signInfo.setCertificateInputStream(new FileInputStream(new File("D:\\tmp\\sign\\signature.p12")));
		signInfo.setPassword("123456");
		signInfo.setImageInputStream(new FileInputStream(new File("D:\\tmp\\sign\\signature.png")));
		signInfo.setSignPdfInputStream(new FileInputStream(new File(PDF_FILE_PATH)));
		// 签章附加信息
		signInfo.setSignatureAppearance(createSigAppearance());
		signInfo.setPageNo(1);
		signInfo.setRectllX(400);
		signInfo.setRectllY(50);
		signInfo.setImageWidth(100);
		signInfo.setImageHight(100);
		signInfo.setSignatureAlgorithm(SignatureAlgorithm.SHA1);
		return signInfo;
	}

	/**
	 * 签章附加信息
	 * @return
	 */
	private SignatureAppearance createSigAppearance() {
		SignatureAppearance sigApp = new SignatureAppearance();
		sigApp.setContact("Andy123456");
		sigApp.setLocation("名桂");
		sigApp.setReason("I am so cool!!!!!!");
		sigApp.setVisibleSignature(true);
		return sigApp;
	}


}
