/**
 * 
 */
package com.project.manage.Service;

import java.util.Map;

import com.project.manage.Bean.HousedetailsBean;
import com.project.manage.Bean.ResponseBean;
import com.project.manage.Model.HomeDetails;
import com.project.manage.Model.HouseRoomdetails;
import com.project.manage.Util.CustomCheckedException;

/**
 * 
 */
public interface HomeDetailsService {

	ResponseBean addhomedetails(HomeDetails homeDetails) throws Exception;

	ResponseBean gethomedetails(Long userid) throws Exception;

	ResponseBean getdisplayhousedetails(Long userid, Long housedetails) throws Exception;

	ResponseBean gethousemasterData(Long userid) throws Exception;

	ResponseBean getroommasterData(Long userid, Long houseId) throws Exception;

	ResponseBean addroomdetails(HouseRoomdetails roomdetails) throws CustomCheckedException;

}
