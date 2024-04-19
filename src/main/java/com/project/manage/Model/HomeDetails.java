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
@Table(name="tbl_mst_user_homedetails")
public class HomeDetails {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "details_id")
//	@GenericGenerator(name = "catInc", strategy = "increment")
//	@GeneratedValue(generator = "catInc")
	private Long detailsid;
	
	@Column(name = "userid")
	private Long userid;
	
	@Column(name = "home_name")
	private String homeName;
	
	@Column(name = "home_location")
	private String homeLocation;
	
	@Column(name = "no_of_room")
	private Integer noofroom;
	
	@Column(name = "statuts_flag")
	private Integer statutsFlag;
}
