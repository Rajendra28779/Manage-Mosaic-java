/**
 * 
 */
package com.project.manage.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.manage.Bean.ResponseBean;
import com.project.manage.Model.MstUserModel;
import com.project.manage.Service.MstUserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * 
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api")
public class MstUserController {
	
	@Autowired
	private MstUserService mstuserservice;
	
	@GetMapping("/createUser")
	public ResponseBean createUser(/*@RequestBody MstUserModel usermodel*/) {
		ResponseBean bean=new ResponseBean();
		MstUserModel usermodel=new MstUserModel();
		try {
			bean=mstuserservice.createUser(usermodel);
		}catch (Exception e) {
			System.out.println(e);
			bean.setErrorMessage(e.getMessage());
		}
		return bean;
	}
	

}
