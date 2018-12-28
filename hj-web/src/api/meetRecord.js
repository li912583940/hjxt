import request from '@/utils/request'

// 会见记录

export const findPojo = params => { return request.get('/jlHjRec/findPojo', { params: params } ).then(res => res) }

export const findCount = params => { return request.get('/jlHjRec/findCount', { params: params } ).then(res => res) }

export const findOne = params => { return request.get('/jlHjRec/findOne', { params: params } ).then(res => res) }

// 获得监区集合
export const findJqList = params => { return request.get('/jlJq/findList', { params: params } ).then(res => res) }

export const GetZwList = params => { return request.get('/sysHjLine/findList', { params: params } ).then(res => res) }

// 获取当前用户在此次会见记录的注释
export const GetZs = params => { return request.get('/jlHjRec/getZs', { params: params } ).then(res => res) }

// 添加当前用户的在会见记录中的注释
export const AddRecordFlag = params => { return request.post('/jlHjRec/addRecordFlag', params ).then(res => res) }

// 获取当前会见记录所有注释
export const GetZsAllPojo = params => { return request.get('/jlHjInfo/findPojo', { params: params } ).then(res => res) }

// 获取录音评级
export const GetRatingState = params => { return request.get('/jlHjRec/getRatingState', { params: params } ).then(res => res) }

// 修改评级内容
export const UpdateRatingState = params => { return request.post('/jlHjRec/updateRatingState', params ).then(res => res) }

// 获取当前会见记录所有录音评级
export const GetRatingStateAllPojo = params => { return request.get('/jlHjRecRatingInfo/findPojo', { params: params } ).then(res => res) }

// 获取当前会见记录的所有复听详情
export const GetAllAssessmentPojo = params => { return request.get('/jlHjRecAssessmentInfo/findPojo', { params: params } ).then(res => res) }

// 获取当前会见记录的其它详情
export const GetOtherInfo = params => { return request.get('/jlHjRec/getOtherInfo', { params: params } ).then(res => res) }

// 导出EXCEL
export function exportExcel(param) {
  return request({
    url: '/jlHjRec/exportExcel',
    method: 'post',
    data: param,
    responseType:'blob'
  }).then(res => res)
}

// 获取一个星期内每天会见总数
export const GetWeekCount = params => { return request.get('/jlHjRec/getWeekCount', { params: params } ).then(res => res) }
