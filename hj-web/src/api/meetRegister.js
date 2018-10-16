//会见登记
import request from '@/utils/request'

//查询会见登记
export const findPojo = params => { return request.get('/jlHjDj/findPojo', { params: params } ).then(res => res) }

//查询犯人
export const findFrPojo = params => { return request.get('/jlHjDj/findFrPojo', { params: params } ).then(res => res) }

//查询亲属
export const findQsPojo = params => { return request.get('/jlHjDj/findQsPojo', { params: params } ).then(res => res) }

// 获得监区集合
export const findJqList = params => { return request.get('/jlJq/findList', { params: params } ).then(res => res) }

// 提交会见登记
export const RequestAddHjdj = params => { return request.post('/jlHjDj/addHjdj', params ).then(res => res) }

// 打印小票
export const RequestPrintXp = params => { return request.post('/jlHjDj/printXp', params ).then(res => res) }

// 取消登记
export const RequestCancelDj = params => { return request.post('/jlHjDj/cancelDj', params ).then(res => res) }