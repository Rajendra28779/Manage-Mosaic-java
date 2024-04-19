/**
 * 
 */
package com.project.manage.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.manage.Bean.HousedetailsBean;
import com.project.manage.Bean.ResponseBean;
import com.project.manage.Model.HomeDetails;
import com.project.manage.Model.MstUserModel;
import com.project.manage.Service.HomeDetailsService;
import com.project.manage.Util.EncryptionUtils;

/**
 * 
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api")
public class HomeDetailsController {
	
	@Autowired
	private HomeDetailsService homedetailsserv;

	@PostMapping("/addhomedetails")
	public ResponseBean loginapi(@RequestBody HomeDetails homeDetails) {
		ResponseBean bean=new ResponseBean();
		try {
			bean=homedetailsserv.addhomedetails(homeDetails);
		}catch (Exception e) {
			bean.setStatus(HttpStatus.BAD_REQUEST.value());
			bean.setMessage("Something Went Wrong");
			bean.setErrorMessage(e.getMessage());
		}
		return bean;
	}
	
	@GetMapping("/gethomedetails")
	public ResponseBean gethomedetails(@RequestParam(value = "userid",required = false) Long userid) {
		ResponseBean bean=new ResponseBean();
		try {
			bean=homedetailsserv.gethomedetails(userid);
		}catch (Exception e) {
			bean.setStatus(HttpStatus.BAD_REQUEST.value());
			bean.setMessage("Something Went Wrong");
			bean.setErrorMessage(e.getMessage());
		}
		return bean;
	}
	
	@GetMapping("/inactiveroomdetails")
	public ResponseBean inactiveroomdetails(@RequestParam(value = "detailsid",required = false) Long detailsid) {
		ResponseBean bean=new ResponseBean();
		try {
			bean=homedetailsserv.inactiveroomdetails(detailsid);
		}catch (Exception e) {
			bean.setStatus(HttpStatus.BAD_REQUEST.value());
			bean.setMessage("Something Went Wrong");
			bean.setErrorMessage(e.getMessage());
		}
		return bean;
	}
	
	@PostMapping("/submitdetails")
	public ResponseBean submitdetails(@RequestBody HousedetailsBean housedetailsbean) {
		ResponseBean bean=new ResponseBean();
		try {
			System.out.println(housedetailsbean);
//			bean=homedetailsserv.submitdetails(homeDetails);
		}catch (Exception e) {
			bean.setStatus(HttpStatus.BAD_REQUEST.value());
			bean.setMessage("Something Went Wrong");
			bean.setErrorMessage(e.getMessage());
		}
		return bean;
	}
}
