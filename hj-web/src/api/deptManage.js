// 关系
import request from '@/utils/request'

export const findPojo = params => { return request.get('/dept/findPojo', { params: params } ).then(res => res) }

export const findOne = params => { return request.get('/dept/findOne', { params: params } ).then(res => res) }

export const RequestAdd = params => { return request.post('/dept/add', params ).then(res => res) }

export const RequestEdit = params => { return request.post('/dept/edit', params ).then(res => res) }

export const RequestDelete = params => { return request.post('/dept/delete', params ).then(res => res) }