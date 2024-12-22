/**
 * 
 */
package com.project.manage.ServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.project.manage.Model.TenantDetails;
import com.project.manage.Repository.HomeDetailsRepository;
import com.project.manage.Repository.HouseRoomdetailsRepository;
import com.project.manage.Repository.TenantDetailsRepository;
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
	private TenantDetailsRepository tenantdetailsRepo;
	
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
	public Map<String, Object> getdisplayhousedetails(Long userid, Long housedetails) throws Exception {
		Map<String,Object> mapobj=new HashMap<>();
		try {
			List<Object[]> objarr=homeroomdetailsrepo.getdisplayhousedetails(userid,housedetails);	
			Map<String,Object> roomdata = new HashMap<>();
			for(Object[] obj:objarr) {				
				roomdata.put("houseId", obj[0]);
				roomdata.put("houseName", obj[1]);
				roomdata.put("address", obj[2]);
				roomdata.put("roomCount", obj[3]);
				roomdata.put("memberCount", obj[4]);
				roomdata.put("totalamount", obj[5]);
			}
			
			List<Object[]> objarr1=homeroomdetailsrepo.getroomlistforhome(housedetails);
			List<Object> list=new ArrayList<>();
			for(Object[] obj:objarr1) {
				Map<String,Object> map = new HashMap<>();
				map.put("roomId", obj[0]);
				map.put("rommNo", obj[1]);
				map.put("floorNo", obj[2]);
				map.put("lastmtrRead", obj[3]);
				map.put("unitprice", obj[4]);
				map.put("tenantName", obj[5]);
				map.put("mobileNo", obj[6]);
				map.put("rentamount", obj[7]);
				map.put("effectiveDate", obj[8]);
				map.put("advamt", obj[9]);
				map.put("tenantId", obj[10]);
				list.add(map);
			}
			
			mapobj.put("status", HttpStatus.OK.value());
			mapobj.put("record", roomdata);
			mapobj.put("rommlist", list);
			mapobj.put("message", "Success");
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomCheckedException(e);
		}
		return mapobj;
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
			List<HouseRoomdetails> list=homeroomdetailsrepo.findByOwneruseridAndHouseid(houseId);
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
			homeroomdetailsrepo.save(roomdetails);
			bean.setStatus(HttpStatus.OK.value());
			bean.setMessage("Success");
		}catch (Exception e) {
			throw new CustomCheckedException(e);
		}
		return bean;
	}

	@Override
	public ResponseBean addtenanttoroom(TenantDetails tenantdetails, MultipartFile image1, MultipartFile image2,
			MultipartFile image3) throws Exception {
		ResponseBean bean=new ResponseBean();
		try {
			if(image1!=null) {
				String FileName=commenService.savetenantDoc(image1);
				tenantdetails.setAadharDetails(FileName);
			}else {
				bean.setStatus(HttpStatus.BAD_REQUEST.value());
				bean.setMessage("Please Upload Aadhar Card");
				return bean;
			}
			
			if(image2!=null) {
				String FileName=commenService.savetenantDoc(image2);
				tenantdetails.setAggrementDoc(FileName);
			}
			
			if(image3!=null) {
				String FileName=commenService.savetenantDoc(image3);
				tenantdetails.setOtherDoc(FileName);
			}
			
			tenantdetails.setCreatedOn(Calendar.getInstance().getTime());
			tenantdetails.setStatusFlag(0);
			tenantdetailsRepo.save(tenantdetails);
			bean.setStatus(HttpStatus.OK.value());
			bean.setMessage("Success");
		}catch (Exception e) {
			e.printStackTrace();
			throw new CustomCheckedException(e);
		}
		return bean;
	}

	@Override
	public ResponseBean gethousedetailsforuser(String phoneNo) throws Exception {
		ResponseBean bean=new ResponseBean();
		try {
			List<Object> list=new ArrayList<>();
			List<Object[]> objlist=tenantdetailsRepo.gethousedetailsforuser(phoneNo);
			for(Object[] obj : objlist) {
				Map<String,Object> map = new HashMap<>();
				map.put("tenantId", obj[0]);
				map.put("mobileNo", obj[1]);
				map.put("rentAmount", obj[2]);
				map.put("advAmount", obj[3]);
				map.put("efectdate", obj[4]);
				map.put("paymentdate", obj[5]);
				map.put("lastpaymentdate", obj[6]);
				map.put("houseName", obj[7]);
				map.put("roomNo", obj[8]);
				map.put("ownerName", obj[9]);
				map.put("ownerMobile", obj[10]);
				map.put("houseId", obj[11]);
				list.add(map);
			}
			bean.setStatus(HttpStatus.OK.value());
			bean.setMessage("Success");
			bean.setRecord(list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomCheckedException(e);
		}
		return bean;
	}

	@Override
	public ResponseBean onChangeroomgettenanrdata(Long roomId, Long houseId) throws Exception {
		ResponseBean bean = new ResponseBean();
		try {
			List<TenantDetails> objectlist=tenantdetailsRepo.onChangeroomgettenanrdata(roomId,houseId);
			TenantDetails tent = new TenantDetails();
			for(TenantDetails data:objectlist) {				
				tent.setFullName(data.getFullName());
				tent.setTenantId(data.getTenantId());
				tent.setMobileNo(data.getMobileNo());
			}
			bean.setStatus(HttpStatus.OK.value());
			bean.setMessage("Success");
			bean.setRecord(tent);
		} catch (Exception e) {
			throw new CustomCheckedException(e);
		}
		return bean;
	}

	@Override
	public ResponseBean viewtenanttoroom(Long userid) throws Exception {
		ResponseBean bean = new ResponseBean();
		try {
			List<Object[]> objectlist=tenantdetailsRepo.viewtenanttoroom(userid);
			List<Map<String, Object>> tenantRoomList = new ArrayList<>();
			for (Object[] row : objectlist) {
			    Map<String, Object> tenantRoomMap = new HashMap<>();
			    tenantRoomMap.put("tenantId", row[0]);
			    tenantRoomMap.put("houseName", row[1]);
			    tenantRoomMap.put("roomNo", row[2]);
			    tenantRoomMap.put("mobileNo", row[3]);
			    tenantRoomMap.put("altMobileNo", row[4]);
			    tenantRoomMap.put("noOfMember", row[5]);
			    tenantRoomMap.put("memberMobileNo", row[6]);
			    tenantRoomMap.put("rentAmount", row[7]);
			    tenantRoomMap.put("effectiveDate", row[8]);
			    tenantRoomMap.put("advAmount", row[9]);
			    tenantRoomMap.put("aadharDetails", row[10]);
			    tenantRoomMap.put("agreementDoc", row[11]);
			    tenantRoomMap.put("otherDoc", row[12]);
			    tenantRoomMap.put("fullName", row[13]);
			    tenantRoomList.add(tenantRoomMap);
			}
			bean.setStatus(HttpStatus.OK.value());
			bean.setMessage("Success");
			bean.setRecord(tenantRoomList);
		}catch (Exception e) {
			throw new CustomCheckedException(e);
		}
		return bean;
	}

}
