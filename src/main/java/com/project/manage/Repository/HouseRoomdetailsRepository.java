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

	@Query(value="SELECT H.HOUSE_ID,H.HOUSE_NAME,H.ADDRESS,COUNT(R.ROOM_ID),\r\n"
			+ "NVL(SUM(T.NO_OF_MEMBER),0) TOTALMEMBER,\r\n"
			+ "(SELECT SUM(p.rent_amount) FROM tbl_mst_hm_paymentdetails P WHERE H.HOUSE_ID=?2) TOTALAMOUNT\r\n"
			+ "FROM TBL_MST_HM_HOMEDETAILS H\r\n"
			+ "LEFT JOIN TBL_MST_HM_ROOMDETAILS R ON H.HOUSE_ID=R.HOUSE_ID\r\n"
			+ "LEFT JOIN tbl_mst_hm_tenantdetails T ON T.OWNER_ID = r.owner_id\r\n"
			+ "AND T.HOUSE_ID=r.house_id AND T.ROOM_ID = R.ROOM_ID AND t.statusflag=0\r\n"
			+ "WHERE H.HOUSE_ID=?2 AND H.OWNER_ID=?1\r\n"
			+ "group BY H.HOUSE_ID,H.HOUSE_NAME,H.ADDRESS",nativeQuery = true)
	List<Object[]> getdisplayhousedetails(Long userid, Long housedetails);

	@Query(value = "SELECT R.ROOM_ID,R.ROOM_NO,R.FLOOR_NO,\r\n"
			+ "        R.LAST_MTR_READ,R.UNIT_PER_PRICE,\r\n"
			+ "        T.FULL_NAME,T.MOBILE_NO,T.RENT_AMOUNT,\r\n"
			+ "        TO_CHAR(T.EFFECTIVE_DATE,'DD-MON-YYYY'),T.ADV_AMOUNT,NVL(T.TENANT_ID,0),\r\n"
			+ "			R.ROOM_IMG_1,R.ROOM_IMG_2,R.ROOM_IMG_3,R.ROOM_IMG_4,R.ROOM_IMG_5\r\n"
			+ "    FROM TBL_MST_HM_ROOMDETAILS R \r\n"
			+ "    LEFT JOIN TBL_MST_HM_TENANTDETAILS T ON T.OWNER_ID = r.owner_id \r\n"
			+ "    AND T.HOUSE_ID=r.house_id AND T.rOOM_ID = R.ROOM_ID AND t.statusflag=0\r\n"
			+ "    WHERE R.house_id=?1 ORDER BY R.ROOM_ID DESC",nativeQuery = true)
	List<Object[]> getroomlistforhome(Long housedetails);

	@Query(value = "SELECT \r\n"
			+ "    NVL(SUM(REP.PAID_AMOUNT),0) AS TOTAL_REVENUE,\r\n"
			+ "    SUM(CASE \r\n"
			+ "        WHEN TO_CHAR(REP.PAID_ON, 'YYYY') = TO_CHAR(SYSDATE, 'YYYY') \r\n"
			+ "        THEN REP.PAID_AMOUNT ELSE 0 END) AS EARNINGS_THIS_YEAR,\r\n"
			+ "    SUM(CASE \r\n"
			+ "        WHEN TO_CHAR(REP.PAID_ON, 'YYYY-MM') = TO_CHAR(SYSDATE, 'YYYY-MM') \r\n"
			+ "        THEN REP.PAID_AMOUNT ELSE 0 END) AS EARNINGS_THIS_MONTH\r\n"
			+ "FROM \r\n"
			+ "    TBL_MST_HM_PAYMENTDETAILS REP\r\n"
			+ "LEFT JOIN \r\n"
			+ "    TBL_MST_HM_ROOMDETAILS R \r\n"
			+ "    ON REP.ROOM_ID = R.ROOM_ID \r\n"
			+ "    AND REP.HOUSE_ID = R.HOUSE_ID\r\n"
			+ "WHERE \r\n"
			+ "    REP.STATUSFLAG = 0 \r\n"
			+ "    AND R.OWNER_ID = ?1",nativeQuery = true)
	List<Object[]> revenuecount(Long userid);
}
