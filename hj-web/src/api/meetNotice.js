// 会见通知JS
import request from '@/utils/request'

export const findPojo = params => { return request.get('/hjNotice/findPojo', { params: params } ).then(res => res) }

export const findNotTzList = params => { return request.get('/hjNotice/findNotTzList', { params: params } ).then(res => res) }