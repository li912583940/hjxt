<template>
  <div class="navbar">
    <hamburger :toggle-click="toggleSideBar" :is-active="sidebar.opened" class="hamburger-container"/>

    <breadcrumb class="breadcrumb-container"/>
		
    <div class="right-menu">
    	<span v-if="isHjDjAcd==1"  style="margin-right: 50px;">
    			
    			<font color="red">普通座位：<span v-if="yanjian!=null">{{yanjian}}</span>; 特殊座位：<span v-if="kuanjian!=null">{{kuanjian}}</span></font>
    	</span>
    	
    	<span v-if="isHjDjAcd==1 && grantIp==1"  style="margin-right: 50px;">
    			
    			<font color="red">排队序号：<span v-if="acdInfo!=null">{{acdInfo.acdgetno}}</span>; 叫号序号：<span v-if="acdInfo!=null">{{acdInfo.acdsetno}}</span></font>
    			<el-button size="mini" type="info"   @click="nextCallNum">下一位</el-button>
    	</span>
    	
      <template v-if="device!=='mobile'">
        <error-log class="errLog-container right-menu-item"/>

        <el-tooltip :content="$t('navbar.screenfull')" effect="dark" placement="bottom">
          <screenfull class="screenfull right-menu-item"/>
        </el-tooltip>

        <el-tooltip :content="$t('navbar.size')" effect="dark" placement="bottom">
          <size-select class="international right-menu-item"/>
        </el-tooltip>

        <!--<lang-select class="international right-menu-item"/>-->

        <el-tooltip :content="$t('navbar.theme')" effect="dark" placement="bottom">
          <theme-picker class="theme-switch right-menu-item"/>
        </el-tooltip>
      </template>

      <el-dropdown class="avatar-container right-menu-item" trigger="click">
        <div class="avatar-wrapper">
          <img :src="imagephoto" class="user-avatar">
          <i class="el-icon-caret-bottom"/>
        </div>
        <el-dropdown-menu slot="dropdown">
          <router-link to="/">
            <el-dropdown-item>
              {{ $t('navbar.dashboard') }}
            </el-dropdown-item>
          </router-link>
          <a target="_blank" @click="openDriver">
            <el-dropdown-item>
              	下载驱动
            </el-dropdown-item>
          </a>
          <a target="_blank" @click="openEditUser">
            <el-dropdown-item>
              	修改密码
            </el-dropdown-item>
          </a>
          <a target="_blank" @click="resetUserPassword">
            <el-dropdown-item>
              	重置密码
            </el-dropdown-item>
          </a>
          <el-dropdown-item divided>
            <span style="display:block;" @click="logout">{{ $t('navbar.logOut') }}</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    
    <!--下载驱动 -->
    <el-dialog title="下载驱动" :visible.sync="dialogDriverVisible" width="800px" :modal-append-to-body="false">
      <table  class="gridtable">
      	<tr height="20px">
      		<th width="15%">序号</th>
      		<th width="15%">操作系统</th>
      		<th width="50%">驱动名称</th>
      		<th width="20%">操作</th>
      	</tr>
	  	 	<tr>
	  	 		<td>1</td>
	  	 		<td>全部</td>
	  	 		<td>网页所需控件</td>
	  	 		<td><a href="/static/Drivers/AllOcx.rar"><el-button type="primary"  size="mini" v-waves icon="el-icon-download">下载</el-button></a></td>
	  	 	</tr>
	  	 	<tr>
	  	 		<td>2</td>
	  	 		<td>全部</td>
	  	 		<td>IC读卡器驱动</td>
	  	 		<td><a href="/static/Drivers/CH341SER.rar"><el-button type="primary"  size="mini" v-waves icon="el-icon-download">下载</el-button></a></td>
	  	 	</tr>
	  	 	<tr>
	  	 		<td>3</td>
	  	 		<td>全部</td>
	  	 		<td>佳博热敏打印驱动</td>
	  	 		<td><a href="/static/Drivers/GAINSCHAV18.exe"><el-button type="primary"  size="mini" v-waves icon="el-icon-download">下载</el-button></a></td>
	  	 	</tr>
	  	 	<tr>
	  	 		<td>4</td>
	  	 		<td>全部</td>
	  	 		<td>海康Hik_DSFilters_Setup</td>
	  	 		<td><a href="/static/Drivers/Hik_DSFilters_Setup.exe"><el-button type="primary"  size="mini" v-waves icon="el-icon-download">下载</el-button></a></td>
	  	 	</tr>
	  	 	<tr>
	  	 		<td>5</td>
	  	 		<td>全部</td>
	  	 		<td>海康Hik_MFPlugins_Setup</td>
	  	 		<td><a href="/static/Drivers/Hik_MFPlugins_Setup.exe"><el-button type="primary"  size="mini" v-waves icon="el-icon-download">下载</el-button></a></td>
	  	 	</tr>
	  	 	<tr>
	  	 		<td>6</td>
	  	 		<td>全部</td>
	  	 		<td>ID-Ocx</td>
	  	 		<td><a href="/static/Drivers/ID-Ocx.rar"><el-button type="primary"  size="mini" v-waves icon="el-icon-download">下载</el-button></a></td>
	  	 	</tr>
	  	 	<tr>
	  	 		<td>7</td>
	  	 		<td>WindowsServer2003</td>
	  	 		<td>IE8-WindowsServer2003-x86-CHS</td>
	  	 		<td><a href="/static/Drivers/IE8-WindowsServer2003-x86-CHS.exe"><el-button type="primary"  size="mini" v-waves icon="el-icon-download">下载</el-button></a></td>
	  	 	</tr>
	  	 	<tr>
	  	 		<td>8</td>
	  	 		<td>WindowsXP-x86</td>
	  	 		<td>IE8-WindowsXP-x86-CHS</td>
	  	 		<td><a href="/static/Drivers/IE8-WindowsXP-x86-CHS.exe"><el-button type="primary"  size="mini" v-waves icon="el-icon-download">下载</el-button></a></td>
	  	 	</tr>
	  	 	<tr>
	  	 		<td>9</td>
	  	 		<td>win7(XP)-32位</td>
	  	 		<td>身份证读卡器驱动</td>
	  	 		<td><a href="/static/Drivers/USBDrv3.0-Win32.msi"><el-button type="primary"  size="mini" v-waves icon="el-icon-download">下载</el-button></a></td>
	  	 	</tr>
	  	 	<tr>
	  	 		<td>10</td>
	  	 		<td>64位</td>
	  	 		<td>身份证读卡器驱动</td>
	  	 		<td><a href="/static/Drivers/Win7_64bit.rar"><el-button type="primary"  size="mini" v-waves icon="el-icon-download">下载</el-button></a></td>
	  	 	</tr>
	  	 	<tr>
	  	 		<td>11</td>
	  	 		<td>win10-32位</td>
	  	 		<td>身份证读卡器驱动</td>
	  	 		<td><a href="/static/Drivers/二代证读验机具USB驱动.rar"><el-button type="primary"  size="mini" v-waves icon="el-icon-download">下载</el-button></a></td>
	  	 	</tr>
	  	 	<tr>
	  	 		<td>12</td>
	  	 		<td>全部</td>
	  	 		<td>二代证验证软件</td>
	  	 		<td><a href="/static/Drivers/二代证验证软件.rar"><el-button type="primary"  size="mini" v-waves icon="el-icon-download">下载</el-button></a></td>
	  	 	</tr>
	  	 	<tr>
	  	 		<td>13</td>
	  	 		<td>全部</td>
	  	 		<td>宇视NVR网页控件</td>
	  	 		<td><a href="/static/Drivers/VideoOcx.rar"><el-button type="primary"  size="mini" v-waves icon="el-icon-download">下载</el-button></a></td>
	  	 	</tr>
	  	</table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogDriverVisible = false">关 闭</el-button>
      </div>
    </el-dialog>
    
    <!-- 新增或编辑 -->
    <el-dialog title="修改密码" :visible.sync="dialogFormVisible" width="600px" :modal-append-to-body="false">
      <el-form :rules="rules" :model="dataForm" ref="dataForm" label-position="right" label-width="120px" style='width: 400px; margin-left:10%;' >
        <el-form-item label="姓名">
          <el-input v-model="dataForm.userName" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="原始密码" prop="userPwdOld">
          <el-input v-model="dataForm.userPwdOld"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="userPwdNew">
          <el-input v-model="dataForm.userPwdNew"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateData">确 定</el-button>
      </div>
    </el-dialog>
    
    <object width="0px" height="0px" id="IDCard2" name="IDCard2"  codebase="../../../ocx/SynCardOcx1.CAB#version=1,0,0,1" classid="clsid:4B3CB088-9A00-4D24-87AA-F65C58531039">
		</object>
		<div v-for="(item, index) in sysHjServerList">
			<object :id="item.serverName" :name="item.serverName" codebase="../../../ocx/TeleQqOcx.ocx#version=1,0,0,1" classid="clsid:561E476B-6C4F-4FCC-A8CE-A85C7F781620" width="0px" height="0px">
			</object>
		</div>
		<audio id="audio1" :src="wavUrl"></audio>
	
		<!-- 拍照-->
		<!--<object id="camera" classid="clsid:792FD9B8-5917-45D2-889D-C49FD174D4E0"
		  codebase="../../../ocx/capProj1.ocx#version=1,0,0,0"
		  width=160
		  height=176
		  hspace=0
		  vspace=0>
		</object>-->
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import ErrorLog from '@/components/ErrorLog'
import Screenfull from '@/components/Screenfull'
import SizeSelect from '@/components/SizeSelect'
import LangSelect from '@/components/LangSelect'
import ThemePicker from '@/components/ThemePicker'

