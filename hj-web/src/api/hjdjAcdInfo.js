import request from '@/utils/request'

//排队叫号

export const findHjDjAcd = params => { return request.get('/hjdjAcdInfo/getAcdInfo', { params: params } ).then(res => res) }

export const NextCallNum = params => { return request.post('/hjdjAcdInfo/nextCallNum', params ).then(res => res) }
