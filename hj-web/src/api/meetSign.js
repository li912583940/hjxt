// 会见签到JS
import request from '@/utils/request'

export const findPojo = params => { return request.get('/hjSign/findPojo', { params: params } ).then(res => res) }