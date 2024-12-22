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

	@Query("from HouseRoomdetails where houseId=:houseid and statusflag=0")
	List<HouseRoomdetails> findByOwneruseridAndHouseid(Long houseid);

	@Query(value="SELECT H.HOUSE_ID,H.HOUSE_NAME,H.ADDRESS,COUNT(R.ROOM_ID),SUM(T.NO_OF_MEMBER),5000\r\n"
			+ "			FROM TBL_MST_HM_HOMEDETAILS H\r\n"
			+ "			LEFT JOIN TBL_MST_HM_ROOMDETAILS R ON H.HOUSE_ID=R.HOUSE_ID\r\n"
			+ "			LEFT JOIN tbl_mst_hm_tenantdetails T ON T.OWNER_ID = r.owner_id\r\n"
			+ "            AND T.HOUSE_ID=r.house_id AND T.rOOM_ID = R.ROOM_ID AND t.statusflag=0\r\n"
			+ "			WHERE H.HOUSE_ID=?2 AND H.OWNER_ID=?1\r\n"
			+ "			group BY H.HOUSE_ID,H.HOUSE_NAME,H.ADDRESS",nativeQuery = true)
	List<Object[]> getdisplayhousedetails(Long userid, Long housedetails);

	@Query(value = "SELECT R.ROOM_ID,R.ROOM_NO,R.FLOOR_NO,\r\n"
			+ "        R.LAST_MTR_READ,R.UNIT_PER_PRICE,\r\n"
			+ "        T.FULL_NAME,T.MOBILE_NO,T.RENT_AMOUNT,\r\n"
			+ "        TO_CHAR(T.EFFECTIVE_DATE,'DD-MON-YYYY'),T.ADV_AMOUNT,NVL(T.TENANT_ID,0)\r\n"
			+ "    FROM TBL_MST_HM_ROOMDETAILS R \r\n"
			+ "    LEFT JOIN TBL_MST_HM_TENANTDETAILS T ON T.OWNER_ID = r.owner_id \r\n"
			+ "    AND T.HOUSE_ID=r.house_id AND T.rOOM_ID = R.ROOM_ID AND t.statusflag=0\r\n"
			+ "    WHERE R.house_id=?1 ORDER BY R.ROOM_ID DESC",nativeQuery = true)
	List<Object[]> getroomlistforhome(Long housedetails);
}
