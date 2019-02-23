import request from '@/utils/request'

// 身份验证

export const RequestSfyz = params => { return request.get('/sfyz/djYz', { params: params } ).then(res => res) }