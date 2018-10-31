import request from '@/utils/request'

// 系统用户

export const findPojo = params => { return request.get('/sysUser/findPojo', { params: params } ).then(res => res) }

export const findOne = params => { return request.get('/sysUser/findOne', { params: params } ).then(res => res) }

export const RequestAdd = params => { return request.post('/sysUser/add', params ).then(res => res) }

export const RequestEdit = params => { return request.post('/sysUser/edit', params ).then(res => res) }

export const RequestDelete = params => { return request.post('/sysUser/delete', params ).then(res => res) }