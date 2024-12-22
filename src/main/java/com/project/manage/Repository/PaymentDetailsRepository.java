/**
 * 
 */
package com.project.manage.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.project.manage.Model.PaymentDetails;

/**
 * 
 */
@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long>{

	@Query(value = "update tbl_mst_hm_paymentdetails set DELETEDFLAG=1 where TENANT_ID=?1 and  DELETEDFLAG=0" ,nativeQuery = true)
	void inactivepriviousrecord(Long tenantId);

	@Query("select Count(*) from PaymentDetails where tenantId=:tenantId and dueDate=:dueDate")
	Integer checkduplicate(Long tenantId, Date dueDate);

	@Query("from PaymentDetails where roomId=:roomId and statusFlag=0 and deletedFlag=0")
	List<PaymentDetails> getBytenantId(Long roomId);

}
