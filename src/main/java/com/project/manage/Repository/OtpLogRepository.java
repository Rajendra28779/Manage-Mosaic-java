/**
 * 
 */
package com.project.manage.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.manage.Model.OtpLog;

/**
 * 
 */
@Repository
public interface OtpLogRepository extends JpaRepository<OtpLog, Long> {

	@Query(value = "select * from (select * from TBL_MST_OTPLOG where lower(USERNAME)=lower(usename) order by CREATED_ON desc) where rownum=1", nativeQuery = true)
	OtpLog getlatestrecord(String usename);

}
