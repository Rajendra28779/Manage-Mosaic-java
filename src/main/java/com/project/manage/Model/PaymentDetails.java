/**
 * 
 */
package com.project.manage.Model;

import java.util.Date;

import jakarta.persistence.*;

import lombok.Data;

/**
 * paidstatus 0 not paid 1 fully paid 2 partial paid
 */

@Entity
@Data
@Table(name = "TBL_MST_HM_PAYMENTDETAILS")
public class PaymentDetails {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_ID")
    private Long paymentId;

    @Column(name = "TENANT_ID")
    private Long tenantId;

    @Column(name = "HOUSE_ID")
    private Long houseId;

    @Column(name = "ROOM_ID")
    private Long roomId;

    @Column(name = "DUE_DATE")
    private Date dueDate;

    @Column(name = "RENT_AMOUNT")
    private Long rentAmount;

    @Column(name = "PRV_PENDING_AMOUNT")
    private Long previousPendingAmount;

    @Column(name = "PRV_MTR_READ")
    private Long previousMeterRead;

    @Column(name = "CURRENT_MTR_READ")
    private Long currentMeterRead;

    @Column(name = "CURRENT_BILL")
    private Long currentBill;

    @Column(name = "TOTAL_BILL")
    private Long totalBill;

    @Column(name = "PAID_AMOUNT")
    private Long paidAmount;

    @Column(name = "CUR_PENDING_AMT")
    private Long currentPendingAmount;

    @Column(name = "TAKEN_BY")
    private Long takenBy;

    @Column(name = "PAID_ON")
    private Date paidOn;

    @Column(name = "PAID_STATUS")
    private Integer paidStatus;

    @Column(name = "CREATED_ON")
    private Date createdOn;

    @Column(name = "DELETEDFLAG")
    private Integer deletedFlag;

    @Column(name = "STATUSFLAG")
    private Integer statusFlag;
    
    @Transient
    private Integer price;
}
