/**
 * 
 */
package com.project.manage.Service;

import org.springframework.security.core.userdetails.UserDetails;

import com.project.manage.Bean.ResponseBean;
import com.project.manage.Model.MstUserModel;

/**
 * 
 */
public interface MstUserService {

	ResponseBean createUser(MstUserModel usermodel) throws Exception;

	ResponseBean checkusername(String username) throws Exception;

}
