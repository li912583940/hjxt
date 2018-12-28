// 会见监控JS
import request from '@/utils/request'

export const findPojo = params => { return request.get('/hjMonitor/findPojo', { params: params } ).then(res => res) }

// 修改时间
export const UpdateSJ = params => { return request.post('/hjMonitor/updateSJ', params ).then(res => res) }

// 更新监听警察信息
export const UpdateYJ = params => { return request.post('/hjMonitor/updateYJ', params ).then(res => res) }

//  获取服务器
export const GetHjServerList = params => { return request.get('/sysHjServer/findList', { params: params } ).then(res => res) }

// 获取网络监控插话表
export const GetMonitorVocList = params => { return request.get('/jlMonitorVoc/findList', { params: params } ).then(res => res) }

// 获取当前用户在此次通话的注释
export const GetZs = params => { return request.get('/hjMonitor/getZs', { params: params } ).then(res => res) }

// 注释
export const AddMonitorFlag = params => { return request.post('/hjMonitor/addMonitorFlag', params ).then(res => res) }

// 切断
export const QieduanHj = params => { return request.post('/hjMonitor/qieduanHj', params ).then(res => res) }
