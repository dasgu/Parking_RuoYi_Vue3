<template>
    <div>
        <el-row style="margin-top: 25px;">
            <el-col :span="6">
                <!-- <el-statistic title="停放车辆 总数" :value="record_all.length" /> -->
                <el-statistic :value="record_all.length">
                    <template #title>
                        <div style="display: inline-flex; align-items: center">
                            停放车辆 历史总数
                        </div>
                    </template>
                    <template #suffix>辆
                        <el-icon style="vertical-align: -0.125em">
                            <StarFilled />
                        </el-icon>
                    </template>
                </el-statistic>
            </el-col>
            <el-col :span="6">
                <el-statistic :value="record_now.length">
                    <template #title>
                        <div style="display: inline-flex; align-items: center">
                            停车场内 目前停放的车辆
                        </div>
                    </template>
                    <template #suffix>辆
                        <el-icon style="vertical-align: -0.125em">
                            <ChatDotSquare />
                        </el-icon>
                    </template>
                </el-statistic>
            </el-col>
            <el-col :span="6">
                <el-statistic :value="totalFeeDuration">
                    <template #title>
                        <div style="display: inline-flex; align-items: center">
                            停车场 历史总收益
                            <!-- <el-icon style="margin-left: 4px" :size="12">
                            <Money />
                        </el-icon> -->
                        </div>
                    </template>
                    <template #suffix>元
                        <el-icon style="vertical-align: -0.125em">
                            <Money />
                        </el-icon>
                    </template>
                </el-statistic>
            </el-col>
            <el-col :span="6">
                <el-statistic :value="record_today_duration">
                    <template #title>
                        <div style="display: inline-flex; align-items: center">
                            今日 入库的车
                        </div>
                    </template>
                    <template #suffix>辆
                        <el-icon style="vertical-align: -0.125em">
                            <Van />
                        </el-icon>
                    </template>
                </el-statistic>
            </el-col>
        </el-row>
        <el-divider />
        <div id="pdfDom">
            <el-row>
                <el-col :span="24">
                    <el-date-picker v-model="year" type="year" placeholder="请选择年份" value-format="YYYY"
                        @change="dateChange" />
                </el-col>
            </el-row>
            <el-divider border-style="dashed" />
            <div id="a" style="width: 100%;height:400px;"></div>
            <div id="b" style="width: 100%;height:400px;"></div>
            <div id="c" style="width: 100%;height:400px;"></div>
            <div id="d" style="width: 100%;height:400px;"></div>
        </div>
        <div style="margin-bottom: 100px; display: flex; justify-content: center;">
            <el-button type="success" @click="pdfOn()">打印</el-button>
        </div>
    </div>

</template>

<script setup>
import * as echarts from 'echarts';
import { dataAnalyze, dataAnalyzeYear } from '../../../api/parking/other';
import { useTransition } from '@vueuse/core'
import { ChatLineRound, TopRight, Money, ChatDotSquare, StarFilled, Van } from '@element-plus/icons-vue'
import htmlToPdf from '../../../utils/htmlToPdf';

const record_all = ref([])
const record_pass = ref([])
const record_now = ref([])
const record_today = ref(0)
const totalFee = ref(0)
let chart_a = null
let option_a = null
let chart_b = null
let option_b = null
let chart_c = null
let option_c = null
let chart_d = null
let option_d = null
const year = ref('2024')

const pdfOn = () => {
    htmlToPdf.getPdf("停车场统计分析");
}

