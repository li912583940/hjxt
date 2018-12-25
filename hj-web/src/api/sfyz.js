import request from '@/utils/request'

// 身份验证

export const findOne = params => { return request.get('/jlFr/findOne', { params: params } ).then(res => res) }