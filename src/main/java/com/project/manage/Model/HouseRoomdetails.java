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

import lombok.Data;

/**
 * Rajendra
 */
@Data
@Entity
@Table(name="TBL_MST_HM_ROOMDETAILS")
public class HouseRoomdetails {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ROOM_ID")
	private Long roomId;
	
	@Column(name = "OWNER_ID")
	private Long ownerId;
	
	@Column(name = "HOUSE_ID")
	private Long houseId;
	
	@Column(name = "ROOM_NO")
	private String roomno;
	
	@Column(name = "FLOOR_NO")
	private String floorNo;
	
	@Column(name = "LAST_MTR_READ")
	private Long lastmtrRead;
	
	@Column(name = "UNIT_PER_PRICE")
	private Integer unitPrice;
	
	@Column(name = "ROOM_IMG_1")
	private String image01;
	
	@Column(name = "ROOM_IMG_2")
	private String image02;
	
	@Column(name = "ROOM_IMG_3")
	private String image03;
	
	@Column(name = "ROOM_IMG_4")
	private String image04;
	
	@Column(name = "ROOM_IMG_5")
	private String image05;
	
	@Column(name = "CREATED_ON")
	private Date createdOn;	
	
	@Column(name = "STATUSFLAG")
	private Integer statusflag;
}
