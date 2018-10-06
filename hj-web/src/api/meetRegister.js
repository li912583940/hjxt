//会见登记
import request from '@/utils/request'

//查询犯人
export const findFrPojo = params => { return request.get('/hjRegister/findFrPojo', { params: params } ).then(res => res) }

//查询亲属
export const findQsPojo = params => { return request.get('/hjRegister/findQsPojo', { params: params } ).then(res => res) }

// 获得监区集合
export const findJqList = params => { return request.get('/jlJq/findList', { params: params } ).then(res => res) }
