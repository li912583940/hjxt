// 特殊会见日设置
import request from '@/utils/request'

export const findPojo = params => { return request.get('/jlHjHoliday/findPojo', { params: params } ).then(res => res) }

export const findOne = params => { return request.get('/jlHjHoliday/findOne', { params: params } ).then(res => res) }

export const RequestAdd = params => { return request.post('/jlHjHoliday/add', params ).then(res => res) }

export const RequestEdit = params => { return request.post('/jlHjHoliday/edit', params ).then(res => res) }

export const RequestDelete = params => { return request.post('/jlHjHoliday/delete', params ).then(res => res) }
