package com.zmg.panda.utils.pdfbox.sign;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.security.KeyStore;
import java.security.Provider;


/**
 * @author Andy
 */
@Data
@ApiModel("签章信息")
public class SignatureCryptoInfo {
	
	private Provider provider;
	private KeyStore keystore;
	private char[] password;
	private String certAlias;

}
