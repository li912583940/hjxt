<template>
	<div id="" style="margin-left: 10%;margin-top: 40px;">
		<el-card shadow="never" style="width: 50%;">
			<span v-if="buttonRole.confPermission==1">
				<el-switch
				  style="display: block"
				  v-model="noticeValue"
				  @change="noticeChange"
				  inactive-color="#ff4949"
				  active-color="#13ce66"
				  inactive-text="身份验证成功发起会见通知"
				  active-text="登记完成发起会见通知"
				 >
				</el-switch>
			</span>
			<span else>
				<el-switch
				  style="display: block"
				  v-model="noticeValue"
				  @change="noticeChange"
				  inactive-color="#ff4949"
				  active-color="#13ce66"
				  inactive-text="身份验证成功发起会见通知"
				  active-text="登记完成发起会见通知"
				  :disabled="true"
				 >
				</el-switch>
			</span>
			
		</el-card>
	</div>
</template>

<script>
import { FindNotice, NoticeChange } from '@/api/noticeSet'

import moment from 'moment';
import waves from '@/directive/waves' // 水波纹指令


export default {
  name: 'noticeSet',
  directives: {
    waves
  },
  data() {
    return {
      noticeValue: true,
      
      //按钮权限   1：有权限， 0：无权限
      buttonRole: { 
      	queryPermission: 1, 
      	confPermission: 0,
      },
      
    }
  },
  filters: {
    dateFormat(date) {
		//时间格式化  
	    if (date == undefined) {  
	      return "";  
	    }  
	    return moment(date).format("YYYY-MM-DD HH:mm:ss");  
	  }
	
  },
  created() {
    this.getList()
  },
  mounted() {
	this.setButtonRole()
  },
  destroyed() {
  },
  methods: {
    getList() {
      FindNotice().then((res) => {
      	let data = res.data
      	data.hjNotice==0?this.noticeValue=true:this.noticeValue=false
      	 
      })
    },
    noticeChange() {
    	let param ={
    		noticeValue: this.noticeValue==true?0:1
    	}
    	NoticeChange(param).then(res =>{
    		
    	})
    },
	setButtonRole() { //设置按钮的权限
    	let roles = sessionStorage.getItem("roles")
    	if(roles.includes('admin')){
    		this.buttonRole.confPermission= 1
    	}else{
    		let buttonRoles = JSON.parse(sessionStorage.getItem("buttonRoles"))
    		let noticeSet = buttonRoles.noticeSet
    		if(noticeSet.length>0){
    			for(let value of noticeSet){
    				if(value=='confPermission'){
    					this.buttonRole.confPermission= 1
    				}
    			}
    		}
    	}
    },
    
	dateFormats: function (val) {
		if(!val){
			return undefined
		}
		return moment(val).format("YYYY-MM-DD HH:mm:ss");
	},
  }
}
</script>

<style>
</style>