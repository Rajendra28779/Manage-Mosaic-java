/**
 * 
 */
package com.project.manage.Service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.project.manage.Bean.HousedetailsBean;
import com.project.manage.Bean.ResponseBean;
import com.project.manage.Model.HomeDetails;
import com.project.manage.Model.HouseRoomdetails;
import com.project.manage.Model.PaymentDetails;
import com.project.manage.Model.TenantDetails;
import com.project.manage.Util.CustomCheckedException;

/**
 * 
 */
public interface HomeDetailsService {

	ResponseBean addhomedetails(HomeDetails homeDetails) throws Exception;

	ResponseBean gethomedetails(Long userid) throws Exception;

	Map<String, Object> getdisplayhousedetails(Long userid, Long housedetails) throws Exception;

	ResponseBean gethousemasterData(Long userid) throws Exception;

	ResponseBean getroommasterData(Long userid, Long houseId) throws Exception;

	ResponseBean addroomdetails(HouseRoomdetails roomdetails, MultipartFile image1, MultipartFile image2,
			MultipartFile image3, MultipartFile image4, MultipartFile image5) throws CustomCheckedException;

	ResponseBean addtenanttoroom(TenantDetails tenantdetails, MultipartFile image1, MultipartFile image2,
			MultipartFile image3) throws Exception;

	ResponseBean gethousedetailsforuser(String phoneNo) throws Exception;

	ResponseBean onChangeroomgettenanrdata(Long roomId, Long houseId) throws Exception;

	ResponseBean viewtenanttoroom(Long userid, Long houseId, Long roomId, Long tenantId) throws Exception;

	ResponseBean checkpendingbalanace(Long roomId) throws Exception;

	ResponseBean gettenantlistforpaymentprocess(Long houseId, Long userid) throws Exception;

	ResponseBean getdashboarddata(Long userid) throws Exception;

	ResponseBean savePaymentdetails(PaymentDetails paymentdetails, Long userid) throws Exception;

}
