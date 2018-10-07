// 会见监控JS
import request from '@/utils/request'

export const findPojo = params => { return request.get('/hjMonitor/findPojo', { params: params } ).then(res => res) }