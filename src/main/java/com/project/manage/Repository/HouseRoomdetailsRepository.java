/**
 * 
 */
package com.project.manage.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.manage.Model.HouseRoomdetails;

/**
 * 
 */
@Repository
public interface HouseRoomdetailsRepository extends JpaRepository<HouseRoomdetails, Long>{

	List<HouseRoomdetails> findByOwneruseridAndHouseid(Long userid, Long housedetails);

}
