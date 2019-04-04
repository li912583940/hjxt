import request from '@/utils/request'

// 服刑人员
export const findList = params => { return request.get('/jlFr/findList', { params: params } ).then(res => res) }

export const findPojo = params => { return request.get('/jlFr/findPojo', { params: params } ).then(res => res) }

export const findCount = params => { return request.get('/jlFr/findCount', { params: params } ).then(res => res) }

export const findOne = params => { return request.get('/jlFr/findOne', { params: params } ).then(res => res) }

export const RequestAdd = params => { return request.post('/jlFr/add', params ).then(res => res) }

export const RequestEdit = params => { return request.post('/jlFr/edit', params ).then(res => res) }

export const RequestDelete = params => { return request.post('/jlFr/delete', params ).then(res => res) }

// 导出EXCEL
export function exportExcel(param) {
  return request({
    url: '/jlFr/exportExcel',
    method: 'post',
    data: param,
    timeout: 600000,
    responseType:'blob'
  }).then(res => res)
}





