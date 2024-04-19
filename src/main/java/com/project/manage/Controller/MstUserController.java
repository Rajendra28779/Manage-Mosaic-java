/**
 * 
 */
package com.project.manage.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping("/checkusername")
	public ResponseBean checkusername(@RequestParam(value="username",required = false) String username) {
		ResponseBean bean=new ResponseBean();
		try {
			bean=mstuserservice.checkusername(username);
		}catch (Exception e) {
			bean.setErrorMessage(e.getMessage());
			bean.setStatus(400);
			bean.setMessage("Something Went Wrong");
		}
		return bean;
	}
	
	@PostMapping("/createUser")
	public ResponseBean createUser(@RequestBody MstUserModel usermodel) {
		ResponseBean bean=new ResponseBean();
		try {
			bean=mstuserservice.createUser(usermodel);
		}catch (Exception e) {
			System.out.println(e);
			bean.setStatus(HttpStatus.BAD_REQUEST.value());
			bean.setMessage("Something Went Wrong");
			bean.setErrorMessage(e.getMessage());
		}
		return bean;
	}
	

}
