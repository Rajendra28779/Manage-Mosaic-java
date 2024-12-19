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

	@Query(value="SELECT H.HOUSE_ID,H.HOUSE_NAME,H.ADDRESS,COUNT(*)\r\n"
			+ "FROM TBL_MST_HM_HOMEDETAILS H\r\n"
			+ "LEFT JOIN TBL_MST_HM_ROOMDETAILS R ON H.HOUSE_ID=R.HOUSE_ID\r\n"
			+ "WHERE H.HOUSE_ID=?2 AND H.OWNER_ID=?1\r\n"
			+ "ORDER BY H.HOUSE_ID,H.HOUSE_NAME,H.ADDRESS;",nativeQuery = true)
	List<Object[]> getdisplayhousedetails(Long userid, Long housedetails);
}
