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
  //config.headers['Content-Type'] = 'application/x-www-form-urlencoded';
	config.headers['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
	
	
  // Do something before request is sent
//if (store.getters.token) {
if(getToken()){
    // 让每个请求携带token-- ['X-Token']为自定义key 请根据实际情况自行修改
    config.headers['X-Token'] = getToken()
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


service.interceptors.response.use(
    response => {
    	const res = response.data;
    	//debugger
    	// 如果文件流 就直接返回 （下载excel）
    	if(res.type=='application/octet-stream' || res.type=='application/octet-stream;charset=UTF-8'){
    		return res
    	}
    	if (res.errCode != 0) { //系统错误
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
        }else{
        	Message({
	          message: res.errMsg,
	          type: 'error',
	          duration: 5 * 1000
	        });
        }
        return Promise.reject('error')

    	}else { // 系统正常
//  		Message({
//        message: res.errMsg,
//        type: 'success',
//        duration: 5 * 1000
//      });
         return response.data;
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
    
    //error => {
//  console.log('err' + error) // for debug
//  Message({
//    message: error.Error,
//    type: 'error',
//    duration: 5 * 1000
//  });
//  return Promise.reject(error);
//}
);
export default service
