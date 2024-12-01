<template>
    <div class="app-container">
        <el-row :gutter="40">
            <el-col :span="10">
                <el-card class="custom_card">
                    <div style="display: flex; justify-content: center;">
                        <div class="barrier_before"></div>
                        <div class="barrier" id="box"></div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="14">
                <el-card>
                    <el-tabs v-model="activeTab" type="" style="">
                        <el-tab-pane label="入库信息" name="enter_info">
                            <div style="display: flex; flex-wrap: wrap;">
                                <div
                                    style="display: flex; justify-content: center; align-items: center; width: 100%; height: 50%;">
                                    <el-image style="width: 550px; height: 350px" :src="imageFrontUrl_in"
                                        fit="contain"></el-image>
                                </div>
                                <div class="el-table el-table--enable-row-hover el-table--medium"
                                    style=" width: 100%; height: 100%;">
                                    <table cellspacing="15" style="width: 100%;">
                                        <thead>
                                            <tr>
                                                <th class="el-table__cell is-leaf">
                                                    <div class="cell">描述
                                                    </div>
                                                </th>
                                                <th class="el-table__cell is-leaf">
                                                    <div class="cell">值
                                                    </div>
                                                </th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td class="el-table__cell is-leaf">
                                                    <div class="cell">车牌号码</div>
                                                </td>
                                                <td class="el-table__cell is-leaf">
                                                    <div class="cell" v-if="record_in.licensePlateNumber == '暂未识别'">{{
                                                        record_in.licensePlateNumber }}</div>
                                                    <div class="cell" v-else>
                                                        <el-input v-model="record_in.licensePlateNumber"
                                                            style="width: 240px" placeholder="请输入" />
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="el-table__cell is-leaf">
                                                    <div class="cell">入库时间</div>
                                                </td>
                                                <td class="el-table__cell is-leaf">
                                                    <div class="cell">{{ record_in.entryTime }}</div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="el-table__cell is-leaf">
                                                    <div class="cell">出库时间</div>
                                                </td>
                                                <td class="el-table__cell is-leaf">
                                                    <div class="cell">{{ record_in.outTime == null ? '暂无计划' :
                                                        record_in.outTime }}</div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="el-table__cell is-leaf">
                                                    <div class="cell">计费规则</div>
                                                </td>
                                                <td class="el-table__cell is-leaf">
                                                    <div class="cell">
                                                        <!-- <el-select v-model="record_in.billingId"
                                                            placeholder="请选择合适的计费标准" style="width: 240px">
                                                            <el-option v-for="item in billingList" :key="item.remark"
                                                                :label="item.remark" :value="item.billingId" />
                                                        </el-select> -->
                                                        <el-tag size="large" type="warning">
                                                            {{ getNowBilling() }}
                                                        </el-tag>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="el-table__cell is-leaf">
                                                    <div class="cell">车辆识别</div>
                                                </td>
                                                <td class="el-table__cell is-leaf">
                                                    <div class="cell">
                                                        <el-button type="primary" plain @click="selectImage('in', 1)"
                                                            style="">
                                                            拍照<el-icon class="el-icon--right">
                                                                <Upload />
                                                            </el-icon>
                                                        </el-button>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="el-table__cell is-leaf">
                                                    <div class="cell">门禁操作</div>
                                                </td>
                                                <td class="el-table__cell is-leaf">
                                                    <div class="cell">
                                                        <el-button type="success" :icon="Check" plain @click="car_in">
                                                            允许入库
                                                        </el-button>
                                                        <el-button id="rotateButton1" :type="bar_status" :icon="Share"
                                                            plain @click="changeSatus">{{ bar_msg }}</el-button>

                                                    </div>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </el-tab-pane>
                        <el-tab-pane label="出库信息" name="out_info">
                            <div
                                style="display: flex; justify-content: center; align-items: center; width: 100%; height: 50%;">
                                <el-image style="width: 550px; height: 350px" :src="imageFrontUrl_out"
                                    fit="contain"></el-image>
                            </div>
                            <div class="el-table el-table--enable-row-hover el-table--medium">
                                <table cellspacing="10" style="width: 100%;">
                                    <thead>
                                        <tr>
                                            <th class="el-table__cell is-leaf">
                                                <div class="cell">描述
                                                </div>
                                            </th>
                                            <th class="el-table__cell is-leaf">
                                                <div class="cell">值
                                                </div>
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td class="el-table__cell is-leaf">
                                                <div class="cell">车牌号码</div>
                                            </td>
                                            <td class="el-table__cell is-leaf">
                                                <div class="cell" v-if="record_out.licensePlateNumber == '暂未识别'">{{
                                                    record_out.licensePlateNumber }}</div>
                                                <div class="cell" v-else>
                                                    <el-input v-model="record_out.licensePlateNumber"
                                                        style="width: 240px" placeholder="请输入" />
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="el-table__cell is-leaf">
                                                <div class="cell">入库时间</div>
                                            </td>
                                            <td class="el-table__cell is-leaf">
                                                <div class="cell">{{ record_out.entryTime == null ? '未知' :
                                                    record_out.entryTime }}</div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="el-table__cell is-leaf">
                                                <div class="cell">出库时间</div>
                                            </td>
                                            <td class="el-table__cell is-leaf">
                                                <div class="cell">{{ record_out.outTime }}</div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="el-table__cell is-leaf">
                                                <div class="cell">计费规则</div>
                                            </td>
                                            <td class="el-table__cell is-leaf">
                                                <div class="cell">{{ record_out.billingRemark == '' ? '未知' :
                                                    record_out.billingRemark }}</div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="el-table__cell is-leaf">
                                                <div class="cell">总费用</div>
                                            </td>
                                            <td class="el-table__cell is-leaf">
                                                <div class="cell">{{ record_out.licensePlateNumber == '暂未识别' ? '未计算' :
                                                    updateFee() }}</div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="el-table__cell is-leaf">
                                                <div class="cell">车辆识别</div>
                                            </td>
                                            <td class="el-table__cell is-leaf">
                                                <div class="cell">
                                                    <el-button type="primary" plain @click="selectImage('out', 2)"
                                                        style="">
                                                        拍照<el-icon class="el-icon--right">
                                                            <Upload />
                                                        </el-icon>
                                                    </el-button>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="el-table__cell is-leaf">
                                                <div class="cell">出库操作</div>
                                            </td>
                                            <td class="el-table__cell is-leaf">
                                                <div class="cell">
                                                    <el-button type="success" plain :icon="Check" @click="car_out">允许出库</el-button>
                                                    <el-button id="rotateButton2" :type="bar_status" :icon="Share"
                                                    plain @click="changeSatus">{{ bar_msg }}</el-button>
                                                </div>

                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </el-tab-pane>
                    </el-tabs>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script setup>
