import request from '@/utils/request'

// 权限配置

export const findPojo = params => { return request.get('/sysRole/findPojo', { params: params } ).then(res => res) }

export const findOne = params => { return request.get('/sysRole/findOne', { params: params } ).then(res => res) }

export const RequestAdd = params => { return request.post('/sysRole/add', params ).then(res => res) }

export const RequestEdit = params => { return request.post('/sysRole/edit', params ).then(res => res) }

export const RequestDelete = params => { return request.post('/sysRole/delete', params ).then(res => res) }