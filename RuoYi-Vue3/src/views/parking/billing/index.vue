<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="计费编号" prop="billingId">
        <el-input
          v-model="queryParams.billingId"
          placeholder="请输入计费编号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="费用" prop="perMinuteFee">
        <el-input
          v-model="queryParams.perMinuteFee"
          placeholder="请输入费用(分钟)"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="额外费用" prop="extraFee">
        <el-input
          v-model="queryParams.extraFee"
          placeholder="请输入额外费用"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input
          v-model="queryParams.remark"
          placeholder="请输入备注"
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
          v-hasPermi="['parking:billing:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['parking:billing:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['parking:billing:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['parking:billing:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="billingList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="计费编号" align="center" prop="billingId" />
      <el-table-column label="费用(分钟)" align="center" prop="perMinuteFee" >
        <template #default="scope">{{ fenToYuan(scope.row.perMinuteFee) }}元</template>
      </el-table-column>
      <el-table-column label="额外费用" align="center" prop="extraFee" >
        <template #default="scope">{{ fenToYuan(scope.row.extraFee) }}元</template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="状态" align="center" prop="status" >
        <template #default="scope">
          <el-button @click="changeStatus(scope.row)" type="success" size="small" v-if="scope.row.status == 0" plain>
            选择
          </el-button>
          <el-tag v-else>
            已选择
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['parking:billing:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['parking:billing:remove']">删除</el-button>
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

    <!-- 添加或修改停车计费对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="billingRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="费用(分钟)" prop="perMinuteFee">
          <el-input v-model="form.perMinuteFee" placeholder="请输入费用(分钟)" />
        </el-form-item>
        <el-form-item label="额外费用" prop="extraFee">
          <el-input v-model="form.extraFee" placeholder="请输入额外费用" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
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

<script setup name="Billing">
import { listBilling, getBilling, delBilling, addBilling, updateBilling } from "@/api/parking/billing";
import { changeBilling } from '@/api/parking/other';

const { proxy } = getCurrentInstance();

const billingList = ref([]);
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
    billingId: null,
    perMinuteFee: null,
    extraFee: null,
    remark: null
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询停车计费列表 */
function getList() {
  loading.value = true;
  listBilling(queryParams.value).then(response => {
    billingList.value = response.rows;
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
    billingId: null,
    perMinuteFee: null,
    extraFee: null,
    remark: null,
    status: 0,
  };
  proxy.resetForm("billingRef");
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
  ids.value = selection.map(item => item.billingId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加停车计费";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _billingId = row.billingId || ids.value
  getBilling(_billingId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改停车计费";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["billingRef"].validate(valid => {
    if (valid) {
      if (form.value.billingId != null) {
        updateBilling(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addBilling(form.value).then(response => {
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
  const _billingIds = row.billingId || ids.value;
  proxy.$modal.confirm('是否确认删除停车计费编号为"' + _billingIds + '"的数据项？').then(function() {
    return delBilling(_billingIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('parking/billing/export', {
    ...queryParams.value
  }, `billing_${new Date().getTime()}.xlsx`)
}

getList();


const fenToYuan = (fen) => {
  console.log(fen)
  return fen / 100.0
}

const changeStatus = (billing) => {
  changeBilling(billing).then((response) => {
    getList();
    proxy.$modal.msgSuccess("选择成功");
  })
}
</script>
