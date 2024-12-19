/**
 * 
 */
package com.project.manage.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.manage.Model.HouseRoomdetails;

/**
 * 
 */
@Repository
public interface HouseRoomdetailsRepository extends JpaRepository<HouseRoomdetails, Long>{

	@Query("from HouseRoomdetails where houseId=:houseid and ownerId=:userid and statusflag=0")
	List<HouseRoomdetails> findByOwneruseridAndHouseid(Long userid, Long houseid);

}
