/**
 * 
 */
package com.project.manage.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.manage.Bean.ResponseBean;
import com.project.manage.Model.MstUserModel;
import com.project.manage.Service.LoginService;
import com.project.manage.Util.EncryptionUtils;

/**
 * 
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/login")
public class LoginController {
	
	@Autowired
	private LoginService loginserv;

	@PostMapping("/loginapi")
	public Map<String,Object> loginapi(@RequestBody MstUserModel usermodel) {
		Map<String,Object> bean=new HashMap<>();
		try {
			usermodel.setUserName(EncryptionUtils.decryptCode(usermodel.getUserName()));
			usermodel.setPassword(EncryptionUtils.decryptCode(usermodel.getPassword()));
			bean=loginserv.loginapi(usermodel);
		}catch (Exception e) {
			System.out.println(e);
		}
		return bean;
	}
}
