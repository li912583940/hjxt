import request from '@/utils/request'

// 会见记录

export const findPojo = params => { return request.get('/jlHjRec/findPojo', { params: params } ).then(res => res) }

export const findOne = params => { return request.get('/jlHjRec/findOne', { params: params } ).then(res => res) }


// 获得监区集合
export const findJqList = params => { return request.get('/jlJq/findList', { params: params } ).then(res => res) }