import { findNotTzList } from '@/api/meetNotice'
import { EditPassword, ResetUserPassword } from '@/api/login'
import { Message, MessageBox } from 'element-ui'
import { findSpNotice } from '@/api/meetSp'
import {findList as GetHjServerList} from '@/api/sysParam'
import {findHjDjAcd, NextCallNum} from '@/api/hjdjAcdInfo'
import {getSurplusZwCount} from '@/api/lineSet'

export default {
  components: {
    Breadcrumb,
    Hamburger,
    ErrorLog,
    Screenfull,
    SizeSelect,
    LangSelect,
    ThemePicker
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'name',
      'avatar',
      'device'
    ])
  },
  data() {
    return {
      imagephoto: '/static/image/user.jpg',
    	wavUrl: '/static/video/hjnotice.wav',
    	
    	dialogDriverVisible : false, //下载驱动
    	
    	dialogFormVisible: false,
    	dataForm: { 
        webid: undefined,
        userName: undefined,
        userPwdOld: undefined,
        userPwdNew: undefined,
      },
      acdInfo: null,
      acdindex: null,
      acdServerName: null,
      grantIp:null, //当前登记电脑的IP是否已录入到排队叫号表中，没有的话，就不提供排队叫号的功能
      
      yanjian:null,
      kuanjian:null,
      
      isHjNotice:0,
      isSpNotice:0,
      isHjDjAcd:0,
      
    	rules: {
        password: [{ required: true, message: '密码不能为空', trigger: 'blur' }]
      },
      // 注册控件
      sysHjServerList: null,
      
    }
  },
  created() {
  	this.getHjServerList()
  	this.getHjDjAcd()
  },
  mounted() {
  	if(this.setButtonRole()==1){
  		if(this.timer){
	  		this.clearInterval(this.timer)
	  	}else{
	  		this.timer = setInterval(() =>{
	  			if(this.isHjNotice==1){
	  				this.getHjNotice()
	  			}
	  			if(this.isSpNotice==1){
	  				this.getSpNotice()
	  			}
	  			if(this.isHjDjAcd==1){
	  				this.getHjDjAcd()
	  			}
	  		}, 5000) //5秒一次
	  	}
  	}

  },
  destroyed() {
  	if(this.timer){
  		clearInterval(this.timer)
  	}
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('toggleSideBar')
    },
    logout() {
      this.$store.dispatch('LogOut').then(() => {
        location.reload()// In order to re-instantiate the vue-router object to avoid bugs
      })
    },
    getHjNotice(){//--会见通知
    	findNotTzList({}).then(res => {
    		let list = res.list
    		if(list.length>0){
    			// 语音提示
          this.audioPaly()
    	
    			let obj = list[0]
    			this.$notify({
	          title: '会见通知',
	          message: obj.jqName+' '+obj.frName+" 有亲属登记会见，请在《会见通知》菜单查看",
	          position: 'bottom-right',
	          type: 'warning'
	        });
    		}
    		
    	})
    },
    setButtonRole() { //右下角弹窗提示 --会见通知 --会见审批
    	let bool = 0
    	let roles = sessionStorage.getItem("roles")
    	if(roles.includes('admin')){
    		bool=1
    		//this.isHjNotice=1
    		this.isSpNotice=1
    	}else{
    		let buttonRoles = JSON.parse(sessionStorage.getItem("buttonRoles"))
    		if(buttonRoles.meetNotice){
    			this.isHjNotice=1
    			bool=1
    		}
    		if(buttonRoles.meetSp){
    			this.isSpNotice=1
    			bool=1
    		}
    		if(buttonRoles.meetRegister){
    			this.isHjDjAcd=1
    			bool=1
    		}
    	}
    	return bool
    },
    
    getSpNotice() {// --会见审批
    	findSpNotice({}).then(res => {
				let count = res.data
				if(count>0){
					// 语音提示
          this.audioPaly()
          
					this.$notify({
	          title: '审批通知',
	          message: "您有新的审批需要处理，请前往《会见审批》菜单查看",
	          position: 'bottom-right',
	          type: 'warning'
	        });
				}
			})
    },
    getHjDjAcd(){
    	if(this.grantIp==null || this.grantIp==1){
    		findHjDjAcd({}).then(res=>{
	    		this.grantIp = res.grantIp
	    		if(res.grantIp==1){
	    			this.acdInfo = res.acdInfo
		    		this.acdindex = res.acdindex
		    		this.acdServerName = res.acdServerName
	    		}
	    	})
    	}
    	
    	getSurplusZwCount({}).then(res =>{
    		this.yanjian=res.yanjian
    		this.kuanjian=res.kuanjian
    	})
    },
    audioPaly() {
    	var audio1 = document.getElementById("audio1")
			audio1.currentTime = 0;
			//audio1.play()
    },
    nextCallNum(){ // 叫号下一位
    	let param={
    		acdindex:this.acdindex,
    		acdServerName:this.acdServerName
    	}
    	NextCallNum(param).then(res=>{
    		this.acdInfo = res.acdInfo
    	})
    },
    
    openDriver(){ // 下载驱动
    	this.dialogDriverVisible=true
    },
    //重置表单
		resetForm() {
			this.dataForm.webid= undefined
	    this.dataForm.userName= undefined
	 		this.dataForm.userPwdOld= undefined
	 		this.dataForm.userPwdNew= undefined
	  },
    openEditUser() { //打开修改密码弹框
    	this.resetForm()
    	
    	this.dialogFormVisible = true
    	let user = JSON.parse(sessionStorage.getItem("user"))
    	if(user){
    		this.dataForm.webid=user.webid
    		this.dataForm.userName=user.userName
    	}
    },
    
    updateData() {
    	EditPassword(this.dataForm).then(res =>{
    		Message({
	        message: '密码修改成功，下次登录时请使用新密码。',
		      type: 'success',
		      duration: 5 * 1000
	      });
    		this.dialogFormVisible = false
    	})
    	
    },
    
    resetUserPassword(){ //重置密码
    	this.$confirm('确认重置密码吗?', '提示', {
				type: 'warning'
			}).then(() => {
				let user = JSON.parse(sessionStorage.getItem("user"))
	    	if(user){
	    		let param = {
	    		webid: user.webid
	    	}
				ResetUserPassword(param).then((res) => {
					Message({
		        message: '密码重置成功。新的密码为123456，下次登录时请使用新密码。',
			      type: 'success',
			      duration: 5 * 1000
		      });
	    	}).catch(error => {
	    		
	      })
	    	}
				
			})
    },
   
    getHjServerList() { // 获取服务器用于注册控件
    	if(navigator.appVersion.indexOf("MSIE") != -1 || (navigator.appVersion.toLowerCase().indexOf("trident") > -1 && navigator.appVersion.indexOf("rv") > -1) ){ // IE浏览器
				GetHjServerList({}).then(res => {
					this.sysHjServerList = res.list
				})
			}
		},
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.navbar {
  height: 50px;
  line-height: 50px;
  border-radius: 0px !important;
  .hamburger-container {
    line-height: 58px;
    height: 50px;
    float: left;
    padding: 0 10px;
  }
  .breadcrumb-container{
    float: left;
  }
  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }
  .right-menu {
    float: right;
    height: 100%;
    &:focus{
     outline: none;
    }
    .right-menu-item {
      display: inline-block;
      margin: 0 8px;
    }
    .screenfull {
      height: 20px;
    }
    .international{
      vertical-align: top;
    }
    .theme-switch {
      vertical-align: 15px;
    }
    .avatar-container {
      height: 50px;
      margin-right: 30px;
      .avatar-wrapper {
        cursor: pointer;
        margin-top: 5px;
        position: relative;
        .user-avatar {
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }
        .el-icon-caret-bottom {
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
table.gridtable {
font-family: verdana,arial,sans-serif;
font-size:15px;
color:#333333;
border-width: 1px;
border-color: #76a5af;
width: 700px;
border-collapse: collapse;
margin-left: 30px;
}
table.gridtable th {
border-width: 1px;
padding: 1px;
border-style: solid;
border-color: #76a5af;
background-color: #dedede;
}
table.gridtable td {
border-width: 1px;
padding: 1px;
border-style: solid;
border-color: #76a5af;
background-color: #ffffff;
text-align:center;
}
</style>