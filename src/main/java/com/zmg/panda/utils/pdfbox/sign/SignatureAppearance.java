package com.zmg.panda.utils.pdfbox.sign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Andy
 */
@Data
@ApiModel("印章附加信息")
public class SignatureAppearance {

	@ApiModelProperty("是否可见")
	private boolean visibleSignature;

	private String reason;
	private String contact;
	private String location;

}
