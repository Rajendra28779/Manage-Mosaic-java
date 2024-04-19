/**
 * 
 */
package com.project.manage.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.manage.Model.MstUserModel;

/**
 * 
 */
@Repository
public interface MstUserRepository extends JpaRepository<MstUserModel, Long> {

	MstUserModel findByUserName(String username);

	MstUserModel findByUserNameIgnoreCase(String userName);
	

	@Query("select count(*) from MstUserModel where lower(userName)=:username")
	Integer usernamecheck(String username);
	
	@Query("select count(*) from MstUserModel where email=:email")
	Integer emailcheck(String email);

	@Query("Select Count(1) from MstUserModel where mobileNo=:mobileNo")
	Integer phonenocheck(String mobileNo);

}
