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
	
	@Query("from MstUserModel where email=:email and statusFlag=0")
	MstUserModel findByEmail(String email);

	@Query("from MstUserModel where mobileNo=:userName and statusFlag=0")
	MstUserModel findBymobile(String userName);

	MstUserModel findByUserNameIgnoreCase(String userName);
	

	@Query("select count(*) from MstUserModel where userName=:username")
	Integer usernamecheck(String username);
	
	@Query("select count(*) from MstUserModel where email=:email")
	Integer emailcheck(String email);

	@Query("Select Count(1) from MstUserModel where mobileNo=:mobileNo")
	Integer phonenocheck(String mobileNo);

	@Query("Select userId from MstUserModel where userName=:username")
	Long getuserIdfromuserName(String username);

	@Query("from MstUserModel where userName=:username and statusFlag=0")
	MstUserModel getfromuserName(String username);
	

}
