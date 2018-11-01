// 监区设置
import request from '@/utils/request'

export const findPojo = params => { return request.get('/jlJq/findPojo', { params: params } ).then(res => res) }

export const findOne = params => { return request.get('/jlJq/findOne', { params: params } ).then(res => res) }

export const RequestAdd = params => { return request.post('/jlJq/add', params ).then(res => res) }

export const RequestEdit = params => { return request.post('/jlJq/edit', params ).then(res => res) }

export const RequestDelete = params => { return request.post('/jlJq/delete', params ).then(res => res) }