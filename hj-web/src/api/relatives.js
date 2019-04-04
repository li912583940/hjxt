import request from '@/utils/request'

// 亲属

export const findList = params => { return request.get('/jlQs/findList', { params: params } ).then(res => res) }

export const findPojo = params => { return request.get('/jlQs/findPojo', { params: params } ).then(res => res) }

export const findCount = params => { return request.get('/jlQs/findCount', { params: params } ).then(res => res) }

export const findOne = params => { return request.get('/jlQs/findOne', { params: params } ).then(res => res) }

export const RequestAdd = params => { return request.post('/jlQs/add', params ).then(res => res) }

export const RequestEdit = params => { return request.post('/jlQs/edit', params ).then(res => res) }

export const RequestDelete = params => { return request.post('/jlQs/delete', params ).then(res => res) }

// 导出EXCEL
export function exportExcel(param) {
  return request({
    url: '/jlQs/exportExcel',
    method: 'post',
    data: param,
    timeout: 600000,
    responseType:'blob'
  }).then(res => res)
}

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