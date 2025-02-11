/**
 * 
 */
package com.project.manage.Model;
import java.util.Date;

import jakarta.persistence.*;

import lombok.Data;

/**
 * Rajendra
 */
@Entity
@Data
@Table(name = "TBL_MST_OTPLOG")
public class OtpLog {

	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "OTPID")
	    private Long otpId;

	    @Column(name = "USERNAME")
	    private String username;

	    @Column(name = "OTPVAL")
	    private String otpVal;

	    @Column(name = "CREATED_ON")
	    private Date createdOn;

	    @Column(name = "ATTEMPT")
	    private Integer attempt;

	    @Column(name = "VERIFYSTATUS")
	    private Integer verifyStatus;
}
