import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/views/layout/Layout'

/* Router Modules */
import componentsRouter from './modules/components'
import chartsRouter from './modules/charts'
import tableRouter from './modules/table'
import nestedRouter from './modules/nested'

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
    path: '/documentation',
    component: Layout,
    redirect: '/documentation/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/documentation/index'),
        name: 'Documentation',
        meta: { title: 'documentation', icon: 'documentation', noCache: true }
      }
    ]
  },
  {
    path: '/guide',
    component: Layout,
    redirect: '/guide/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/guide/index'),
        name: 'Guide',
        meta: { title: 'guide', icon: 'guide', noCache: true }
      }
    ]
  }
]

export default new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

export const asyncRouterMap = [
  { // 服刑人员管理
    path: '/criminal',
    component: Layout,
    redirect: '/criminal',
    name: 'criminalManage',
    meta: {
      title: 'criminalManage',
      icon: 'peoples'
    },
    children: [
      { path: 'criminal', component: () => import('@/views/criminal/criminal'), name: 'criminal', meta: { title: 'criminal', icon: 'peoples' }}, //服刑人员
      { path: 'relatives', component: () => import('@/views/criminal/relatives'), name: 'relatives', meta: { title: 'relatives', icon: 'peoples'}} // 亲属
    ]
  },

  { // 会见登记
    path: '/meetRegister',
    component: Layout,
    children: [{
      path: 'index',
      component: () => import('@/views/meet-register/index'),
      name: 'meetRegister',
      meta: { title: 'meetRegister', icon: 'excel' }
    }]
  },

  

  { // 会见签到 -- 座位分配
    path: '/meetSign',
    component: Layout,
    children: [{
      path: 'index',
      component: () => import('@/views/meet-sign/index'),
      name: 'meetSign',
      meta: { title: 'meetSign', icon: 'form' }
    }]
  },

  { // 会见通知
    path: '/meetNotice',
    component: Layout,
    children: [{
      path: 'index',
      component: () => import('@/views/meet-notice/index'),
      name: 'meetNotice',
      meta: { title: 'meetNotice', icon: 'email' }
    }]
  },
  
  { // 会见监控
    path: '/meetMonitor',
    component: Layout,
    children: [{
      path: 'index',
      component: () => import('@/views/meet-monitor/index'),
      name: 'meetMonitor',
      meta: { title: 'meetMonitor', icon: 'eye' }
    }]
  },
  
  { // 会见审批
    path: '/meetSp',
    component: Layout,
    children: [{
      path: 'index',
      component: () => import('@/views/meet-sp/index'),
      name: 'meetSp',
      meta: { title: 'meetSp', icon: 'documentation' }
    }]
  },
  
  { // 警察信息
    path: '/yjMessage',
    component: Layout,
    children: [{
      path: 'index',
      component: () => import('@/views/yj-message/index'),
      name: 'yjMessage',
      meta: { title: 'yjMessage', icon: 'people' }
    }]
  },
  
  { // 会见记录
    path: '/meetRecord',
    component: Layout,
    children: [{
      path: 'index',
      component: () => import('@/views/log-record/meetRecord'),
      name: 'meetRecord',
      meta: { title: 'meetRecord', icon: 'form' }
    }]
  },
  
  { // 会见报表
    path: '/meetReport',
    component: Layout,
    children: [{
      path: 'index',
      component: () => import('@/views/log-record/meetReport'),
      name: 'meetReport',
      meta: { title: 'meetReport', icon: 'chart' }
    }]
  },
  
  { // 日志记录
    path: '/logRecord',
    component: Layout,
    redirect: '/logRecord',
    name: 'logRecord',
    meta: {
      title: 'logRecord',
      icon: 'clipboard'
    },
    children: [
      { path: 'operationLog', component: () => import('@/views/log-record/operationLog'), name: 'operationLog', meta: { title: 'operationLog', icon: 'form' }}, //操作日志
      { path: 'registerLog', component: () => import('@/views/log-record/registerLog'), name: 'registerLog', meta: { title: 'registerLog' , icon: 'form'}}, //登记记录
      { path: 'entranceGuard', component: () => import('@/views/log-record/entranceGuard'), name: 'entranceGuard', meta: { title: 'entranceGuard', icon: 'form' }} // 门禁记录
      
    ]
  },
  
  
  { // 系统设置
    path: '/systemSet',
    component: Layout,
    redirect: '/systemSet',
    name: 'systemSet',
    meta: {
      title: 'systemSet',
      icon: 'tab'
    },
    children: [
      { path: 'sysUser', component: () => import('@/views/system-set/sysUser'), name: 'sysUser', meta: { title: 'sysUser', icon: 'user' }}, //系统用户管理
      { path: 'sysRoles', component: () => import('@/views/system-set/sysRoles'), name: 'sysRoles', meta: { title: 'sysRoles', icon: 'user'}}, //系统权限配置
      { path: 'criminalLevel', component: () => import('@/views/system-set/criminalLevel'), name: 'criminalLevel', meta: { title: 'criminalLevel', icon: 'tree' }}, //服刑人员级别
      { path: 'jqSet', component: () => import('@/views/system-set/jqSet'), name: 'jqSet', meta: { title: 'jqSet', icon: 'tab' }}, //监区设置
      { path: 'lineSet', component: () => import('@/views/system-set/lineSet'), name: 'lineSet', meta: { title: 'lineSet', icon: 'tab' }}, // 线路设置
      { path: 'sysParam', component: () => import('@/views/system-set/sysParam'), name: 'sysParam', meta: { title: 'sysParam', icon: 'tab' }} // 系统参数
    ]
  },
 
//{
//  path: '/permission',
//  component: Layout,
//  redirect: '/permission/index',
//  alwaysShow: true, // will always show the root menu
//  meta: {
//    title: 'permission',
//    icon: 'lock',
//    roles: ['admin', 'editor'] // you can set roles in root nav
//  },
//  children: [
//    {
//      path: 'page',
//      component: () => import('@/views/permission/page'),
//      name: 'PagePermission',
//      meta: {
//        title: 'pagePermission',
//        roles: ['admin'] // or you can only set roles in sub nav
//      }
//    },
//    {
//      path: 'directive',
//      component: () => import('@/views/permission/directive'),
//      name: 'DirectivePermission',
//      meta: {
//        title: 'directivePermission'
//        // if do not set roles, means: this page does not require permission
//      }
//    }
//  ]
//},
//
//{
//  path: '/icon',
//  component: Layout,
//  children: [
//    {
//      path: 'index',
//      component: () => import('@/views/svg-icons/index'),
//      name: 'Icons',
//      meta: { title: 'icons', icon: 'icon', noCache: true }
//    }
//  ]
//},
//
///** When your routing table is too long, you can split it into small modules**/
//componentsRouter,
//chartsRouter,
//nestedRouter,
//tableRouter,
//
//{
//  path: '/example',
//  component: Layout,
//  redirect: '/example/list',
//  name: 'Example',
//  meta: {
//    title: 'example',
//    icon: 'example'
//  },
//  children: [
//    {
//      path: 'create',
//      component: () => import('@/views/example/create'),
//      name: 'CreateArticle',
//      meta: { title: 'createArticle', icon: 'edit' }
//    },
//    {
//      path: 'edit/:id(\\d+)',
//      component: () => import('@/views/example/edit'),
//      name: 'EditArticle',
//      meta: { title: 'editArticle', noCache: true },
//      hidden: true
//    },
//    {
//      path: 'list',
//      component: () => import('@/views/example/list'),
//      name: 'ArticleList',
//      meta: { title: 'articleList', icon: 'list' }
//    }
//  ]
//},
//
//{
//  path: '/tab',
//  component: Layout,
//  children: [
//    {
//      path: 'index',
//      component: () => import('@/views/tab/index'),
//      name: 'Tab',
//      meta: { title: 'tab', icon: 'tab' }
//    }
//  ]
//},
//
//{
//  path: '/error',
//  component: Layout,
//  redirect: 'noredirect',
//  name: 'ErrorPages',
//  meta: {
//    title: 'errorPages',
//    icon: '404'
//  },
//  children: [
//    {
//      path: '401',
//      component: () => import('@/views/errorPage/401'),
//      name: 'Page401',
//      meta: { title: 'page401', noCache: true }
//    },
//    {
//      path: '404',
//      component: () => import('@/views/errorPage/404'),
//      name: 'Page404',
//      meta: { title: 'page404', noCache: true }
//    }
//  ]
//},
//
//{
//  path: '/error-log',
//  component: Layout,
//  redirect: 'noredirect',
//  children: [
//    {
//      path: 'log',
//      component: () => import('@/views/errorLog/index'),
//      name: 'ErrorLog',
//      meta: { title: 'errorLog', icon: 'bug' }
//    }
//  ]
//},
//
//{
//  path: '/excel',
//  component: Layout,
//  redirect: '/excel/export-excel',
//  name: 'Excel',
//  meta: {
//    title: 'excel',
//    icon: 'excel'
//  },
//  children: [
//    {
//      path: 'export-excel',
//      component: () => import('@/views/excel/exportExcel'),
//      name: 'ExportExcel',
//      meta: { title: 'exportExcel' }
//    },
//    {
//      path: 'export-selected-excel',
//      component: () => import('@/views/excel/selectExcel'),
//      name: 'SelectExcel',
//      meta: { title: 'selectExcel' }
//    },
//    {
//      path: 'upload-excel',
//      component: () => import('@/views/excel/uploadExcel'),
//      name: 'UploadExcel',
//      meta: { title: 'uploadExcel' }
//    }
//  ]
//},
//
//{
//  path: '/zip',
//  component: Layout,
//  redirect: '/zip/download',
//  alwaysShow: true,
//  meta: { title: 'zip', icon: 'zip' },
//  children: [
//    {
//      path: 'download',
//      component: () => import('@/views/zip/index'),
//      name: 'ExportZip',
//      meta: { title: 'exportZip' }
//    }
//  ]
//},
//
//{
//  path: '/theme',
//  component: Layout,
//  redirect: 'noredirect',
//  children: [
//    {
//      path: 'index',
//      component: () => import('@/views/theme/index'),
//      name: 'Theme',
//      meta: { title: 'theme', icon: 'theme' }
//    }
//  ]
//},
//
//{
//  path: '/clipboard',
//  component: Layout,
//  redirect: 'noredirect',
//  children: [
//    {
//      path: 'index',
//      component: () => import('@/views/clipboard/index'),
//      name: 'ClipboardDemo',
//      meta: { title: 'clipboardDemo', icon: 'clipboard' }
//    }
//  ]
//},
//
//{
//  path: '/i18n',
//  component: Layout,
//  children: [
//    {
//      path: 'index',
//      component: () => import('@/views/i18n-demo/index'),
//      name: 'I18n',
//      meta: { title: 'i18n', icon: 'international' }
//    }
//  ]
//},
//
//{
//  path: 'external-link',
//  component: Layout,
//  children: [
//    {
//      path: 'https://github.com/PanJiaChen/vue-element-admin',
//      meta: { title: 'externalLink', icon: 'link' }
//    }
//  ]
//},

  { path: '*', redirect: '/404', hidden: true }
]
