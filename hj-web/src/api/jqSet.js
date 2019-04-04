// 监区设置
import request from '@/utils/request'

// 获得监区集合
export const findList = params => { return request.get('/jlJq/findList', { params: params } ).then(res => res) }

export const findPojo = params => { return request.get('/jlJq/findPojo', { params: params } ).then(res => res) }

export const findOne = params => { return request.get('/jlJq/findOne', { params: params } ).then(res => res) }

export const RequestAdd = params => { return request.post('/jlJq/add', params ).then(res => res) }

export const RequestEdit = params => { return request.post('/jlJq/edit', params ).then(res => res) }

export const RequestDelete = params => { return request.post('/jlJq/delete', params ).then(res => res) }

//获取当前监区选中的会见星期日
export const GetCheckedWeek = params => { return request.get('/jlJq/getCheckedWeek', { params: params } ).then(res => res) }

//提交当前监区的会见星期日
export const AddJqWeek = params => { return request.post('/jlJq/addJqWeek', params ).then(res => res) }