import { Search, Share, Check } from '@element-plus/icons-vue'
import img from '@/assets/images/car_1.jpg'
import img1 from '@/assets/images/car_2.jpg'
import { uploadFile, getBilling, carIn, carOut } from '@/api/parking/other';

const { proxy } = getCurrentInstance();

const billingList = ref([]);
const currentTime = ref('');
const record_in = reactive({
    licensePlateNumber: '暂未识别',
    entryTime: currentTime,
    outTime: null,
    fee: 0,
    billingId: '',
    billingRemark: '',
});
const record_out = reactive({
    licensePlateNumber: '暂未识别',
    entryTime: null,
    outTime: currentTime,
    fee: 0,
    billingId: '',
    billingRemark: '',
});

//拦车杆动画
const bar_msg = ref('抬杠');
const bar_status = ref('danger')
const changeSatus = () => {
    console.log('杠')
    var box = document.getElementById("box");
    if (box.style.transform == "rotate(-90deg)") {
        bar_msg.value = '抬杠'
        bar_status.value = 'danger'
    }
    else if (box.style.transform == "") {
        console.log(box.style.transform)
        bar_msg.value = '放杠'
        bar_status.value = 'success'
    }
}

// 定义一个函数来更新当前时间
const updateTime = () => {
    const now = new Date();
    currentTime.value = now.toLocaleString('zh-CN', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit',
        hour12: false
    });
};

//导航栏跳转
const activeTab = ref("enter_info");
const activeTab_ = ref("bar_msg")

//图片上传
const imageFrontUrl_in = ref(img)
const imageFrontUrl_out = ref(img1)
let file = null
async function selectImage(val, flag) {
    try {
        const [fileHandle] = await window.showOpenFilePicker({
            types: [
                {
                    description: 'Images',
                    accept: {
                        'image/*': ['.png', '.jpg', '.jpeg', '.gif']
                    }
                }
            ],
            multiple: false
        });
        file = await fileHandle.getFile();
        if (val == "in") {
            imageFrontUrl_in.value = URL.createObjectURL(file);
            proxy.$modal.msgWarning("入库车牌正在识别");
            setTimeout(() => {
                upload(1);
            }, 2000);
        } else if (val == "out") {
            imageFrontUrl_out.value = URL.createObjectURL(file);
            proxy.$modal.msgWarning("出库车牌正在识别");
            setTimeout(() => {
                upload(2);
            }, 2000);
        }

    } catch (error) {
        console.error('Error selecting image:', error);
    }
}
const upload = (flag) => {
    let formData = new FormData();
    formData.append("file", file, 'car');
    formData.append("flag", flag);
    uploadFile(formData).then((response) => {
        console.log("上传图片回调");
        console.log(response);
        if (flag == 1) {
            record_in.licensePlateNumber = response.licensePlate;
            proxy.$modal.msgSuccess("入库车牌识别成功");
        }
        else if (flag == 2) {
            record_out.licensePlateNumber = response.record.licensePlateNumber;
            record_out.entryTime = response.record.entryTime;
            record_out.billingId = response.record.billingId;
            const billings = billingList.value.filter(billing => {
                return billing.billingId == record_out.billingId;
            })
            record_out.billingRemark = billings[0].remark;
            if (response.code == 200) {
                proxy.$modal.msgSuccess("出库车牌识别成功");
            }
            else {
                proxy.$modal.msgError(response.msg);
            }
        }

    })
}

