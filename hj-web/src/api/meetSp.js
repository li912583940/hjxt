import request from '@/utils/request'

// 会见审批

export const findPojo = params => { return request.get('/jlHjSp/findPojo', { params: params } ).then(res => res) }

export const FindDetails = params => { return request.get('/jlHjSp/findDetails', { params: params } ).then(res => res) }