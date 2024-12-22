/**
 * 
 */
package com.project.manage.Service;

import com.project.manage.Bean.ResponseBean;
import com.project.manage.Model.RepairRequest;
import com.project.manage.Util.CustomCheckedException;

/**
 * 
 */
public interface CompliantService {
	
	ResponseBean savehousemaintancerqst(RepairRequest repaisrqst) throws CustomCheckedException;

	ResponseBean getrqstdetailsfortrackt(Long userid) throws CustomCheckedException;

	ResponseBean getrequestdetailsForowner(Long userid) throws CustomCheckedException;

	ResponseBean takeactionagainestrequest(Integer actiontype, Long userid, Long rqstId) throws CustomCheckedException;

}
