import request from '@/utils/request'

// 服刑人员

export const findPojo = params => { return request.get('/jlFr/findPojo', { params: params } ).then(res => res) }

export const findOne = params => { return request.get('/jlFr/findOne', { params: params } ).then(res => res) }

export const RequestAdd = params => { return request.post('/jlFr/add', params ).then(res => res) }

export const RequestEdit = params => { return request.post('/jlFr/edit', params ).then(res => res) }

export const RequestDelete = params => { return request.post('/jlFr/delete', params ).then(res => res) }


// 亲属

export const findQsPojo = params => { return request.get('/jlQs/findPojo', { params: params } ).then(res => res) }

export const findQsOne = params => { return request.get('/jlQs/findOne', { params: params } ).then(res => res) }

export const RequestQsAdd = params => { return request.post('/jlQs/add', params ).then(res => res) }

export const RequestQsEdit = params => { return request.post('/jlQs/edit', params ).then(res => res) }

export const RequestQsDelete = params => { return request.post('/jlQs/delete', params ).then(res => res) }

//获取亲属关系
export const findGxList = params => { return request.get('/jlQsGx/findList', { params: params } ).then(res => res) }