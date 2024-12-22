/**
 * 
 */
package com.project.manage.Model;
import java.util.Date;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "TBL_MST_HM_TENANTDETAILS")
public class TenantDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TENANT_ID")
    private Long tenantId;

    @Column(name = "OWNER_ID")
    private Long ownerId;

    @Column(name = "HOUSE_ID")
    private Long houseId;

    @Column(name = "ROOM_ID")
    private Long roomId;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "MOBILE_NO")
    private String mobileNo;

    @Column(name = "ALT_MOBILE_NO")
    private String altMobileNo;

    @Column(name = "NO_OF_MEMBER")
    private String noOfMember;

    @Column(name = "MEMBER_MOBILE_NO")
    private String memberMobileNo;

    @Column(name = "RENT_AMOUNT")
    private Long rentAmount;

    @Column(name = "ADV_AMOUNT")
    private Long advAmount;

    @Column(name = "EFFECTIVE_DATE")
    private Date effectiveDate;

    @Column(name = "AADHAR_DETAILS", length = 50)
    private String aadharDetails;

    @Column(name = "AGGREMENT_DOC", length = 50)
    private String aggrementDoc;

    @Column(name = "OTHER_DOC", length = 50)
    private String otherDoc;

    @Column(name = "CREATED_ON")
    private Date createdOn;

    @Column(name = "STATUSFLAG")
    private Integer statusFlag;
}
