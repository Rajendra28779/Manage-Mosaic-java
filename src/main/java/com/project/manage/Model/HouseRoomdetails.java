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
 * Rajendra
 */
@Data
@Entity
@Table(name="tbl_mst_house_room_details")
public class HouseRoomdetails {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "room_details_id")
//	@GenericGenerator(name = "catInc", strategy = "increment")
//	@GeneratedValue(generator = "catInc")
	private Long rooddetailsId;
	
	@Column(name = "username")
	private String roomNo;
	
	@Column(name = "allot_person")
	private String allotperson;
	
	@Column(name = "room_rent")
	private Long roomrent;
	
	@Column(name = "owner_userid")
	private Long owneruserid;
	
	@Column(name = "houseid")
	private Long houseid;
	
	@Column(name = "create_on")
	private Date createon;
	
	@Column(name = "status_flag")
	private Integer statusflag;
}
