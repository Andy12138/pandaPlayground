package com.zmg.panda.utils.pdfbox.sign;

import lombok.Data;

/**
 * @author Andy
 */
@Data
public class SignatureInformation {
	/**
	 * 印章信息(证书流、图片、密码等...)
	 */
	private SignatureCryptoInfo signatureCryptoInfo;
	/**
	 * 印章附加信息（理由、地址等...）
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

}
