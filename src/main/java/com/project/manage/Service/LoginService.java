/**
 * 
 */
package com.project.manage.Service;

import java.util.Map;

import com.project.manage.Bean.ResponseBean;
import com.project.manage.Model.MstUserModel;
import com.project.manage.Util.CustomCheckedException;

/**
 * 
 */
public interface LoginService {

	Map<String, Object> loginapi(MstUserModel usermodel) throws Exception;

	Map<String, Object> loginfrmgoogle(String email, String name) throws CustomCheckedException;

}
