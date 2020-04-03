package com.zmg20200111.panda.utils.graphics.bean;

import lombok.Data;

@Data
public class RegistrationCertificate {
    private String organName;
    private int organNameSize;
    private String organNameFamily;
    private int organNameTop;
    private int organNameBoxLeft;
    private int organNameBoxReght;

    private String socialCreditCode;
    private int socialCreditCodeSize;
    private String socialCreditFamily;
    private int socialCreditLeft;
    private int socialCreditTop;

    private String auditOrgan;
    private int auditOrganSize;
    private String auditOrganFamily;
    private int auditOrganLeft;
    private int auditOrganTop;

    private String auditDate;
    private int auditDateSize;
    private String auditDateFamily;
    private int auditDateLeft;
    private int auditDateTop;



    public String getOrganName() {
        if (this.organName.indexOf("仲裁委员会") > 0) {
            return this.organName.replace("仲裁委员会", "");
        }
        if (this.organName.indexOf("仲裁机构") > 0) {
            return this.organName.replace("仲裁机构", "");
        }
        if (this.organName.indexOf("仲裁委") > 0) {
            return this.organName.replace("仲裁委", "");
        }
        return this.organName;
    }
}