//允许入库
const car_in = () => {
    if (record_in.licensePlateNumber == '暂未识别') {
        proxy.$modal.msgWarning("请先进行车牌识别");
        return;
    }
    if (record_in.billingId == '') {
        proxy.$modal.msgWarning("请选择计费标准");
        return;
    }
    carIn(record_in).then((response) => {
        console.log("入库");
        console.log(response);
        if (response.code == 200) {
            proxy.$modal.msgSuccess("正在入库");
            //模拟放行
            bar_change();
            setTimeout(bar_change, 3000);
        }
        else {
            proxy.$modal.msgError(response.msg);
        }
    })
}

//允许出库
const car_out = () => {
    if (record_out.licensePlateNumber == '暂未识别') {
        proxy.$modal.msgWarning("请先进行车牌识别");
        return;
    }
    carOut(record_out).then((response) => {
        console.log("出库");
        console.log(response)
        if (response.code == 200) {
            proxy.$modal.msgSuccess("正在出库");
            //模拟放行
            bar_change();
            setTimeout(bar_change, 3000);
        }
        else {
            proxy.$modal.msgError(response.msg);
        }
    })
}

//更新费用
const updateFee = () => {
    let date1 = new Date(record_out.outTime)
    let date2 = new Date(record_out.entryTime)
    const diffInMs = date1.getTime() - date2.getTime();
    // 将毫秒转换为分钟
    const diffInMinutes = diffInMs / (1000 * 60);
    console.log(diffInMinutes)

    const billings = billingList.value.filter(val => {
        return val.billingId == record_out.billingId;
    });
    console.log('更新fee')
    console.log(diffInMs)
    let ans = diffInMinutes * billings[0].perMinuteFee + billings[0].extraFee;
    console.log(ans)
    record_out.fee = ans;

    return (ans / 100).toFixed(2) + '元';
}

//杆旋转方法
const bar_change = () => {
    var box = document.getElementById("box");
    if (box.style.transform == "rotate(-90deg)") {
        box.style.transform = ''; /* 设置旋转角度 */
    }
    else if (box.style.transform == '') {
        box.style.transform = "rotate(-90deg)"; /* 设置旋转角度 */
    }
}

const getNowBilling = () => {
    const billings = billingList.value.filter(val => {
        return val.status == 1;
    })

    if (billings.length == 1) {
        record_in.billingId = billings[0].billingId;
        return billings[0].remark;
    }

    return '管理员暂未选择计费标准';
}

onMounted(() => {
    updateTime(); // 初始化时间
    const interval = setInterval(updateTime, 1000); // 每秒更新一次时间

    //杆旋转，添加点击事件
    document.getElementById("rotateButton1").addEventListener("click", bar_change);
    document.getElementById("rotateButton2").addEventListener("click", bar_change);

    //获得计费标准
    getBilling({}).then((response) => {
        billingList.value = response.list;
    })
});
</script>

<style>
.rotate-box {
    width: 500px;
    height: 100px;
    background-color: #4CAF50;
    transition: transform 1s ease-in-out;
    /* 设置旋转的过渡效果 */
    transform-origin: left;
    /* 设置旋转中心为左边 */
}

.pad {
    width: 500px;
    height: 500px;
}

.btn {
    margin-top: 9.5vw;
    display: flex;
    justify-content: center;
    align-items: center;
}

.top {
    padding-left: 100px;
}

.barrier {
    width: 840px;
    height: 20px;
    background-color: red;
    border-radius: 10px;
    transition: transform 2s ease-in-out;
    /* 设置旋转的过渡效果 */
    transform-origin: left;
    /* 设置旋转中心为左边 */
    margin-top: 625px;
    margin-bottom: 120px;
    margin-left: 10px;
}

.barrier_before {
    width: 20px;
    height: 100px;
    background-color: rgb(10, 9, 9);
    border-radius: 5px;
    margin-top: 585px;
    margin-bottom: 120px;
}

.custom_card {
    background-image: url('@/assets/images/backgroud.jpg');
    /* 替换为你的图片路径 */
    background-size: cover;
    /* 使背景图片覆盖整个卡片 */
    background-position: center;
    /* 背景图片居中 */
}
</style>