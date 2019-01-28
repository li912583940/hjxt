import request from '@/utils/request'

// 会见报表


export const RequestReport = params => { return request.get('/hjReport/findReport', { params: params } ).then(res => res) }

// 获得监区集合
export const findJqList = params => { return request.get('/jlJq/findList', { params: params } ).then(res => res) }

// 获得监区集合
export const findYearList = params => { return request.get('/hjReport/findYearList', { params: params } ).then(res => res) }
