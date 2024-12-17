/**
 * 
 */
package com.project.manage.Service;

import java.util.Map;

import com.project.manage.Bean.HousedetailsBean;
import com.project.manage.Bean.ResponseBean;
import com.project.manage.Model.HomeDetails;

/**
 * 
 */
public interface HomeDetailsService {

	ResponseBean addhomedetails(HomeDetails homeDetails) throws Exception;

	ResponseBean gethomedetails(Long userid) throws Exception;

	ResponseBean inactiveroomdetails(Long detailsid) throws Exception;

	ResponseBean submitdetails(HousedetailsBean housedetailsbean) throws Exception;

	ResponseBean getroomdetails(Long userid, Long housedetails) throws Exception;

}
