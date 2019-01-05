import request from '@/utils/request'

export function loginByUsername(username, password) {
  const data = {
    username,
    password
  }
  return request({
    url: '/login/login',
    method: 'post',
    data
  })
}

export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}


export const requestLogin = params => { return request.post('/login', params).then(res => res) }

export const getRoles = params => { return request.get('/sysUser/getRoles', { params: params }).then(res => res); };

export const EditPassword = params => { return request.post('/editPassword', params).then(res => res) }

export const ResetUserPassword = params => { return request.post('/resetUserPassword', params).then(res => res) }
