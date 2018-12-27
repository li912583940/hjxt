import request from '@/utils/request'

// 登记记录

export const findPojo = params => { return request.get('/hjdjLog/findPojo', { params: params } ).then(res => res) }

export const findCount = params => { return request.get('/hjdjLog/findCount', { params: params } ).then(res => res) }

export const findOne = params => { return request.get('/hjdjLog/findOne', { params: params } ).then(res => res) }

// 获得监区集合
export const findJqList = params => { return request.get('/jlJq/findList', { params: params } ).then(res => res) }

// 导出EXCEL
export function exportExcel(param) {
  return request({
    url: '/hjdjLog/exportExcel',
    method: 'post',
    data: param,
    timeout: 600000,
    responseType:'blob'
  }).then(res => res)
}
