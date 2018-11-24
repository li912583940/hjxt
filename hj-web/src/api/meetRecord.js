import request from '@/utils/request'

// 会见记录

export const findPojo = params => { return request.get('/jlHjRec/findPojo', { params: params } ).then(res => res) }

export const findOne = params => { return request.get('/jlHjRec/findOne', { params: params } ).then(res => res) }

// 获得监区集合
export const findJqList = params => { return request.get('/jlJq/findList', { params: params } ).then(res => res) }

// 获取当前用户在此次会见记录的注释
export const GetZs = params => { return request.get('/jlHjRec/getZs', { params: params } ).then(res => res) }

// 添加当前用户的在会见记录中的注释
export const AddRecordFlag = params => { return request.post('/jlHjRec/addRecordFlag', params ).then(res => res) }

// 获取当前会见记录所有注释
export const GetZsAllPojo = params => { return request.get('/jlHjInfo/findPojo', { params: params } ).then(res => res) }

// 导出EXCEL
export function DownZip(url,param) {
  return request({
    url: url,
    method: 'post',
    data: param,
    responseType:'blob'
  }).then(res => res)
}
