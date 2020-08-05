package com.zmg.panda.utils.pdfbox.sign;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CRLException;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureOptions;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.visible.PDVisibleSigProperties;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.visible.PDVisibleSignDesigner;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.DERUTCTime;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.CMSAttributes;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cert.jcajce.JcaX509CRLHolder;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoGeneratorBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.tsp.TSPException;

/**
 * @author Andy
 */
public class PdfboxSign implements SignatureInterface {

	private static BouncyCastleProvider provider = new BouncyCastleProvider();
	/**
	 * 私钥
	 */
	private PrivateKey privateKey;
	/**
	 * 证书链
	 */
	private Certificate[] certChain;
	/**
	 * 印章附加信息
	 */
	private SignatureAppearance signatureAppearance;
	/**
	 * 在第几页签名
	 */
	private Integer pageNo = 1;
	/**
	 * 摘要算法名称
	 */
	private String signatureAlgorithm;
	/**
	 * 图章x坐标
	 */
	private float rectllX;
	/**
	 * 图章Y坐标
	 */
	private float rectllY;
	/**
	 * 印章宽度
	 */
	private float imageWidth;
	/**
	 * 印章高度
	 */
	private float imageHight;


	public PdfboxSign(SignatureInformation signatureInfo) {
		try {
			this.signatureAppearance = signatureInfo.getSignatureAppearance();
			SignatureCryptoInfo cryptoInfo = signatureInfo.getSignatureCryptoInfo();
			// 证书链
			String certAlias = cryptoInfo.getCertAlias();
			// 密码
			char[] password = cryptoInfo.getPassword();
			KeyStore keystore = cryptoInfo.getKeystore();
			// 私钥
			privateKey = (PrivateKey) keystore.getKey(certAlias, password);
			// 得到证书链
			certChain = keystore.getCertificateChain(certAlias);

			rectllX = signatureInfo.getRectllX();
			rectllY = signatureInfo.getRectllY();
			imageWidth = signatureInfo.getImageWidth();
			imageHight = signatureInfo.getImageHight();
			pageNo = signatureInfo.getPageNo();
			signatureAlgorithm = signatureInfo.getSignatureAlgorithm();
		} catch (KeyStoreException e) {
			throw new RuntimeException("pdf签章密码错误！", e);
		} catch (UnrecoverableKeyException | NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 给pdf签章
	 * @param pdfInputStream
	 * @return
	 * @throws IOException
	 */
	public ByteArrayOutputStream signPDF(InputStream pdfInputStream) throws IOException {

		if (pdfInputStream == null) {
			throw new RuntimeException("找不到pdf文件");
		}
		// 加载pdf
		PDDocument doc = PDDocument.load(pdfInputStream);
		// 创建 签章dictionary
		PDSignature signature = new PDSignature();
		// default filter
		// signature.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);
		// subfilter for basic and PAdES Part 2 signatures
		signature.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED);
		// 签章附加信息
		signature.setName(signatureAppearance.getContact());
		signature.setLocation(signatureAppearance.getLocation());
		signature.setReason(signatureAppearance.getReason());
		// 设置签名时间
		signature.setSignDate(Calendar.getInstance());

		// 注册签名字典和签名接口
		SignatureOptions sigOpts;
		if (signatureAppearance.isVisibleSignature()) {
			sigOpts = createVisibleSignature(doc);
		} else {
			sigOpts = new SignatureOptions();
		}
		sigOpts.setPreferredSignatureSize(100000);

		doc.addSignature(signature, this, sigOpts);
		// 输出
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		doc.saveIncremental(byteArrayOutputStream);
		sigOpts.close();
		return byteArrayOutputStream;
	}

	private SignatureOptions createVisibleSignature(PDDocument doc) throws IOException {
		SignatureOptions sigOpts = new SignatureOptions();

		// why is this needed?
		PDVisibleSignDesigner visibleSig = new PDVisibleSignDesigner(doc,
				new FileInputStream(new File("D:\\tmp\\sign\\signature.png")), 1);

		visibleSig.xAxis(rectllX).yAxis(rectllY).width(imageWidth).height(imageHight).signatureFieldName("signature");

		// 设置印章附加信息
		PDVisibleSigProperties signatureProperties = new PDVisibleSigProperties();
		signatureProperties
				.signerName(signatureAppearance.getContact())
				.signerLocation(signatureAppearance.getLocation())
				.signatureReason(signatureAppearance.getReason())
				.preferredSize(0).page(1).visualSignEnabled(true)
				.setPdVisibleSignature(visibleSig).buildSignature();

		sigOpts.setVisualSignature(signatureProperties);
		sigOpts.setPage(pageNo - 1);
		return sigOpts;
	}

	@Override
	public byte[] sign(InputStream content) {
		CMSSignedDataGenerator gen = new CMSSignedDataGenerator();
		// CertificateChain
		List<Certificate> certList = Arrays.asList(certChain);
		try {
			// SHA1 SHA256 SH384 SHA512
			X509Certificate signingCert = (X509Certificate) certList.get(0);
			gen.addSignerInfoGenerator(new JcaSimpleSignerInfoGeneratorBuilder().setProvider("BC")
					.setSignedAttributeGenerator(new AttributeTable(new Hashtable<>()))
					.build(signatureAlgorithm, privateKey, signingCert));

			gen.addCertificates(new JcaCertStore(certList));

			X509CRL[] crls = fetchCRLs(signingCert);
			for (X509CRL crl : crls) {
				gen.addCRL(new JcaX509CRLHolder(crl));
			}

			CMSProcessableByteArray processable = new CMSProcessableByteArray(IOUtils.toByteArray(content));

			CMSSignedData signedData = gen.generate(processable, false);

			return signedData.getEncoded();
		} catch (Exception e) {
			throw new RuntimeException("Problem while preparing signature");
		}

	}

	private X509CRL[] fetchCRLs(X509Certificate signingCert)
			throws CertificateException, MalformedURLException, CRLException, IOException {
		List<String> crlList = CRLDistributionPointsExtractor.getCrlDistributionPoints(signingCert);
		List<X509CRL> crls = new ArrayList<X509CRL>();
		for (String crlUrl : crlList) {
			if (!crlUrl.startsWith("http")) {
				continue;
			}
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			URL url = new URL(crlUrl);
			X509CRL crl = (X509CRL) cf.generateCRL(url.openStream());
			crls.add(crl);
		}
		return crls.toArray(new X509CRL[] {});
	}

}
