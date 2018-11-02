// 线路设置
import request from '@/utils/request'

export const findPojo = params => { return request.get('/sysHjLine/findPojo', { params: params } ).then(res => res) }

export const findOne = params => { return request.get('/sysHjLine/findOne', { params: params } ).then(res => res) }

export const RequestAdd = params => { return request.post('/sysHjLine/add', params ).then(res => res) }

export const RequestEdit = params => { return request.post('/sysHjLine/edit', params ).then(res => res) }

export const RequestDelete = params => { return request.post('/sysHjLine/delete', params ).then(res => res) }