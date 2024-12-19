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

import lombok.Data;

/**
 * 
 */
@Data
@Entity
@Table(name="TBL_MST_HM_HOMEDETAILS")
public class HomeDetails {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "HOUSE_ID")
	private Long houseId;
	
	@Column(name = "OWNER_ID")
	private Long ownerid;
	
	@Column(name = "HOUSE_NAME")
	private String homeName;
	
	@Column(name = "ADDRESS")
	private String homeLocation;
	
	@Column(name = "TOTAL_ROOM")
	private Integer noofroom;
	
	@Column(name = "TOTAL_FLOOR")
	private Integer nooffloor;
	
	@Column(name = "CREATED_ON")
	private Date createdOn;	
	
	@Column(name = "STATUSFLAG")
	private Integer statutsFlag;
}
