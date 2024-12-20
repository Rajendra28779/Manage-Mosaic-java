/**
 * 
 */
package com.project.manage.ServiceImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.manage.Bean.HousedetailsBean;
import com.project.manage.Bean.ResponseBean;
import com.project.manage.Model.HomeDetails;
import com.project.manage.Model.HouseRoomdetails;
import com.project.manage.Repository.HomeDetailsRepository;
import com.project.manage.Repository.HouseRoomdetailsRepository;
import com.project.manage.Service.CommenService;
import com.project.manage.Service.HomeDetailsService;
import com.project.manage.Util.CustomCheckedException;

/**
 * 
 */
@Service
public class HomeDetailsServiceImpl implements HomeDetailsService{

	@Autowired
	private HomeDetailsRepository homeDetailsrepo;
	
	@Autowired
	private HouseRoomdetailsRepository homeroomdetailsrepo;
	
	@Autowired
	private CommenService commenService;
	
	@Override
	public ResponseBean addhomedetails(HomeDetails homeDetails) throws Exception {
		ResponseBean bean=new ResponseBean();
		try {
			homeDetails.setStatutsFlag(0);
			homeDetailsrepo.save(homeDetails);
			bean.setStatus(HttpStatus.OK.value());
			bean.setMessage("Successful");
		}catch (Exception e) {
			throw new CustomCheckedException(e);
		}
		return bean;
	}

	@Override
	public ResponseBean gethomedetails(Long userid) throws Exception {
		ResponseBean bean=new ResponseBean();
		try {
			bean.setRecord(homeDetailsrepo.gethomelist(userid));
			bean.setStatus(HttpStatus.OK.value());
			bean.setMessage("Success");			
		}catch (Exception e) {
			throw new CustomCheckedException(e);
		}
		return bean;
	}	

	@Override
	public ResponseBean getdisplayhousedetails(Long userid, Long housedetails) throws Exception {
		ResponseBean bean=new ResponseBean();
		List<Object> list=new ArrayList<>();
		try {
			List<Object[]> objlist=homeroomdetailsrepo.getdisplayhousedetails(userid,housedetails);
			for(Object[] obj:objlist) {
				Map<String,Object> map = new HashMap<>();
				
//				SELECT H.HOUSE_ID,H.HOUSE_NAME,H.ADDRESS,COUNT(*)
//				FROM TBL_MST_HM_HOMEDETAILS H
//				LEFT JOIN TBL_MST_HM_ROOMDETAILS R ON H.HOUSE_ID=R.HOUSE_ID
//				WHERE H.HOUSE_ID=2 AND H.OWNER_ID=1
//				ORDER BY H.HOUSE_ID,H.HOUSE_NAME,H.ADDRESS;
				
				list.add(map);
			}
			bean.setStatus(HttpStatus.OK.value());
			bean.setRecord(list);
			bean.setMessage("Success");
		} catch (Exception e) {
			throw new CustomCheckedException(e);
		}
		return bean;
	}

	@Override
	public ResponseBean gethousemasterData(Long userid) throws Exception {
		ResponseBean bean=new ResponseBean();
		try {
			List<Object> objlist=new ArrayList<>();
			List<HomeDetails> list=homeDetailsrepo.gethomelist(userid);
			for(HomeDetails dap:list) {
				Map<String,Object> map=new HashMap<>();
				map.put("homeId",dap.getHouseId());
				map.put("homeName",dap.getHomeName());
				map.put("location",dap.getHomeLocation());
				objlist.add(map);
			}			
			bean.setStatus(HttpStatus.OK.value());
			bean.setRecord(objlist);
			bean.setMessage("Success");
		} catch (Exception e) {
			throw new CustomCheckedException(e);
		}
		return bean;
	}

	@Override
	public ResponseBean getroommasterData(Long userid, Long houseId) throws Exception {
		ResponseBean bean=new ResponseBean();
		try {
			List<Object> objlist=new ArrayList<>();
			List<HouseRoomdetails> list=homeroomdetailsrepo.findByOwneruseridAndHouseid(userid,houseId);
			for(HouseRoomdetails dap:list) {
				Map<String,Object> map=new HashMap<>();
				map.put("roomId",dap.getRoomId());
				map.put("houseId",dap.getHouseId());
				map.put("roomNo",dap.getRoomno());
				objlist.add(map);
			}			
			bean.setStatus(HttpStatus.OK.value());
			bean.setRecord(objlist);
			bean.setMessage("Success");
		} catch (Exception e) {
			throw new CustomCheckedException(e);
		}
		return bean;
	}

	@Override
	public ResponseBean addroomdetails(HouseRoomdetails roomdetails, MultipartFile image1, MultipartFile image2, 
			MultipartFile image3, MultipartFile image4, MultipartFile image5) throws CustomCheckedException {
		ResponseBean bean=new ResponseBean();
		try {
			
			if(image1!=null) {
				String FileName=commenService.saveroomimage(image1,roomdetails.getOwnerId(),roomdetails.getHouseId());
				roomdetails.setImage01(FileName);
			}else {
				bean.setStatus(HttpStatus.BAD_REQUEST.value());
				bean.setMessage("Please Upload Image1");
				return bean;
			}
			
			if(image2!=null) {
				String FileName=commenService.saveroomimage(image2,roomdetails.getOwnerId(),roomdetails.getHouseId());
				roomdetails.setImage02(FileName);
			}else {
				bean.setStatus(HttpStatus.BAD_REQUEST.value());
				bean.setMessage("Please Upload Image2");
				return bean;
			}
			
			if(image3!=null) {
				String FileName=commenService.saveroomimage(image3,roomdetails.getOwnerId(),roomdetails.getHouseId());
				roomdetails.setImage03(FileName);
			}
			
			if(image4!=null) {
				String FileName=commenService.saveroomimage(image4,roomdetails.getOwnerId(),roomdetails.getHouseId());
				roomdetails.setImage04(FileName);
			}
			
			if(image5!=null) {
				String FileName=commenService.saveroomimage(image5,roomdetails.getOwnerId(),roomdetails.getHouseId());
				roomdetails.setImage05(FileName);
			}			
			
			
			roomdetails.setCreatedOn(Calendar.getInstance().getTime());
			roomdetails.setStatusflag(0);
			System.out.println(roomdetails);
//			homeroomdetailsrepo.save(roomdetails);
			bean.setStatus(HttpStatus.OK.value());
			bean.setMessage("Success");
		}catch (Exception e) {
			throw new CustomCheckedException(e);
		}
		return bean;
	}

}
