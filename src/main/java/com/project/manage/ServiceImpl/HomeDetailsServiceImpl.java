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
import com.project.manage.Model.PaymentDetails;
import com.project.manage.Model.TenantDetails;
import com.project.manage.Repository.HomeDetailsRepository;
import com.project.manage.Repository.HouseRoomdetailsRepository;
import com.project.manage.Repository.PaymentDetailsRepository;
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
	private PaymentDetailsRepository paymentsrepo;
	
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
				map.put("roomimg1", obj[11]);
				map.put("roomimg2", obj[12]);
				map.put("roomimg3", obj[13]);
				map.put("roomimg4", obj[14]);
				map.put("roomimg5", obj[15]);
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
				map.put("houseName", obj[5]);
				map.put("roomNo", obj[6]);
				map.put("ownerName", obj[7]);
				map.put("ownerMobile", obj[8]);
				map.put("houseId", obj[9]);
				map.put("deudate", obj[10]);
				map.put("prvamt", obj[11]);
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
	public ResponseBean viewtenanttoroom(Long userid, Long houseId, Long roomId,Long tenantId) throws Exception {
		ResponseBean bean = new ResponseBean();
		try {
			List<Object[]> objectlist=tenantdetailsRepo.viewtenanttoroom(userid,houseId,roomId,tenantId);
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

	@Override
	public ResponseBean checkpendingbalanace(Long roomId) throws Exception {
		ResponseBean bean = new ResponseBean();
		try {
			List<PaymentDetails> list=paymentsrepo.getBytenantId(roomId);
			Long val=0l;
			if(list.size()==0) {
				val=0l;
			}else {
			PaymentDetails payment=list.get(0);
				val= ((payment.getRentAmount()== null ? 0 : payment.getRentAmount()) 
								+ (payment.getPreviousPendingAmount()== null ? 0 : payment.getPreviousPendingAmount()))
									- (payment.getPaidAmount()== null ? 0 : payment.getPaidAmount());
			}
			TenantDetails tenantdata=tenantdetailsRepo.findByroomId(roomId);
			Map<String,Object> map=new HashMap<>();
			map.put("val",val);
			map.put("tenantName",tenantdata!=null?tenantdata.getFullName():"");
			map.put("tenantMobileNo",tenantdata!=null?tenantdata.getMobileNo():"");
			
			bean.setStatus(HttpStatus.OK.value());
			bean.setMessage("Success");
			bean.setRecord(map);
		}catch (Exception e) {
			throw new CustomCheckedException(e);
		}
		return bean;
	}

	@Override
	public ResponseBean gettenantlistforpaymentprocess(Long houseId, Long userid) throws Exception {
		ResponseBean bean = new ResponseBean();
		List<Map<String, Object>> list= new ArrayList<>();
		try {
			List<Object[]> objectlist=tenantdetailsRepo.gettenantlistforpaymentprocess(userid,houseId);
			for (Object[] obj : objectlist) {
			    Map<String, Object> record = new HashMap<>();
			    record.put("paymentId", obj[0]);
			    record.put("tenantId", obj[1]);
			    record.put("fullName", obj[2]);
			    record.put("mobileNo", obj[3]);
			    record.put("dueDate", obj[4]); 
			    record.put("rentAmount", obj[5]);
			    record.put("houseName", obj[6]);
			    record.put("roomNo", obj[7]);
			    record.put("prvPendingAmount", obj[8]);
			    record.put("prvMtrRead", obj[9]);
			    record.put("advamount", obj[10]);
			    record.put("paidstatus", obj[11]);
			    record.put("currBill", obj[12]);
			    list.add(record);
			}
			bean.setStatus(HttpStatus.OK.value());
			bean.setMessage("Success");
			bean.setRecord(list);
		} catch (Exception e) {
			throw new CustomCheckedException(e);
		}
		return bean;
	}

	@Override
	public ResponseBean getdashboarddata(Long userid) throws Exception {
		ResponseBean bean = new ResponseBean();
		try {
			Map<String,Object> map=new HashMap<>();
			
			List<Object[]> roomcountdata = tenantdetailsRepo.roomcountdata(userid);
			Map<String,Object> mapobj=new HashMap<>();
			for(Object[] obj:roomcountdata) {				
				mapobj.put("homecount", obj[0]);
				mapobj.put("roomcount", obj[1]);				
				mapobj.put("tenantcount", obj[2]);
				mapobj.put("available", obj[3]);
				mapobj.put("advancebook", obj[4]);
				mapobj.put("tenantfamily", obj[5]);
			}
			map.put("roomcountdata",mapobj);
			
			List<Object[]> revenue = homeroomdetailsrepo.revenuecount(userid);
			Map<String,Object> maprevj=new HashMap<>();
			for(Object[] obj:revenue) {				
				maprevj.put("totalrevenue", obj[0]);
				maprevj.put("yearlyrevenue", obj[1]);				
				maprevj.put("monthlyrevenue", obj[2]);
			}
			map.put("revenuecount",maprevj);
			
			bean.setStatus(HttpStatus.OK.value());
			bean.setMessage("Success");
			bean.setRecord(map);
		} catch (Exception e) {
			throw new CustomCheckedException(e);
		}
		return bean;
	}

	@Override
	public ResponseBean savePaymentdetails(PaymentDetails paymentdetails, Long userid) throws Exception {
		ResponseBean bean = new ResponseBean();
		try {
			if(paymentdetails.getPaidStatus() == 0) {
				PaymentDetails paymentdata = paymentsrepo.findById(paymentdetails.getPaymentId()).get();
				paymentdata.setTakenBy(userid);
				paymentdata.setCurrentMeterRead(paymentdetails.getCurrentMeterRead());
				Long currentbill = 0l;
				if( paymentdata.getPreviousMeterRead()==null || paymentdetails.getCurrentMeterRead()==null) {
					currentbill =0l;
				} else {
					currentbill = (paymentdetails.getCurrentMeterRead() - paymentdata.getPreviousMeterRead()) * paymentdetails.getPrice();
				}
				paymentdata.setCurrentBill(currentbill);
				paymentdata.setTotalBill(paymentdata.getRentAmount() + paymentdata.getPreviousPendingAmount() + currentbill);
				paymentdata.setPaidAmount(paymentdetails.getPaidAmount());
				Long pendingamount = paymentdata.getTotalBill()-paymentdata.getPaidAmount();
				paymentdata.setCurrentPendingAmount(pendingamount < 0 ? 0 : pendingamount);
				paymentdata.setPaidOn(new Date());
				paymentdata.setPaidStatus(paymentdata.getCurrentPendingAmount() == 0 ? 1 : 2 );
				paymentsrepo.save(paymentdata);
				
			} else {
				
			}			
			bean.setStatus(HttpStatus.OK.value());
			bean.setMessage("Success");
		}catch (Exception e) {
			throw new CustomCheckedException(e);
		}
		return bean;
	}

}
