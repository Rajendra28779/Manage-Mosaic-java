/**
 * 
 */
package com.project.manage.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * 
 */
@Data
@Entity
@Table(name="TBL_MST_USERDETAILS")
public class MstUserModel {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "USERID")
	private Long userId;
	
	@Column(name = "USERNAME")
	private String userName;
	
	@Column(name = "PASS_WORD")
	private String password;
	
	@Column(name = "GROUPID")
	private Integer groupId;
	
	@Column(name = "FIRSTNAME")
	private String firstName;
	
	@Column(name = "FULLNAME")
	private String fullname;
	
	@Column(name = "LASTNAME")
	private String lastName;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "MOBILE")
	private String mobileNo;
	
	@Column(name = "ADDRESS")
	private String address;	
	
	@Column(name = "CREATED_ON")
	private Date createdOn;	
	
	@Column(name = "STATUSFLAG")
	private Integer statusFlag;
}