const dateChange = () => {
    dataAnalyzeYear(getDateYear(year.value)).then(response => {
        console.log(response);

        const data = response.record_year;

        // 初始化一个包含 12 个空数组的数组
        const groupedByMonth = Array.from({ length: 12 }, () => []);

        // 使用 reduce 方法将数据按月份分组
        data.forEach(item => {
            const entryTime = new Date(item.entryTime)
            const month = entryTime.getMonth(); // 获取月份（0-11）
            groupedByMonth[month].push(item);
        });

        const res_a = [];
        groupedByMonth.forEach((month, index) => {
            res_a.push(month.length);
        })
        option_a.series[0].data = res_a;
        chart_a.setOption(option_a);

        const res_b = [];
        groupedByMonth.forEach((month, index) => {
            let month_fee = 0;
            month.forEach((item, index) => {
                console.log(item.fee);
                month_fee += item.fee;
            })
            res_b.push(month_fee / 100);
        })
        option_b.series[0].data = res_b;
        chart_b.setOption(option_b);

        const res_c = [];
        let j1 = 0;
        let j2 = 0;
        let j3 = 0;
        let j4 = 0;
        groupedByMonth.forEach((month, index) => {
            if (index >= 0 && index < 3) {
                j1 += month.length;
            }
            else if (index >= 3 && index < 6) {
                j2 += month.length;
            }
            else if (index >= 6 && index < 9) {
                j3 += month.length;
            }
            else if (index >= 9 && index < 12) {
                j4 += month.length;
            }
        })
        res_c.push(j1);
        res_c.push(j2);
        res_c.push(j3);
        res_c.push(j4);
        option_c.series[0].data = res_c;
        chart_c.setOption(option_c);

        const res_d = []
        let g1 = 0;
        let g2 = 0;
        let g3 = 0;
        let g4 = 0;
        groupedByMonth.forEach((month, index) => {
            if (index >= 0 && index < 3) {
                g1 += res_b[index];
            }
            else if (index >= 3 && index < 6) {
                g2 += res_b[index];
            }
            else if (index >= 6 && index < 9) {
                g3 += res_b[index];
            }
            else if (index >= 9 && index < 12) {
                g4 += res_b[index];
            }
        })
        res_d.push(g1);
        res_d.push(g2);
        res_d.push(g3);
        res_d.push(g4);
        option_d.series[0].data = res_d;
        chart_d.setOption(option_d);

    })
}

const totalFeeDuration = useTransition(totalFee, {
    duration: 1500,
})
const record_today_duration = useTransition(record_today, {
    duration: 1500,
})

/// 声明定义一下echart
let echart = echarts;

const getDateYear = (year) => {
    const specificDate = new Date(year, 0, 1, 0, 0, 0, 0);

    return specificDate;
}

onMounted(() => {
    initChart();
    dataAnalyze().then(response => {
        console.log(response);
        record_all.value = response.record_all;
        record_pass.value = response.record_pass;
        record_now.value = response.record_now;
        record_today.value = response.record_today.length;
        totalFee.value = response.totalFee / 100;
    });

    dataAnalyzeYear(getDateYear(year.value)).then(response => {
        console.log(response);

        const data = response.record_year;

        // 初始化一个包含 12 个空数组的数组
        const groupedByMonth = Array.from({ length: 12 }, () => []);

        // 使用 reduce 方法将数据按月份分组
        data.forEach(item => {
            const entryTime = new Date(item.entryTime)
            const month = entryTime.getMonth(); // 获取月份（0-11）
            groupedByMonth[month].push(item);
        });

        const res_a = [];
        groupedByMonth.forEach((month, index) => {
            res_a.push(month.length);
        })
        option_a.series[0].data = res_a;
        chart_a.setOption(option_a);

        const res_b = [];
        groupedByMonth.forEach((month, index) => {
            let month_fee = 0;
            month.forEach((item, index) => {
                console.log(item.fee);
                month_fee += item.fee;
            })
            res_b.push(month_fee / 100);
        })
        option_b.series[0].data = res_b;
        chart_b.setOption(option_b);

        const res_c = [];
        let j1 = 0;
        let j2 = 0;
        let j3 = 0;
        let j4 = 0;
        groupedByMonth.forEach((month, index) => {
            if (index >= 0 && index < 3) {
                j1 += month.length;
            }
            else if (index >= 3 && index < 6) {
                j2 += month.length;
            }
            else if (index >= 6 && index < 9) {
                j3 += month.length;
            }
            else if (index >= 9 && index < 12) {
                j4 += month.length;
            }
        })
        res_c.push(j1);
        res_c.push(j2);
        res_c.push(j3);
        res_c.push(j4);
        option_c.series[0].data = res_c;
        chart_c.setOption(option_c);

        const res_d = []
        let g1 = 0;
        let g2 = 0;
        let g3 = 0;
        let g4 = 0;
        groupedByMonth.forEach((month, index) => {
            if (index >= 0 && index < 3) {
                g1 += res_b[index];
            }
            else if (index >= 3 && index < 6) {
                g2 += res_b[index];
            }
            else if (index >= 6 && index < 9) {
                g3 += res_b[index];
            }
            else if (index >= 9 && index < 12) {
                g4 += res_b[index];
            }
        })
        res_d.push(g1);
        res_d.push(g2);
        res_d.push(g3);
        res_d.push(g4);
        option_d.series[0].data = res_d;
        chart_d.setOption(option_d);

    })


});

