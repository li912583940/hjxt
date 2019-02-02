import request from '@/utils/request'

// 系统用户

export const findPojo = params => { return request.get('/sysUser/findPojo', { params: params } ).then(res => res) }

export const findCount = params => { return request.get('/sysUser/findCount', { params: params } ).then(res => res) }

export const findOne = params => { return request.get('/sysUser/findOne', { params: params } ).then(res => res) }

export const RequestAdd = params => { return request.post('/sysUser/add', params ).then(res => res) }

export const RequestEdit = params => { return request.post('/sysUser/edit', params ).then(res => res) }

export const RequestDelete = params => { return request.post('/sysUser/delete', params ).then(res => res) }

export const GetDeptList = params => { return request.get('/dept/findList', { params: params } ).then(res => res) }

// 获取系统角色列表
export const FindRoleList = params => { return request.get('/sysRole/findList', { params: params } ).then(res => res) }

// 获取当前用户选中的角色
export const GetCheckedRole = params => { return request.get('/sysUser/getCheckedRole', { params: params } ).then(res => res) }

// 为当前用户添加角色
export const AddUserRole = params => { return request.post('/sysUser/addUserRole', params ).then(res => res) }

