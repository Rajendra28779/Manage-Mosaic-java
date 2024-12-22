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
import org.springframework.web.multipart.MultipartFile;

import com.project.manage.Bean.HousedetailsBean;
import com.project.manage.Bean.ResponseBean;
import com.project.manage.Model.HomeDetails;
import com.project.manage.Model.HouseRoomdetails;
import com.project.manage.Model.MstUserModel;
import com.project.manage.Model.TenantDetails;
import com.project.manage.Repository.MstUserRepository;
import com.project.manage.Service.HomeDetailsService;
import com.project.manage.Util.EncryptionUtils;
import com.project.manage.config.JwtFilter;

/**
 * 
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/api")
public class HomeDetailsController {
	
	@Autowired
	private HomeDetailsService homedetailsserv;
	
	@Autowired
	private MstUserRepository mastuserrepo;

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
	public Map<String, Object> getdisplayhousedetails(@RequestParam(value = "userid",required = false) Long userid,
			@RequestParam(value = "houseId",required = false) Long houseId) {
		Map<String, Object> bean=new HashMap<>();
		try {
			bean=homedetailsserv.getdisplayhousedetails(userid,houseId);
		}catch (Exception e) {
			bean.put("status",HttpStatus.BAD_REQUEST.value());
			bean.put("message","Something Went Wrong");
			bean.put("error",e.getMessage());
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
	public ResponseBean addroomforhome(HouseRoomdetails roomdetails,
			@RequestParam(value = "image1",required = false) MultipartFile image1,
			@RequestParam(value = "image2",required = false) MultipartFile image2,
			@RequestParam(value = "image3",required = false) MultipartFile image3,
			@RequestParam(value = "image4",required = false) MultipartFile image4,
			@RequestParam(value = "image5",required = false) MultipartFile image5
			) {
		ResponseBean bean=new ResponseBean();
		try {
			bean=homedetailsserv.addroomdetails(roomdetails,image1,image2,image3,image4,image5);
		}catch (Exception e) {
			bean.setStatus(HttpStatus.BAD_REQUEST.value());
			bean.setMessage("Something Went Wrong");
			bean.setErrorMessage(e.getMessage());
		}
		return bean;
	} 
	
	@PostMapping("/addtenanttoroom")
	public ResponseBean addtenanttoroom(TenantDetails tenantdetails,
			@RequestParam(value = "image1",required = false) MultipartFile image1,
			@RequestParam(value = "image2",required = false) MultipartFile image2,
			@RequestParam(value = "image3",required = false) MultipartFile image3
			) {
		ResponseBean bean=new ResponseBean();
		try {
			bean=homedetailsserv.addtenanttoroom(tenantdetails,image1,image2,image3);
		}catch (Exception e) {
			bean.setStatus(HttpStatus.BAD_REQUEST.value());
			bean.setMessage("Something Went Wrong");
			bean.setErrorMessage(e.getMessage());
		}
		return bean;
	} 
	
	@GetMapping("/viewtenanttoroom")
	public ResponseBean addtenanttoroom(@RequestParam(value = "houseId",required = false) Long houseId,
			@RequestParam(value = "roomId",required = false) Long roomId) {
		ResponseBean bean=new ResponseBean();
		try {
			String usename=JwtFilter.getusername();
			Long userid= mastuserrepo.getuserIdfromuserName(usename);
			bean=homedetailsserv.viewtenanttoroom(userid,houseId,roomId);
		}catch (Exception e) {
			e.printStackTrace();
			bean.setStatus(HttpStatus.BAD_REQUEST.value());
			bean.setMessage("Something Went Wrong");
			bean.setErrorMessage(e.getMessage());
		}
		return bean;
	} 
	
	@GetMapping("/gethousedetailsforuser")
	public ResponseBean gethousedetailsforuser(@RequestParam(value = "phoneNo",required = false) String phoneNo) {
		ResponseBean bean=new ResponseBean();
		try {
			bean=homedetailsserv.gethousedetailsforuser(phoneNo);
		}catch (Exception e) {
			bean.setStatus(HttpStatus.BAD_REQUEST.value());
			bean.setMessage("Something Went Wrong");
			bean.setErrorMessage(e.getMessage());
		}
		return bean;
	}
	
	@GetMapping("/onChangeroomgettenanrdata")
	public ResponseBean onChangeroomgettenanrdata(@RequestParam(value = "roomId",required = false) Long roomId,
			@RequestParam(value = "houseId",required = false) Long houseId) {
		ResponseBean bean=new ResponseBean();
		try {
			bean=homedetailsserv.onChangeroomgettenanrdata(roomId,houseId);
		}catch (Exception e) {
			bean.setStatus(HttpStatus.BAD_REQUEST.value());
			bean.setMessage("Something Went Wrong");
			bean.setErrorMessage(e.getMessage());
		}
		return bean;
	}
	
	@GetMapping("/checkpendingbalanace")
	public ResponseBean checkpendingbalanace(@RequestParam(value = "roomId",required = false) Long roomId) {
		ResponseBean bean=new ResponseBean();
		try {
			bean=homedetailsserv.checkpendingbalanace(roomId);
		}catch (Exception e) {
			bean.setStatus(HttpStatus.BAD_REQUEST.value());
			bean.setMessage("Something Went Wrong");
			bean.setErrorMessage(e.getMessage());
		}
		return bean;
	}
}
