// 会见监控JS
import request from '@/utils/request'

export const findPojo = params => { return request.get('/hjMonitor/findPojo', { params: params } ).then(res => res) }

// 修改时间
export const UpdateSJ = params => { return request.post('/hjMonitor/updateSJ', params ).then(res => res) }


export const FindHjServerList = params => { return request.get('/sysHjServer/findList', { params: params } ).then(res => res) }