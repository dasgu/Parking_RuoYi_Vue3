package com.ruoyi.parking.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.parking.domain.AParkingRecord;
import com.ruoyi.parking.service.IAParkingRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 停车记录Controller
 * 
 * @author hr
 * @date 2024-11-20
 */
@RestController
@RequestMapping("/parking/record")
public class AParkingRecordController extends BaseController
{
    @Autowired
    private IAParkingRecordService aParkingRecordService;

    /**
     * 查询停车记录列表
     */
    @PreAuthorize("@ss.hasPermi('parking:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(AParkingRecord aParkingRecord)
    {
        startPage();
        List<AParkingRecord> list = aParkingRecordService.selectAParkingRecordList(aParkingRecord);
        return getDataTable(list);
    }

    /**
     * 导出停车记录列表
     */
    @PreAuthorize("@ss.hasPermi('parking:record:export')")
    @Log(title = "停车记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AParkingRecord aParkingRecord)
    {
        List<AParkingRecord> list = aParkingRecordService.selectAParkingRecordList(aParkingRecord);
        ExcelUtil<AParkingRecord> util = new ExcelUtil<AParkingRecord>(AParkingRecord.class);
        util.exportExcel(response, list, "停车记录数据");
    }

    /**
     * 获取停车记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('parking:record:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return success(aParkingRecordService.selectAParkingRecordByRecordId(recordId));
    }

    /**
     * 新增停车记录
     */
    @PreAuthorize("@ss.hasPermi('parking:record:add')")
    @Log(title = "停车记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AParkingRecord aParkingRecord)
    {
        return toAjax(aParkingRecordService.insertAParkingRecord(aParkingRecord));
    }

    /**
     * 修改停车记录
     */
    @PreAuthorize("@ss.hasPermi('parking:record:edit')")
    @Log(title = "停车记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AParkingRecord aParkingRecord)
    {
        return toAjax(aParkingRecordService.updateAParkingRecord(aParkingRecord));
    }

    /**
     * 删除停车记录
     */
    @PreAuthorize("@ss.hasPermi('parking:record:remove')")
    @Log(title = "停车记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(aParkingRecordService.deleteAParkingRecordByRecordIds(recordIds));
    }
}
