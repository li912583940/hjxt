// 会见通知JS
import request from '@/utils/request'

export const findPojo = params => { return request.get('/hjNotice/findPojo', { params: params } ).then(res => res) }