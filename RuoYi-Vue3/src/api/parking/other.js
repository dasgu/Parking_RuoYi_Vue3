import request from '@/utils/request'

export function uploadFile(data) {
    return request({
        url: '/parking/other/uploadFile',
        method: 'post',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        data: data
    })
}

export function getBilling(query) {
    return request({
        url: '/parking/other/getBilling',
        method: 'put',
        data: query
    })
}

export function carIn(data) {
    return request({
        url: '/parking/other/carIn',
        method: 'put',
        data: data
    })
}

export function carOut(data) {
    return request({
        url: '/parking/other/carOut',
        method: 'put',
        data: data
    })
}

export function changeBilling(data) {
    return request({
        url: '/parking/other/changeBilling',
        method: 'put',
        data: data
    })
}

export function dataAnalyze(data) {
    return request({
        url: '/parking/other/dataAnalyze',
        method: 'put',
        data: data
    })
}

export function dataAnalyzeYear(data) {
    return request({
        url: '/parking/other/dataAnalyzeYear',
        method: 'put',
        data: data
    })
}
