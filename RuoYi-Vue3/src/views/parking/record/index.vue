<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="记录编号" prop="recordId">
        <el-input
          v-model="queryParams.recordId"
          placeholder="请输入记录编号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="车牌号" prop="licensePlateNumber">
        <el-input
          v-model="queryParams.licensePlateNumber"
          placeholder="请输入车牌号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="进入时间" prop="entryTime">
        <el-date-picker clearable
          v-model="queryParams.entryTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择进入时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="离开时间" prop="outTime">
        <el-date-picker clearable
          v-model="queryParams.outTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择离开时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="费用" prop="fee">
        <el-input
          v-model="queryParams.fee"
          placeholder="请输入费用"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="计费编号" prop="billingId">
        <el-input
          v-model="queryParams.billingId"
          placeholder="请输入计费编号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['parking:record:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['parking:record:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['parking:record:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['parking:record:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="recordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="记录编号" align="center" prop="recordId" />
      <el-table-column label="车牌号" align="center" prop="licensePlateNumber" />
      <el-table-column label="进入时间" align="center" prop="entryTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.entryTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="离开时间" align="center" prop="outTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.outTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="费用" align="center" prop="fee" >
        <template #default="scope">
          <span>{{ fenToYuan(scope.row.fee) }}元</span>
        </template>
      </el-table-column>
      <el-table-column label="计费编号" align="center" prop="billingId" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['parking:record:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['parking:record:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改停车记录对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="recordRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="车牌号" prop="licensePlateNumber">
          <el-input v-model="form.licensePlateNumber" placeholder="请输入车牌号" />
        </el-form-item>
        <el-form-item label="进入时间" prop="entryTime">
          <el-date-picker clearable
            v-model="form.entryTime"
            type="date"
            value-format="YYYY/MM/DD HH:mm:ss"
            placeholder="请选择进入时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="离开时间" prop="outTime">
          <el-date-picker clearable
            v-model="form.outTime"
            type="date"
            value-format="YYYY/MM/DD HH:mm:ss"
            placeholder="请选择离开时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="费用" prop="fee">
          <el-input v-model="form.fee" placeholder="请输入费用" />
        </el-form-item>
        <el-form-item label="计费编号" prop="billingId">
          <el-input v-model="form.billingId" placeholder="请输入计费编号" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Record">
import { listRecord, getRecord, delRecord, addRecord, updateRecord } from "@/api/parking/record";

const { proxy } = getCurrentInstance();

const recordList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    recordId: null,
    licensePlateNumber: null,
    entryTime: null,
    outTime: null,
    fee: null,
    billingId: null
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询停车记录列表 */
function getList() {
  loading.value = true;
  listRecord(queryParams.value).then(response => {
    recordList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    recordId: null,
    licensePlateNumber: null,
    entryTime: null,
    outTime: null,
    fee: null,
    billingId: null
  };
  proxy.resetForm("recordRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.recordId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加停车记录";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _recordId = row.recordId || ids.value
  getRecord(_recordId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改停车记录";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["recordRef"].validate(valid => {
    if (valid) {
      if (form.value.recordId != null) {
        updateRecord(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addRecord(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _recordIds = row.recordId || ids.value;
  proxy.$modal.confirm('是否确认删除停车记录编号为"' + _recordIds + '"的数据项？').then(function() {
    return delRecord(_recordIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('parking/record/export', {
    ...queryParams.value
  }, `record_${new Date().getTime()}.xlsx`)
}

getList();

const fenToYuan = (fen) => {
  console.log(fen)
  return fen / 100.0
}
</script>
