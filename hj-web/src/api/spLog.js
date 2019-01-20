import request from '@/utils/request'

// 审批记录

export const findPojo = params => { return request.get('/jlHjSp/findPojoLog', { params: params } ).then(res => res) }

export const FindDetails = params => { return request.get('/jlHjSpDetails/findList', { params: params } ).then(res => res) }

// 获得监区集合
export const findJqList = params => { return request.get('/jlJq/findList', { params: params } ).then(res => res) }


// 导出EXCEL
export function exportExcel(param) {
  return request({
    url: '/jlHjSp/exportExcel',
    method: 'post',
    data: param,
    timeout: 600000,
    responseType:'blob'
  }).then(res => res)
}