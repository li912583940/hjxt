import request from '@/utils/request'

export const findPojo = params => { return request.get('/jlFr/findPojo', { params: params } ).then(res => res) }