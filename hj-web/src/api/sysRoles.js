import request from '@/utils/request'

// 权限配置

export const findPojo = params => { return request.get('/sysRole/findPojo', { params: params } ).then(res => res) }

//角色查询
export const findOne = params => { return request.get('/sysRole/findOne', { params: params } ).then(res => res) }

//角色新增
export const RequestAdd = params => { return request.post('/sysRole/add', params ).then(res => res) }

//角色编辑
export const RequestEdit = params => { return request.post('/sysRole/edit', params ).then(res => res) }

//角色删除
export const RequestDelete = params => { return request.post('/sysRole/delete', params ).then(res => res) }

//获取系统目录的树形结构
export const GetMenuTree = params => { return request.get('/sysRole/getMenuTree', { params: params } ).then(res => res) }

//获取当前角色配置的目录权限
export const GetCheckedMenu = params => { return request.get('/sysRole/getCheckedMenu', { params: params } ).then(res => res) }

//获取系统监区的树形结构
export const GetJqTree = params => { return request.get('/sysRole/getJqTree', { params: params } ).then(res => res) }

//获取当前角色的监区列表
export const GetCheckedJq = params => { return request.get('/sysRole/getCheckedJq', { params: params } ).then(res => res) }

//为当前角色配置目录权限
export const AddRoleMenu = params => { return request.post('/sysRole/addRoleMenu', params ).then(res => res) }

//为当前角色配置监区权限
export const AddRoleJq = params => { return request.post('/sysRole/addRoleJq', params ).then(res => res) }

//获取系统用户列表
export const FindUserList = params => { return request.get('/sysUser/findList', { params: params } ).then(res => res) }

//获取当前角色的用户列表
export const GetCheckedUser = params => { return request.get('/sysRole/getCheckedUser', { params: params } ).then(res => res) }

//为当前角色添加用户
export const AddRoleUser = params => { return request.post('/sysRole/addRoleUser', params ).then(res => res) }