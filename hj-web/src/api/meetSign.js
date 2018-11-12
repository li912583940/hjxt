// 会见签到JS
import request from '@/utils/request'

export const findPojo = params => { return request.get('/hjSign/findPojo', { params: params } ).then(res => res) }

// 分配座位
export const FpZw = params => { return request.post('/hjSign/fpZw', params ).then(res => res) }

// 取消分配座位
export const QxFpZw = params => { return request.post('/hjSign/qxFpZw', params ).then(res => res) }

// 获取剩余的座位号
export const GetSurplusZw = params => { return request.get('/hjSign/getSurplusZw', { params: params } ).then(res => res) }

// 人工分配座位
export const RgFpZw = params => { return request.post('/hjSign/rgFpZw', params ).then(res => res) }

// 授权才能使用电话
export const GrantCall = params => { return request.post('/hjSign/grantCall', params ).then(res => res) }

// 取消授权才能使用电话
export const CancelGrantCall = params => { return request.post('/hjSign/cancelGrantCall', params ).then(res => res) }