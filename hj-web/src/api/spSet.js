// 审批设置
import request from '@/utils/request'

export const findPojo = params => { return request.get('/jlHjSpSet/findPojo', { params: params } ).then(res => res) }

export const findDetails = params => { return request.get('/jlHjSpSet/findDetails', { params: params } ).then(res => res) }

export const RequestSpConf = params => { return request.post('/jlHjSpSet/spConf', params ).then(res => res) }

export const GetDeptList = params => { return request.get('/dept/findList', { params: params } ).then(res => res) }

export const GetUserList = params => { return request.get('/sysUser/findList', { params: params } ).then(res => res) }

//获取亲属关系集合
export const GetGxList = params => { return request.get('/jlQsGx/findList', { params: params } ).then(res => res) }

