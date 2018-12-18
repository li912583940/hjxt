import request from '@/utils/request'

// 登记记录

export const findPojo = params => { return request.get('/sysLog/findPojo', { params: params } ).then(res => res) }

export const findCount = params => { return request.get('/sysLog/findCount', { params: params } ).then(res => res) }

export const findOne = params => { return request.get('/sysLog/findOne', { params: params } ).then(res => res) }

export const RequestAdd = params => { return request.post('/sysLog/add', params ).then(res => res) }

export const RequestEdit = params => { return request.post('/sysLog/edit', params ).then(res => res) }

export const RequestDelete = params => { return request.post('/sysLog/delete', params ).then(res => res) }