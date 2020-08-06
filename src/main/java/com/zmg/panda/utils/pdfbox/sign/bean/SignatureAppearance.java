package com.zmg.panda.utils.pdfbox.sign.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Andy
 */
@Data
@ApiModel("印章附加信息")
public class SignatureAppearance {

	private String reason;
	private String contact;
	private String location;

	@ApiModelProperty("是否可见")
	private boolean visibleSignature = true;


}
