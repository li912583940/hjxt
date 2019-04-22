// 验证通知设置
import request from '@/utils/request'


export const FindConf = params => { return request.get('/sysConf/findConf', { params: params } ).then(res => res) }

export const EditConf = params => { return request.post('/sysConf/editConf', params ).then(res => res) }

export const GetCheckedHjType = params => { return request.get('/sysConf/getCheckedHjType', { params: params } ).then(res => res) }
