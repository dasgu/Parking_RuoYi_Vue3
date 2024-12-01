package com.ruoyi.parking.mapper;

import com.ruoyi.parking.domain.AParkingBilling;
import com.ruoyi.parking.domain.AParkingRecord;

import java.util.List;

public interface AParkingOtherMapper {

    public List<AParkingRecord> selectAParkingRecordByLicensePlateNumber(String licensePlateNumber);

    public int updateAParkingRecordOutTime(AParkingRecord aParkingRecord);

    public int updateAParkingRecordFee(AParkingRecord aParkingRecord);

    public int updateAParkingBillingStatusToZero();

    public int updateAParkingBillingStatus(AParkingBilling aParkingBilling);

    public List<AParkingRecord> selectAParkingRecordByYear(AParkingRecord aParkingRecord);

}
