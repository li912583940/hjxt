import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/views/layout/Layout'

/* Router Modules */

/** note: Submenu only appear when children.length>=1
 *  detail see  https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 **/

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    roles: ['admin','editor']     will control the page roles (you can set multiple roles)
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
    noCache: true                if true ,the page will no be cached(default is false)
  }
**/
export const constantRouterMap = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path*',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/auth-redirect',
    component: () => import('@/views/login/authredirect'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/errorPage/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/errorPage/401'),
    hidden: true
  },
  {
    path: '',
    component: Layout,
    redirect: 'dashboard',
    children: [{
      path: 'dashboard',
      component: () => import('@/views/dashboard/index'),
      name: 'Dashboard',
      meta: { title: 'dashboard', icon: 'dashboard', noCache: true }
    }]
  },
  {
  	path: '/addHjDj',
    component: Layout,
  	children: [
	  	{
	  		path: '/addHjDj',
	  		component: () => import('@/views/meet-register/addHjDj'),
	  	}
  	],
  	hidden: true
  },
  { // 会见登记-添加家属
    path: '/addQs',
    component: Layout,
    children: [
      {
        path: '/addQs',
        component: () => import('@/views/meet-register/addQs'),
      }
    ],
    hidden: true
  },
  
  { // 罪犯管理-添加家属
    path: '/addCriQs',
    component: Layout,
    children: [
      {
        path: '/addCriQs',
        component: () => import('@/views/criminal/addQs'),
      }
    ],
    hidden: true
  },
]

export default new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

