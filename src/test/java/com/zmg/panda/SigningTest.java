package com.zmg.panda;

import java.io.*;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.CertificateException;

import com.zmg.panda.utils.pdfbox.sign.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

public class SigningTest {

	
	private static final String PDF_FILE_PATH = "D:\\tmp\\sign\\test2.pdf";

	@Test
	public void testX() throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		InputStream is = new FileInputStream(new File(PDF_FILE_PATH));
		
		SignatureInformation signatureInfo = createSignatureInfo();
		// 初始化
		PdfboxSign pdfboxSign = new PdfboxSign(signatureInfo);
		
		ByteArrayOutputStream outputStream = pdfboxSign.signPDF(is);
		
		File outputFile = new File("D:\\tmp\\sign\\zmgSignTest.pdf");
		FileOutputStream fos = new FileOutputStream(outputFile);
		outputStream.writeTo(fos);
		fos.close();
	}
	
	@Test
	public void test() throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		InputStream is = new FileInputStream(PDF_FILE_PATH);
		
		SignatureInformation signatureInfo = createSignatureInfo();
		PdfboxSign signing = new PdfboxSign(signatureInfo);
		
		ByteArrayOutputStream baos = signing.signPDF(is);
		
		File outputFile = new File("D:\\tmp\\sign\\output.pdf");
		FileOutputStream fos = new FileOutputStream(outputFile);
		baos.writeTo(fos);
		fos.close();
	}

	@Test
	public void testNotVis() throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		InputStream is = new FileInputStream(PDF_FILE_PATH);
		SignatureInformation signatureInfo = createSignatureInfo();
		signatureInfo.getSignatureAppearance().setVisibleSignature(false);
		
		PdfboxSign signing = new PdfboxSign(signatureInfo);
		File outputFile = new File("D:\\tmp\\sign\\output_not_vis.pdf");
		ByteArrayOutputStream baos = signing.signPDF(is);
		FileOutputStream fos = new FileOutputStream(outputFile);
		baos.writeTo(fos);
		fos.close();
	}
	
	@Test
	public void noTsa() throws IOException{
		Security.addProvider(new BouncyCastleProvider());
		InputStream is = new FileInputStream(PDF_FILE_PATH);
		SignatureInformation signatureInfo = createSignatureInfo();
		signatureInfo.getSignatureAppearance().setVisibleSignature(false);

		PdfboxSign signing = new PdfboxSign(signatureInfo);
		File outputFile = new File("D:\\tmp\\sign\\output_no_tsa.pdf");
		ByteArrayOutputStream baos = signing.signPDF(is);
		FileOutputStream fos = new FileOutputStream(outputFile);
		baos.writeTo(fos);
		fos.close();
	}

	/**
	 * 初始化签章信息
	 * @return
	 */
	private SignatureInformation createSignatureInfo() {
		SignatureInformation sigInfo = new SignatureInformation();
		// 签章证书信息
		sigInfo.setSignatureCryptoInfo(createSigCryptoInfo());
		// 签章附加信息
		sigInfo.setSignatureAppearance(createSigAppearance());

		sigInfo.setPageNo(1);
		sigInfo.setRectllX(400);
		sigInfo.setRectllY(50);
		sigInfo.setImageWidth(100);
		sigInfo.setImageHight(100);
		sigInfo.setSignatureAlgorithm(SignatureAlgorithm.SHA1);
		return sigInfo;
	}

	/**
	 * 签章附加信息
	 * @return
	 */
	private SignatureAppearance createSigAppearance() {
		SignatureAppearance sigApp = new SignatureAppearance();
		sigApp.setContact("Andy123456");
		sigApp.setLocation("I am from China");
		sigApp.setReason("I am a panda");
		sigApp.setVisibleSignature(true);
		return sigApp;
	}

	/**
	 * 签章证书信息
	 * @return
	 */
	private SignatureCryptoInfo createSigCryptoInfo() {
		String password = "123456";

		SignatureCryptoInfo sigCryptoInfo = new SignatureCryptoInfo();
		// sigCryptoInfo.setCertAlias("Heiri Muster (Qualified Signature)");
		KeyStore keyStore = loadKeystore();
		try {
			keyStore.load(new FileInputStream(new File("D:\\tmp\\sign\\signature.p12")), password.toCharArray());
			sigCryptoInfo.setCertAlias(keyStore.aliases().nextElement());
			sigCryptoInfo.setKeystore(keyStore);
			sigCryptoInfo.setPassword(password.toCharArray());
			sigCryptoInfo.setProvider(keyStore.getProvider());
		} catch (NoSuchAlgorithmException | CertificateException | IOException | KeyStoreException e) {
			throw new RuntimeException(e);
		}
		return sigCryptoInfo;
	}

	private KeyStore loadKeystore() {
		try {
			return KeyStore.getInstance("PKCS12");
		} catch (KeyStoreException ex) {
			throw new RuntimeException(ex);
		}
	}

}
