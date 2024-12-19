/**
 * 
 */
package com.project.manage.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.manage.Model.HomeDetails;

/**
 * Rajendra
 */
@Repository
public interface HomeDetailsRepository extends JpaRepository<HomeDetails, Long> {

	@Query("from HomeDetails where ownerid=:userid and statutsFlag=0 order by homeName")
	List<HomeDetails> gethomelist(Long userid);

}
