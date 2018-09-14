import Qs from 'qs';
import axios from 'axios'
import { Message, MessageBox } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'

// create an axios instance
const service = axios.create({
  baseURL: process.env.BASE_API, // api的base_url
  timeout: 5000, // request timeout
  //withCredentials: true,  //加了这段就可以跨域了
  transformRequest: [function (data) {
        data = Qs.stringify(data);
        return data
  }],
})

// request interceptor
service.interceptors.request.use(config => {
	config.headers['Accept'] = 'text/plain';
  config.headers['Content-Type'] = 'application/x-www-form-urlencoded';
  // Do something before request is sent
//if (store.getters.token) {
//  // 让每个请求携带token-- ['X-Token']为自定义key 请根据实际情况自行修改
//  config.headers['X-Token'] = getToken()
//}

	//对于/auth/**的请求路径，默认不添加token认证
	if (config.url.indexOf("/login") <0 && store.getters.token) {
	    config.headers['X-Token'] = getToken();
	}
	if (config.method == 'post') {
	    config.data = {
	        ...config.data
	    }
	} else if (config.method == 'get') {
	    config.params = {
	        ...config.params
	    }
	}
  return config
}, error => {
  // Do something with request error
  console.log(error) // for debug
  Promise.reject(error)
})

// respone interceptor
//service.interceptors.response.use(
//response => {
//		console.log(response);
//	  return response;
//	},
///**
// * 下面的注释为通过在response里，自定义code来标示请求状态
// * 当code返回如下情况则说明权限有问题，登出并返回到登录页
// * 如想通过xmlhttprequest来状态码标识 逻辑可写在下面error中
// * 以下代码均为样例，请结合自生需求加以修改，若不需要，则可删除
// */
//   response => {
//   	console.log(response);
//     const res = response.data
//     //console.log(res);
//     if (res.errCode != 0) {
//       Message({
//         message: res.errMsg,
//         type: 'error',
//         duration: 5 * 1000
//       })
//       // 101:无效token;
//       if (res.errCode === 101) {
//         // 请自行在引入 MessageBox
//         // import { Message, MessageBox } from 'element-ui'
//         MessageBox.confirm('你已被登出，可以取消继续留在该页面，或者重新登录', '确定登出', {
//           confirmButtonText: '重新登录',
//           cancelButtonText: '取消',
//           type: 'warning'
//         }).then(() => {
//           store.dispatch('FedLogOut').then(() => {
//             location.reload() // 为了重新实例化vue-router对象 避免bug
//           })
//         })
//       }
//       return Promise.reject('error')
//     } else {
//       return response.data
//     }
//   },
//error => {
//  console.log('err' + error) // for debug
//  Message({
//    message: error.Error,
//    type: 'error',
//    duration: 5 * 1000
//  });
//  return Promise.reject(error);
//}
//
//);


service.interceptors.response.use(
    response => {
    	//console.log(response);
    	const res = response.data;
    	if (res.errCode != 0) {
    		Message({
          message: res.errMsg,
          type: 'error',
          duration: 5 * 1000
        });
    		
    		// 101:无效token;
        if (res.errCode === 101) {
           // 请自行在引入 MessageBox
           // import { Message, MessageBox } from 'element-ui'
           MessageBox.confirm('登录已失效，可以取消继续留在该页面，或者重新登录', '确定登出', {
             confirmButtonText: '重新登录',
             cancelButtonText: '取消',
             type: 'warning'
           }).then(() => {
             store.dispatch('FedLogOut').then(() => {
               location.reload() // 为了重新实例化vue-router对象 避免bug
             })
           })
        }
        return Promise.reject('error')

    	}else {
    		Message({
          message: res.errMsg,
          type: 'success',
          duration: 5 * 1000
        });
         return response;
      }
    },
    
    error => {
    	console.log('msg: '+ error);
        if (error.response) {
            switch (error.response.status) {
                case 401:            /**token未授权或token授权失败，过期等等**/
                    // 401 清除token信息并跳转到登录页面
                    store.commit(types.LOGOUT);
                    break;
                case 404:            /**未找到页面**/
                  // 404 跳转到404页面
                  
                  break;
                case 406:
                	MessageBox({
	                    message: '提交参数不合法，无法通过效验请求',
	                    type: 'error',
	                    duration: 5 * 1000
                    });
                    break;
            }
        }
        // console.log(JSON.stringify(error));//console : Error: Request failed with status code 402
        //console.log('error  :'+error);
        return Promise.reject(error)
    }
);
export default service
