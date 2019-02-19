import request from '@/utils/request'

// 会见审批

export const findPojo = params => { return request.get('/jlHjSp/findPojo', { params: params } ).then(res => res) }

export const FindDetails = params => { return request.get('/jlHjSpDetails/findList', { params: params } ).then(res => res) }

export const FindSpeedSpUser = params => { return request.get('/jlHjSpUser/findSpeedSpUser', { params: params } ).then(res => res) }

export const RequestSpData = params => { return request.post('/jlHjSp/saveSpResult', params).then(res => res) }

export const findSpNotice = params => { return request.get('/jlHjSp/findSpNotice', { params: params } ).then(res => res) }

// 下载亲属附件
export function WordDownload(param) {
  return request({
    url: '/jlQs/wordDownload',
    method: 'post',
    data: param,
    timeout: 600000,
    responseType:'blob'
  }).then(res => res)
}