import request from '@/utils/request'

// 服刑人员

export const findPojo = params => { return request.get('/jlFr/findPojo', { params: params } ).then(res => res) }

export const findCount = params => { return request.get('/jlFr/findCount', { params: params } ).then(res => res) }

export const findOne = params => { return request.get('/jlFr/findOne', { params: params } ).then(res => res) }

export const RequestAdd = params => { return request.post('/jlFr/add', params ).then(res => res) }

export const RequestEdit = params => { return request.post('/jlFr/edit', params ).then(res => res) }

export const RequestDelete = params => { return request.post('/jlFr/delete', params ).then(res => res) }

// 导出EXCEL
export function exportExcel(param) {
  return request({
    url: '/jlFr/exportExcel',
    method: 'post',
    data: param,
    timeout: 600000,
    responseType:'blob'
  }).then(res => res)
}

// 亲属

export const findQsPojo = params => { return request.get('/jlQs/findPojo', { params: params } ).then(res => res) }

export const findQsOne = params => { return request.get('/jlQs/findOne', { params: params } ).then(res => res) }

export const RequestQsAdd = params => { return request.post('/jlQs/add', params ).then(res => res) }

export const RequestQsEdit = params => { return request.post('/jlQs/edit', params ).then(res => res) }

export const RequestQsDelete = params => { return request.post('/jlQs/delete', params ).then(res => res) }

// 下载亲属附件
export function WordDownload(param) {
  return request({
    url: '/jlQs/wordDownload',
    method: 'post',
    data: param,
    timeout: 600000,
    responseType:'blob'
  }).then(res => res)
}

//获取亲属关系集合
export const findGxList = params => { return request.get('/jlQsGx/findList', { params: params } ).then(res => res) }

// 获得监区集合
export const findJqList = params => { return request.get('/jlJq/findList', { params: params } ).then(res => res) }

// 获取犯人级别集合
export const findJbList = params => { return request.get('/jlJb/findList', { params: params } ).then(res => res) }
