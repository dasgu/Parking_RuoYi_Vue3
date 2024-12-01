import request from '@/utils/request'

// 查询停车计费列表
export function listBilling(query) {
  return request({
    url: '/parking/billing/list',
    method: 'get',
    params: query
  })
}

// 查询停车计费详细
export function getBilling(billingId) {
  return request({
    url: '/parking/billing/' + billingId,
    method: 'get'
  })
}

// 新增停车计费
export function addBilling(data) {
  return request({
    url: '/parking/billing',
    method: 'post',
    data: data
  })
}

// 修改停车计费
export function updateBilling(data) {
  return request({
    url: '/parking/billing',
    method: 'put',
    data: data
  })
}

// 删除停车计费
export function delBilling(billingId) {
  return request({
    url: '/parking/billing/' + billingId,
    method: 'delete'
  })
}
