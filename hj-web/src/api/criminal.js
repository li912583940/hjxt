import request from '@/utils/request'

// 服刑人员

export const findPojo = params => { return request.get('/jlFr/findPojo', { params: params } ).then(res => res) }

export const findOne = params => { return request.get('/jlFr/findOne', { params: params } ).then(res => res) }

export const RequestAdd = params => { return request.post('/jlFr/add', params ).then(res => res) }

export const RequestEdit = params => { return request.post('/jlFr/edit', params ).then(res => res) }

export const RequestDelete = params => { return request.post('/jlFr/delete', params ).then(res => res) }