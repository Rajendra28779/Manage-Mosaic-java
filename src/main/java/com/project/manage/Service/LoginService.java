/**
 * 
 */
package com.project.manage.Service;

import java.util.Map;

import com.project.manage.Model.MstUserModel;

/**
 * 
 */
public interface LoginService {

	Map<String, Object> loginapi(MstUserModel usermodel) throws Exception;

}
