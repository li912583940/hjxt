// 审批设置
import request from '@/utils/request'

export const findPojo = params => { return request.get('/jlHjSpSet/findPojo', { params: params } ).then(res => res) }

export const findOne = params => { return request.get('/jlHjSpSet/findOne', { params: params } ).then(res => res) }

export const RequestAdd = params => { return request.post('/jlHjSpSet/add', params ).then(res => res) }

export const RequestEdit = params => { return request.post('/jlHjSpSet/edit', params ).then(res => res) }

export const RequestDelete = params => { return request.post('/jlHjSpSet/delete', params ).then(res => res) }