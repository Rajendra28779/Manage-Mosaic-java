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
import com.project.manage.Model.HouseRoomdetails;
import com.project.manage.Model.MstUserModel;
import com.project.manage.Service.HomeDetailsService;
import com.project.manage.Util.EncryptionUtils;

/**
 * 
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/api")
public class HomeDetailsController {
	
	@Autowired
	private HomeDetailsService homedetailsserv;

	@PostMapping("/addnewhome")
	public ResponseBean addnewhome(@RequestBody HomeDetails homeDetails) {
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
	
	@GetMapping("/getdisplayhousedetails")
	public ResponseBean getdisplayhousedetails(@RequestParam(value = "userid",required = false) Long userid,
			@RequestParam(value = "houseId",required = false) Long houseId) {
		ResponseBean bean=new ResponseBean();
		try {
			bean=homedetailsserv.getdisplayhousedetails(userid,houseId);
		}catch (Exception e) {
			bean.setStatus(HttpStatus.BAD_REQUEST.value());
			bean.setMessage("Something Went Wrong");
			bean.setErrorMessage(e.getMessage());
		}
		return bean;
	}
	
	@GetMapping("/gethousemasterData")
	public ResponseBean gethousemasterData(@RequestParam(value = "userId",required = false) Long userid) {
		ResponseBean bean=new ResponseBean();
		try {
			bean=homedetailsserv.gethousemasterData(userid);
		}catch (Exception e) {
			bean.setStatus(HttpStatus.BAD_REQUEST.value());
			bean.setMessage("Something Went Wrong");
			bean.setErrorMessage(e.getMessage());
		}
		return bean;
	}
	
	@GetMapping("/getroommasterData")
	public ResponseBean getroommasterData(@RequestParam(value = "userId",required = false) Long userid,
			@RequestParam(value = "houseId",required = false) Long houseId) {
		ResponseBean bean=new ResponseBean();
		try {
			bean=homedetailsserv.getroommasterData(userid,houseId);
		}catch (Exception e) {
			bean.setStatus(HttpStatus.BAD_REQUEST.value());
			bean.setMessage("Something Went Wrong");
			bean.setErrorMessage(e.getMessage());
		}
		return bean;
	}
	
	@PostMapping("/addroomforhome")
	public ResponseBean addroomforhome(@RequestBody HouseRoomdetails roomdetails) {
		ResponseBean bean=new ResponseBean();
		try {
			bean=homedetailsserv.addroomdetails(roomdetails);
		}catch (Exception e) {
			bean.setStatus(HttpStatus.BAD_REQUEST.value());
			bean.setMessage("Something Went Wrong");
			bean.setErrorMessage(e.getMessage());
		}
		return bean;
	}
}
