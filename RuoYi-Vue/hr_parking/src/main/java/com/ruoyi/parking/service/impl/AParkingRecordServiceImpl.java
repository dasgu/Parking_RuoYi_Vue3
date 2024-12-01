package com.ruoyi.parking.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.parking.mapper.AParkingRecordMapper;
import com.ruoyi.parking.domain.AParkingRecord;
import com.ruoyi.parking.service.IAParkingRecordService;

/**
 * 停车记录Service业务层处理
 * 
 * @author hr
 * @date 2024-11-20
 */
@Service
public class AParkingRecordServiceImpl implements IAParkingRecordService 
{
    @Autowired
    private AParkingRecordMapper aParkingRecordMapper;

    /**
     * 查询停车记录
     * 
     * @param recordId 停车记录主键
     * @return 停车记录
     */
    @Override
    public AParkingRecord selectAParkingRecordByRecordId(Long recordId)
    {
        return aParkingRecordMapper.selectAParkingRecordByRecordId(recordId);
    }

    /**
     * 查询停车记录列表
     * 
     * @param aParkingRecord 停车记录
     * @return 停车记录
     */
    @Override
    public List<AParkingRecord> selectAParkingRecordList(AParkingRecord aParkingRecord)
    {
        return aParkingRecordMapper.selectAParkingRecordList(aParkingRecord);
    }

    /**
     * 新增停车记录
     * 
     * @param aParkingRecord 停车记录
     * @return 结果
     */
    @Override
    public int insertAParkingRecord(AParkingRecord aParkingRecord)
    {
        return aParkingRecordMapper.insertAParkingRecord(aParkingRecord);
    }

    /**
     * 修改停车记录
     * 
     * @param aParkingRecord 停车记录
     * @return 结果
     */
    @Override
    public int updateAParkingRecord(AParkingRecord aParkingRecord)
    {
        return aParkingRecordMapper.updateAParkingRecord(aParkingRecord);
    }

    /**
     * 批量删除停车记录
     * 
     * @param recordIds 需要删除的停车记录主键
     * @return 结果
     */
    @Override
    public int deleteAParkingRecordByRecordIds(Long[] recordIds)
    {
        return aParkingRecordMapper.deleteAParkingRecordByRecordIds(recordIds);
    }

    /**
     * 删除停车记录信息
     * 
     * @param recordId 停车记录主键
     * @return 结果
     */
    @Override
    public int deleteAParkingRecordByRecordId(Long recordId)
    {
        return aParkingRecordMapper.deleteAParkingRecordByRecordId(recordId);
    }
}
