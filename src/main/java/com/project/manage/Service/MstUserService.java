/**
 * 
 */
package com.project.manage.Service;

import com.project.manage.Bean.ResponseBean;
import com.project.manage.Model.MstUserModel;

/**
 * 
 */
public interface MstUserService {

	ResponseBean createUser(MstUserModel usermodel) throws Exception;

}
