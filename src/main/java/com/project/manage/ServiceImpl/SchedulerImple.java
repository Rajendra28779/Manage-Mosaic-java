package com.project.manage.ServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.manage.Model.PaymentDetails;
import com.project.manage.Repository.PaymentDetailsRepository;
import com.project.manage.Repository.TenantDetailsRepository;
import com.project.manage.Service.SchedulerService;
import com.project.manage.Util.CustomCheckedException;

@Service
public class SchedulerImple implements SchedulerService {
	
	@Autowired
	private TenantDetailsRepository tenantrepo;
	
	@Autowired
	private PaymentDetailsRepository paymentdtlsrepo;

	@Override
	public void addpaymentdata() throws CustomCheckedException {
		List<PaymentDetails> list = new ArrayList<>(); 
		try {			
			List<Object[]> objlist =  tenantrepo.getuserlisttopaid();
			for(Object[] obj:objlist) {
				Long tenantId=((BigDecimal) obj[0]).longValue();				
				Integer checkduplicate=paymentdtlsrepo.checkduplicate(tenantId,(Date) obj[3]);
				if(checkduplicate==0) {
					paymentdtlsrepo.inactivepriviousrecord(tenantId);					
					PaymentDetails paymentDetails=new PaymentDetails();
					paymentDetails.setTenantId(tenantId);
					paymentDetails.setHouseId(((BigDecimal) obj[1]).longValue());
					paymentDetails.setRoomId(((BigDecimal) obj[2]).longValue());
					paymentDetails.setDueDate((Date) obj[3]);
					paymentDetails.setRentAmount(((BigDecimal) obj[4]).longValue());
					paymentDetails.setPreviousPendingAmount(((BigDecimal) obj[5]).longValue());
					paymentDetails.setPreviousMeterRead(((BigDecimal) obj[6]).longValue());
					paymentDetails.setPaidStatus(0);
					paymentDetails.setCreatedOn(Calendar.getInstance().getTime());
					paymentDetails.setDeletedFlag(0);
					paymentDetails.setStatusFlag(0);
					list.add(paymentDetails);
				}
				System.out.println(list);
				paymentdtlsrepo.saveAll(list);
			}			
		} catch (Exception e) {
			throw new CustomCheckedException();
		}				
	}

}
