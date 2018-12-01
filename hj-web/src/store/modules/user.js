import { loginByUsername, logout, getRoles, requestLogin } from '@/api/login'
import { getToken, setToken, removeToken} from '@/utils/auth'

const user = {
  state: {
    status: '',
    code: '',
    token: getToken(),
    name: '',
    avatar: '',
    introduction: '',
    roles: [],
    setting: {
      articlePlatform: []
    }
  },

  mutations: {
    SET_CODE: (state, code) => {
      state.code = code
    },
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_INTRODUCTION: (state, introduction) => {
      state.introduction = introduction
    },
    SET_SETTING: (state, setting) => {
      state.setting = setting
    },
    SET_STATUS: (state, status) => {
      state.status = status
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    }
 
  },

  actions: {
    // 用户名登录
    RequestLogin({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
      	requestLogin(userInfo).then((res) => {
      		commit('SET_TOKEN', res.data.token)
					setToken(res.data.token);
					sessionStorage.setItem("user", JSON.stringify(res.data))
					resolve()
				})
       .catch(error => {
          reject(error)
        })
      })
    },

		// 获取用户信息
    GetRoles({ commit, state }) {
      return new Promise((resolve, reject) => {
      	let para = {
					token:state.token
				}
      	getRoles(para).then((res) => {
      		if (res.data && res.data.length > 0) { // 验证返回的roles是否是一个非空数组
      			commit('SET_ROLES', res.data)
      			sessionStorage.setItem("roles", JSON.stringify(res.data))
      			sessionStorage.setItem("buttonRoles",  JSON.stringify(res.buttonData))
          } else {
            reject('当前用户没有分配角色权限 !')
          }
					commit('SET_NAME', 'admin')
          commit('SET_AVATAR', '超级管理员')
          commit('SET_INTRODUCTION', '')
          resolve(res)
				}).catch(error => {
          reject(error)
        })
      	
      })
    },
    

    // 第三方验证登录
    // LoginByThirdparty({ commit, state }, code) {
    //   return new Promise((resolve, reject) => {
    //     commit('SET_CODE', code)
    //     loginByThirdparty(state.status, state.email, state.code).then(response => {
    //       commit('SET_TOKEN', response.data.token)
    //       setToken(response.data.token)
    //       resolve()
    //     }).catch(error => {
    //       reject(error)
    //     })
    //   })
    // },

    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          commit('SET_TOKEN', '')
          removeToken()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeToken()
        resolve()
      })
    },

    // 动态修改权限
    ChangeRoles({ commit, dispatch }, role) {
      return new Promise(resolve => {
        commit('SET_TOKEN', role)
        setToken(role)
        getUserInfo(role).then(response => {
          const data = response.data
          commit('SET_NAME', data.name)
          commit('SET_AVATAR', data.avatar)
          commit('SET_INTRODUCTION', data.introduction)
          dispatch('GenerateRoutes', data) // 动态修改权限后 重绘侧边菜单
          resolve()
        })
      })
    }
  }
}

export default user
