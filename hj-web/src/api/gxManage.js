// 关系
import request from '@/utils/request'

//获取亲属关系集合
export const findList = params => { return request.get('/jlQsGx/findList', { params: params } ).then(res => res) }

export const findPojo = params => { return request.get('/jlQsGx/findPojo', { params: params } ).then(res => res) }

export const findOne = params => { return request.get('/jlQsGx/findOne', { params: params } ).then(res => res) }

export const RequestAdd = params => { return request.post('/jlQsGx/add', params ).then(res => res) }

export const RequestEdit = params => { return request.post('/jlQsGx/edit', params ).then(res => res) }

export const RequestDelete = params => { return request.post('/jlQsGx/delete', params ).then(res => res) }