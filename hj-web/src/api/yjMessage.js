// 警察信息JS
import request from '@/utils/request'

export const findPojo = params => { return request.get('/jlYj/findPojo', { params: params } ).then(res => res) }

export const findOne = params => { return request.get('/jlYj/findOne', { params: params } ).then(res => res) }

export const RequestAdd = params => { return request.post('/jlYj/add', params ).then(res => res) }

export const RequestEdit = params => { return request.post('/jlYj/edit', params ).then(res => res) }

export const RequestDelete = params => { return request.post('/jlYj/delete', params ).then(res => res) }


export const findDeptNameList = params => { return request.get('/dept/findList', { params: params } ).then(res => res) }