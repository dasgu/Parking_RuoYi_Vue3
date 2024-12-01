package com.ruoyi.parking.service;

import java.util.List;
import com.ruoyi.parking.domain.AParkingRecord;

/**
 * 停车记录Service接口
 * 
 * @author hr
 * @date 2024-11-20
 */
public interface IAParkingRecordService 
{
    /**
     * 查询停车记录
     * 
     * @param recordId 停车记录主键
     * @return 停车记录
     */
    public AParkingRecord selectAParkingRecordByRecordId(Long recordId);

    /**
     * 查询停车记录列表
     * 
     * @param aParkingRecord 停车记录
     * @return 停车记录集合
     */
    public List<AParkingRecord> selectAParkingRecordList(AParkingRecord aParkingRecord);

    /**
     * 新增停车记录
     * 
     * @param aParkingRecord 停车记录
     * @return 结果
     */
    public int insertAParkingRecord(AParkingRecord aParkingRecord);

    /**
     * 修改停车记录
     * 
     * @param aParkingRecord 停车记录
     * @return 结果
     */
    public int updateAParkingRecord(AParkingRecord aParkingRecord);

    /**
     * 批量删除停车记录
     * 
     * @param recordIds 需要删除的停车记录主键集合
     * @return 结果
     */
    public int deleteAParkingRecordByRecordIds(Long[] recordIds);

    /**
     * 删除停车记录信息
     * 
     * @param recordId 停车记录主键
     * @return 结果
     */
    public int deleteAParkingRecordByRecordId(Long recordId);
}