onUnmounted(() => {
    echart.dispose(chart_a);
    echart.dispose(chart_b);
    echart.dispose(chart_c);
    echart.dispose(chart_d);
});

// 基础配置一下Echarts
function initChart() {
    chart_a = echart.init(document.getElementById("a"));
    // 把配置和数据放这里
    option_a = {
        title: {
            text: '停车场一年12个月的入场车辆',
            x: 'center'
        },
        xAxis: {
            type: "category",
            data: [
                "一月",
                "二月",
                "三月",
                "四月",
                "五月",
                "六月",
                "七月",
                "八月",
                "九月",
                "十月",
                "十一月",
                "十二月"
            ]
        },
        tooltip: {
            trigger: "axis",
            formatter: function (params) {
                return params[0].data + "辆";
            }
        },
        yAxis: {
            type: "value"
        },
        series: [
            {
                data: [
                    0,
                    2,
                    4,
                    1,
                    9,
                    10,
                    2,
                    5,
                    1,
                    9,
                    11,
                    4
                ],
                type: "line",
                smooth: true,
                lineStyle: {
                    normal: {
                        color: 'blue',
                        width: 4,
                        type: 'solid'
                    }
                }
            }
        ]
    };
    chart_a.setOption(option_a);

    chart_b = echart.init(document.getElementById("b"));
    option_b = {
        title: {
            text: '停车场一年12个月的月收入',
            x: 'center'
        },
        xAxis: {
            type: "category",
            data: [
                "一月",
                "二月",
                "三月",
                "四月",
                "五月",
                "六月",
                "七月",
                "八月",
                "九月",
                "十月",
                "十一月",
                "十二月"
            ]
        },
        tooltip: {
            trigger: "axis",
            formatter: function (params) {
                return params[0].data + "元";
            }
        },
        yAxis: {
            type: "value"
        },
        series: [
            {
                data: [
                    0,
                    2,
                    4,
                    1,
                    9,
                    10,
                    2,
                    5,
                    1,
                    9,
                    11,
                    4
                ],
                type: "line",
                lineStyle: {
                    normal: {
                        color: 'green',
                        width: 4,
                        type: 'solid'
                    }
                }
            }
        ]
    };
    chart_b.setOption(option_b);

    chart_c = echart.init(document.getElementById("c"));
    option_c = {
        title: {
            text: '停车场一年四个季度的入库车辆',
            x: 'center'
        },
        xAxis: {
            type: "category",
            data: [
                "第一季度",
                "第二季度",
                "第三季度",
                "第四季度",
            ]
        },
        tooltip: {
            trigger: "axis",
            formatter: function (params) {
                return params[0].data + "辆";
            }
        },
        yAxis: {
            type: "value"
        },
        series: [
            {
                data: [
                    10,
                    12,
                    15,
                    5,
                ],
                type: "bar",
                barWidth: '20%',
                itemStyle: {
                    barBorderRadius: 5,
                    borderWidth: 1,
                    borderType: 'solid',
                    borderColor: '#73c0de',
                    shadowColor: '#5470c6',
                    shadowBlur: 3
                }
            }
        ]
    };
    chart_c.setOption(option_c);

    chart_d = echart.init(document.getElementById("d"));
    option_d = {
        title: {
            text: '停车场一年四个季度的收入',
            x: 'center'
        },
        xAxis: {
            type: "category",
            data: [
                "第一季度",
                "第二季度",
                "第三季度",
                "第四季度",
            ]
        },
        tooltip: {
            trigger: "axis",
            formatter: function (params) {
                return params[0].data + "元";
            }
        },
        yAxis: {
            type: "value"
        },
        series: [
            {
                data: [
                    10,
                    12,
                    15,
                    50,
                ],
                type: "bar",
                barWidth: '20%',
                itemStyle: {
                    color: '#91cc75',
                    shadowColor: '#91cc75',
                    borderType: 'dashed',
                    opacity: 0.5
                }
            }
        ]
    };
    chart_d.setOption(option_d);

    window.onresize = function () {
        //自适应大小
        chart_a.resize();
        chart_b.resize();
        chart_c.resize();
        chart_d.resize();
    };
}
</script>

<style>
.el-col {
    text-align: center;
}

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