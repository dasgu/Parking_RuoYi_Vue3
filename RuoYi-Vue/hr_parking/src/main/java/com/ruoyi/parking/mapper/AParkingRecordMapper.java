package com.ruoyi.parking.mapper;

import java.util.List;
import com.ruoyi.parking.domain.AParkingRecord;

/**
 * 停车记录Mapper接口
 * 
 * @author hr
 * @date 2024-11-20
 */
public interface AParkingRecordMapper 
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
     * 删除停车记录
     * 
     * @param recordId 停车记录主键
     * @return 结果
     */
    public int deleteAParkingRecordByRecordId(Long recordId);

    /**
     * 批量删除停车记录
     * 
     * @param recordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAParkingRecordByRecordIds(Long[] recordIds);
}
