package com.ruoyi.parking.controller;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.parking.domain.AParkingBilling;
import com.ruoyi.parking.domain.AParkingRecord;
import com.ruoyi.parking.mapper.AParkingBillingMapper;
import com.ruoyi.parking.mapper.AParkingOtherMapper;
import com.ruoyi.parking.mapper.AParkingRecordMapper;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/parking/other")
public class AParkingOtherController extends BaseController {

    @Resource
    private AParkingBillingMapper aParkingBillingMapper;

    @Resource
    private AParkingRecordMapper aParkingRecordMapper;

    @Resource
    private AParkingOtherMapper aParkingOtherMapper;


    private String licensePlateNumberIdentification(int flag, String path) {
        if (flag == 1) {
            //刘陈峰
            return null;
        }
        else {
            //临时代码生成随机车牌号
            String[] licensePlates = {
                    "粤A12345",
                    "京B67890",
                    "沪C23456",
                    "川D78901",
                    "浙E34567",
                    "苏F89012",
                    "鲁G45678",
                    "晋H90123",
                    "冀J56789",
                    "皖K01234"
            };

            Random random = new Random();
            int randomNumber = random.nextInt(10);

            return licensePlates[randomNumber];
        }
    }

    @PostMapping("/uploadFile")
    public AjaxResult avatar(@RequestParam("file") MultipartFile file, @RequestParam("flag") Integer flag) throws Exception
    {
        //上传图片
        if (!file.isEmpty())
        {
            //通过工具类保存在本地路径具体在/RouYi-Vue/uploadPath/car/下面
            String r_path = FileUploadUtils.upload(RuoYiConfig.getProfile() + "/car", file, MimeTypeUtils.IMAGE_EXTENSION);

            //输出图片的相对位置
            System.out.println("车牌号相对位置" + r_path);
            //分割相对路径
            String[] parts = r_path.split("profile");
            System.out.println("车牌号绝对位置后半部分" + parts[1]);

            //图像识别算法返回车牌号
            //第一个参数是标志位，标志位为0则使用临时生成车牌，标志位为1则使用识别算法
            //第二个参数是图片路径，自己处理
            String licensePlate = licensePlateNumberIdentification(0, null);

            if (flag == 1) {
                //入库
                //成功返回车牌号给前端
                AjaxResult ajax = AjaxResult.success();
                ajax.put("imgUrl", r_path);
                ajax.put("licensePlate", licensePlate);
                return ajax;
            }
            else if (flag == 2) {
                //出库
                //先从数据库查询出是否有当前识别出的车牌号
                List<AParkingRecord> recordList = aParkingOtherMapper.selectAParkingRecordByLicensePlateNumber(licensePlate);

                if (recordList != null && recordList.size() == 1 && recordList.get(0).getLicensePlateNumber().equals(licensePlate)) {
                    AjaxResult ajax = AjaxResult.success();
                    ajax.put("record", recordList.get(0));
                    return ajax;
                }
                else {
                    return error("当前车牌号不在停车场中，请联系管理员");
                }
            }


        }
        return error("上传图片异常，请联系管理员");
    }

    @PutMapping("/getBilling")
    public AjaxResult getBilling(@RequestBody AParkingBilling aParkingBilling) {
//        System.out.println(aParkingBilling);

        List<AParkingBilling> list = aParkingBillingMapper.selectAParkingBillingList(aParkingBilling);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("list", list);
        return ajax;
    }

    @PutMapping("/carIn")
    public AjaxResult carIn(@RequestBody AParkingRecord aParkingRecord) {

//        System.out.println(aParkingRecord);
        List<AParkingRecord> recordList = aParkingRecordMapper.selectAParkingRecordList(null);

        //本次入库的车牌号
        String licensePlate = aParkingRecord.getLicensePlateNumber();

        for (AParkingRecord record : recordList) {
            //车牌相同并且离开时间为空，说明车辆在停车场中
            if (licensePlate.equals(record.getLicensePlateNumber()) && record.getOutTime() == null) {
                return error("停车场中有相同的车牌号，请联系管理员解决问题");
            }
        }

        //入库
        int cnt = aParkingRecordMapper.insertAParkingRecord(aParkingRecord);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("cnt", cnt);
        return ajax;
    }

    @PutMapping("/carOut")
    public AjaxResult carOut(@RequestBody AParkingRecord aParkingRecord) {

        int cnt_outTime = aParkingOtherMapper.updateAParkingRecordOutTime(aParkingRecord);
        int cnt_fee = aParkingOtherMapper.updateAParkingRecordFee(aParkingRecord);


        //出库
        AjaxResult ajax = AjaxResult.success();
        ajax.put("cnt_outTime", cnt_outTime);
        ajax.put("cnt_fee", cnt_fee);
        return ajax;
    }

    @PutMapping("/changeBilling")
    public AjaxResult changeBilling(@RequestBody AParkingBilling aParkingBilling) {

        int cnt_zero = aParkingOtherMapper.updateAParkingBillingStatusToZero();
        int cnt_change = aParkingOtherMapper.updateAParkingBillingStatus(aParkingBilling);


        //出库
        AjaxResult ajax = AjaxResult.success();
        ajax.put("cnt_zero", cnt_zero);
        ajax.put("cnt_change", cnt_change);
        return ajax;
    }

    @PutMapping("/dataAnalyze")
    public AjaxResult dataAnalyze() {
        List<AParkingRecord> record_all = aParkingRecordMapper.selectAParkingRecordList(null);

        List<AParkingRecord> record_pass = record_all.stream().filter(val -> val.getOutTime() != null).collect(Collectors.toList());
        List<AParkingRecord> record_now = record_all.stream().filter(val -> val.getOutTime() == null).collect(Collectors.toList());

        int totalFee = 0;
        for (AParkingRecord aParkingRecord : record_pass) {
            totalFee += aParkingRecord.getFee();
        }

        LocalDate today = LocalDate.now();

        List<AParkingRecord> record_today = record_all.stream()
                .filter(obj -> {
                    LocalDate objDate = obj.getEntryTime().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    return objDate.equals(today);
                })
                .collect(Collectors.toList());

        AjaxResult ajax = AjaxResult.success();
        ajax.put("record_all", record_all);
        ajax.put("record_pass", record_pass);
        ajax.put("record_now", record_now);
        ajax.put("record_today", record_today);
        ajax.put("totalFee", totalFee);
        return ajax;
    }

    @PutMapping("/dataAnalyzeYear")
    public AjaxResult dataAnalyzeYear(@RequestBody Date year) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(year);
        Date entryTime = calendar.getTime();

        // 增加一年
        calendar.add(Calendar.YEAR, 1);
        Date outTime = calendar.getTime();

        AParkingRecord tempTime = new AParkingRecord();
        tempTime.setEntryTime(entryTime);
        tempTime.setOutTime(outTime);

        List<AParkingRecord> record_year = aParkingOtherMapper.selectAParkingRecordByYear(tempTime);

        AjaxResult ajax = AjaxResult.success();
        ajax.put("record_year", record_year);
        return ajax;
    }

}