// 动态权限路由
export const asyncRouterMap = [
  { // 服刑人员管理
    path: '/criminal',
    component: Layout,
    //redirect: '/criminal',
    name: 'criminalManage',
    meta: {
      title: 'criminalManage',
      icon: 'peoples',
      roles:'criminalManage'
    },
    children: [
      { path: 'criminal', component: () => import('@/views/criminal/criminal'), name: 'criminal', meta: { title: 'criminal', icon: 'peoples', roles:'criminal' }}, //服刑人员
      { path: 'relatives', component: () => import('@/views/criminal/relatives'), name: 'relatives', meta: { title: 'relatives', icon: 'peoples', roles:'relatives'}} // 亲属
    ]
  },

  { // 会见登记
    path: '/meetRegister',
    component: Layout,
    meta: {
      roles:'meetRegister'
    },
    children: [{
      path: 'index',
      component: () => import('@/views/meet-register/index'),
      name: 'meetRegister',
      meta: { title: 'meetRegister', icon: 'excel', roles:'meetRegister' }
    }]
  },
  
{ // 身份验证
    path: '/sfYz',
    component: Layout,
    meta: {
      roles:'sfYz'
    },
    children: [{
      path: 'index',
      component: () => import('@/views/sfYz/index'),
      name: 'sfYz',
      meta: { title: 'sfYz', icon: 'form', roles:'sfYz' }
    }]
},

  { // 会见签到 -- 座位分配
    path: '/meetSign',
    component: Layout,
    meta: {
      roles:'meetSign'
    },
    children: [{
      path: 'index',
      component: () => import('@/views/meet-sign/index'),
      name: 'meetSign',
      meta: { title: 'meetSign', icon: 'form', roles:'meetSign' }
    }]
  },

  { // 会见通知
    path: '/meetNotice',
    component: Layout,
    meta: {
      roles:'meetNotice'
    },
    children: [{
      path: 'index',
      component: () => import('@/views/meet-notice/index'),
      name: 'meetNotice',
      meta: { title: 'meetNotice', icon: 'email', roles:'meetNotice' }
    }]
  },
  
//{ // 会见监控-图形
//  path: '/meetMonitorGraph',
//  component: Layout,
//  meta: {
//    roles:'meetMonitorGraph'
//  },
//  children: [{
//    path: 'index',
//    component: () => import('@/views/meet-monitor/graph'),
//    name: 'meetMonitorGraph',
//    meta: { title: 'meetMonitorGraph', icon: 'eye', roles:'meetMonitorGraph' }
//  }]
//},
  
  { // 会见监控
    path: '/meetMonitor',
    component: Layout,
    meta: {
      roles:'meetMonitor'
    },
    children: [{
      path: 'index',
      component: () => import('@/views/meet-monitor/index'),
      name: 'meetMonitor',
      meta: { title: 'meetMonitor', icon: 'eye', roles:'meetMonitor' }
    }]
  },
  
  
  
  { // 会见审批
    path: '/meetSp',
    component: Layout,
    meta: {
      roles:'meetSp'
    },
    children: [{
      path: 'index',
      component: () => import('@/views/meet-sp/index'),
      name: 'meetSp',
      meta: { title: 'meetSp', icon: 'documentation', roles:'meetSp' }
    }]
  },
  
  { // 警察信息
    path: '/yjMessage',
    component: Layout,
    meta: {
      roles:'yjMessage'
    },
    children: [{
      path: 'index',
      component: () => import('@/views/yj-message/index'),
      name: 'yjMessage',
      meta: { title: 'yjMessage', icon: 'people', roles:'yjMessage' }
    }]
  },
  
  { // 会见记录
    path: '/meetRecord',
    component: Layout,
    meta: {
      roles:'meetRecord'
    },
    children: [{
      path: 'index',
      component: () => import('@/views/log-record/meetRecord'),
      name: 'meetRecord',
      meta: { title: 'meetRecord', icon: 'form', roles:'meetRecord' }
    }]
  },
  
  { // 会见报表
    path: '/meetReport',
    component: Layout,
    meta: {
      roles:'meetReport'
    },
    children: [{
      path: 'index',
      component: () => import('@/views/log-record/meetReport'),
      name: 'meetReport',
      meta: { title: 'meetReport', icon: 'chart', roles:'meetReport' }
    }]
  },
  
  { // 日志记录
    path: '/logRecord',
    component: Layout,
    //redirect: '/logRecord',
    name: 'logRecord',
    meta: {
      title: 'logRecord',
      icon: 'clipboard',
      roles:'logRecord'
    },
    children: [
      { path: 'operationLog', component: () => import('@/views/log-record/operationLog'), name: 'operationLog', meta: { title: 'operationLog', icon: 'form', roles:'operationLog' }}, //操作日志
      { path: 'registerLog', component: () => import('@/views/log-record/registerLog'), name: 'registerLog', meta: { title: 'registerLog' , icon: 'form', roles:'registerLog'}}, //登记记录
      { path: 'spLog', component: () => import('@/views/log-record/spLog'), name: 'spLog', meta: { title: 'spLog' , icon: 'form', roles:'spLog'}}, //审批记录
//    { path: 'entranceGuard', component: () => import('@/views/log-record/entranceGuard'), name: 'entranceGuard', meta: { title: 'entranceGuard', icon: 'form', roles:'entranceGuard' }} // 门禁记录
      
    ]
  },
  
  
  { // 系统设置
    path: '/systemSet',
    component: Layout,
    //redirect: '/systemSet',
    name: 'systemSet',
    meta: {
      title: 'systemSet',
      icon: 'tab',
      roles:'systemSet'
    },
    children: [
      { path: 'sysUser', component: () => import('@/views/system-set/sysUser'), name: 'sysUser', meta: { title: 'sysUser', icon: 'user', roles:'sysUser' }}, //系统用户管理
      { path: 'sysRoles', component: () => import('@/views/system-set/sysRoles'), name: 'sysRoles', meta: { title: 'sysRoles', icon: 'user', roles:'sysRoles'}}, //系统权限配置
      { path: 'criminalLevel', component: () => import('@/views/system-set/criminalLevel'), name: 'criminalLevel', meta: { title: 'criminalLevel', icon: 'tree', roles:'criminalLevel' }}, //服刑人员级别
      { path: 'jqSet', component: () => import('@/views/system-set/jqSet'), name: 'jqSet', meta: { title: 'jqSet', icon: 'tab', roles:'jqSet' }}, //监区设置
      { path: 'holidaySet', component: () => import('@/views/system-set/holidaySet'), name: 'holidaySet', meta: { title: 'holidaySet', icon: 'tab', roles:'holidaySet' }}, //特殊会见日
      { path: 'spSet', component: () => import('@/views/system-set/spSet'), name: 'spSet', meta: { title: 'spSet', icon: 'tab', roles:'spSet' }}, //审批设置
      { path: 'lineSet', component: () => import('@/views/system-set/lineSet'), name: 'lineSet', meta: { title: 'lineSet', icon: 'tab', roles:'lineSet' }}, // 线路设置
      { path: 'gxManage', component: () => import('@/views/system-set/gxManage'), name: 'gxManage', meta: { title: 'gxManage', icon: 'tab', roles:'gxManage' }}, // 亲属关系
      { path: 'deptManage', component: () => import('@/views/system-set/deptManage'), name: 'deptManage', meta: { title: 'deptManage', icon: 'tab', roles:'deptManage' }}, // 部门管理
      { path: 'confSet', component: () => import('@/views/system-set/confSet'), name: 'confSet', meta: { title: 'confSet', icon: 'tab', roles:'confSet' }}, // 会见通知配置
      { path: 'sysParam', component: () => import('@/views/system-set/sysParam'), name: 'sysParam', meta: { title: 'sysParam', icon: 'tab', roles:'sysParam' }} // 系统参数
    ]
  },
 

  { path: '*', redirect: '/404', hidden: true }
]
