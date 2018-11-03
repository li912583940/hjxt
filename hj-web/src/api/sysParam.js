// 系统参数
import request from '@/utils/request'

export const findPojo = params => { return request.get('/sysHjServer/findPojo', { params: params } ).then(res => res) }

export const findOne = params => { return request.get('/sysHjServer/findOne', { params: params } ).then(res => res) }

export const RequestAdd = params => { return request.post('/sysHjServer/add', params ).then(res => res) }

export const RequestEdit = params => { return request.post('/sysHjServer/edit', params ).then(res => res) }

export const RequestDelete = params => { return request.post('/sysHjServer/delete', params ).then(res => res) }