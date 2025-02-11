/**
 * 
 */
package com.project.manage.Model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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
