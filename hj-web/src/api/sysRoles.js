import request from '@/utils/request'

// 权限配置

export const findPojo = params => { return request.get('/sysRole/findPojo', { params: params } ).then(res => res) }

export const findOne = params => { return request.get('/sysRole/findOne', { params: params } ).then(res => res) }

export const RequestAdd = params => { return request.post('/sysRole/add', params ).then(res => res) }

export const RequestEdit = params => { return request.post('/sysRole/edit', params ).then(res => res) }

export const RequestDelete = params => { return request.post('/sysRole/delete', params ).then(res => res) }

export const GetMenuTree = params => { return request.get('/sysRole/getMenuTree', { params: params } ).then(res => res) }

export const GetCheckedMenu = params => { return request.get('/sysRole/getCheckedMenu', { params: params } ).then(res => res) }

export const GetJqTree = params => { return request.get('/sysRole/getJqTree', { params: params } ).then(res => res) }

export const GetCheckedJq = params => { return request.get('/sysRole/getCheckedJq', { params: params } ).then(res => res) }

export const AddRoleMenu = params => { return request.post('/sysRole/addRoleMenu', params ).then(res => res) }

export const AddRoleJq = params => { return request.post('/sysRole/addRoleJq', params ).then(res => res) }

export const FindUserList = params => { return request.get('/sysUser/findList', { params: params } ).then(res => res) }