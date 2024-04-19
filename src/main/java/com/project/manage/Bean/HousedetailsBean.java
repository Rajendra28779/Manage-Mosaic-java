/**
 * 
 */
package com.project.manage.Bean;

import java.util.List;

import com.project.manage.Model.HouseRoomdetails;

import lombok.Data;

/**
 * 
 */
@Data
public class HousedetailsBean {

	private Long userid;
	private List<HouseRoomdetails> roomdetails;
	private Long housedetails;
}
