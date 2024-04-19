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
@Table(name="tbl_mst_userdata")
public class MstUserModel {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "userid")
//	@GenericGenerator(name = "catInc", strategy = "increment")
//	@GeneratedValue(generator = "catInc")
	private Long userId;
	
	@Column(name = "username")
	private String userName;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "group_Id")
	private Integer groupId;
	
	@Column(name = "firstname")
	private String firstName;
	
	@Column(name = "fullname")
	private String fullname;
	
	@Column(name = "lastname")
	private String lastName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "mobileno")
	private String mobileNo;
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "created_by")
	private Long createdBy;
	
	@Column(name = "created_on")	
	private Date createdOn;
	
	@Column(name = "updated_by")
	private Long updatedBy;
	
	@Column(name = "updated_on")
	private Date updatedOn;
	
	@Column(name = "status_flag")
	private Integer statusFlag;
}
