/**
 * 
 */
package com.project.manage.Service;

import java.util.Map;

import com.project.manage.Bean.ResponseBean;
import com.project.manage.Util.CustomCheckedException;

/**
 * Rajendra
 */
public interface CommenService {

	ResponseBean rqstforcontact(Map<String, Object> mapobj) throws CustomCheckedException ;

}
