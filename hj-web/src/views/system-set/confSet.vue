<template>
	<div id="" style="margin-left: 10%;margin-top: 40px;">
		<el-card shadow="always" style="width: 500px;" >
			<div style="margin-left: 15px;">
				<span v-if="buttonRole.confPermission==1">
					<el-switch
					  style="display: block"
					  v-model="dataForm.hjNotice"
					  @change="noticeChange"
					  inactive-color="#ff4949"
					  active-color="#13ce66"
					  inactive-text="身份验证成功发起会见通知"
					  active-text="登记完成发起会见通知"
					 >
					</el-switch>
				</span>
				<span v-if="buttonRole.confPermission==0">
					<el-switch
					  style="display: block"
					  v-model="dataForm.hjNotice"
					  inactive-color="#ff4949"
					  active-color="#13ce66"
					  inactive-text="身份验证成功发起会见通知"
					  active-text="登记完成发起会见通知"
					  :disabled="true"
					 >
					</el-switch>
				</span>
			</div>
		</el-card>
		
		<el-card shadow="always" style="width: 500px;margin-top: 40px;" >
			<div style="margin-left: 40px;">
				<span v-if="buttonRole.confPermission==1">
					<el-switch
					  style="display: block"
					  v-model="dataForm.printXp"
					  @change="printXpChange"
					  inactive-color="#ff4949"
					  active-color="#13ce66"
					  inactive-text="登记完成自动打印小票"
					  active-text="人工打印小票"
					 >
					</el-switch>
				</span>
				<span v-if="buttonRole.confPermission==0">
					<el-switch
					  style="display: block"
					  v-model="dataForm.printXp"
					  inactive-color="#ff4949"
					  active-color="#13ce66"
					  inactive-text="登记完成自动打印小票"
					  active-text="人工打印小票"
					  :disabled="true"
					 >
					</el-switch>
				</span>
			</div>
		</el-card>
		
		<el-card shadow="always" style="width: 500px;margin-top: 40px;" >
			<div style="margin-left: 40px;">
				<span v-if="buttonRole.confPermission==1">
					<el-switch
					  style="display: block"
					  v-model="dataForm.fpZw"
					  @change="fpZwChange"
					  inactive-color="#ff4949"
					  active-color="#13ce66"
					  inactive-text="登记完成自动分配座位"
					  active-text="登记完成不自动分配座位"
					 >
					</el-switch>
				</span>
				<span v-if="buttonRole.confPermission==0">
					<el-switch
					  style="display: block"
					  v-model="dataForm.fpZw"
					  inactive-color="#ff4949"
					  active-color="#13ce66"
					  inactive-text="登记完成自动分配座位"
					  active-text="登记完成不自动分配座位"
					  :disabled="true"
					 >
					</el-switch>
				</span>
			</div>
			<div style="margin-top: 20px;">
				<span style="color: red;"><font size="2">下面功能启用条件是选择 <登记完成自动分配座位> </font></span>
			</div>
			<div style="margin-top: 10px;">
				<span v-if="buttonRole.confPermission==1">
					<el-switch
					  style="display: block"
					  v-model="dataForm.saveHjdj"
					  @change="saveHjdjChange"
					  inactive-color="#ff4949"
					  active-color="#13ce66"
					  inactive-text="座位不够，可以继续提交登记"
					  active-text="座位不够，不让登记"
					 >
					</el-switch>
				</span>
				<span v-if="buttonRole.confPermission==0">
					<el-switch
					  style="display: block"
					  v-model="dataForm.saveHjdj"
					  inactive-color="#ff4949"
					  active-color="#13ce66"
					  inactive-text="座位不够，可以继续提交登记"
					  active-text="座位不够，不让登记"
					  :disabled="true"
					 >
					</el-switch>
				</span>
			</div>
		</el-card>
	</div>
</template>

<script>
import { FindConf, EditConf } from '@/api/confSet'

import moment from 'moment';
import waves from '@/directive/waves' // 水波纹指令


export default {
  name: 'noticeSet',
  directives: {
    waves
  },
  data() {
    return {
      dataForm:{
      	id: undefined,
      	hjNotice: true,
      	printXp: true,
      	fpZw: true,
      	saveHjdj: true,
      },
      
      
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
      FindConf().then((res) => {
      	let data = res.data
      	this.dataForm.id = data.id
      	data.hjNotice==0?this.dataForm.hjNotice=true:this.dataForm.hjNotice=false
      	data.printXp==0?this.dataForm.printXp=true:this.dataForm.printXp=false
      	data.fpZw==0?this.dataForm.fpZw=true:this.dataForm.fpZw=false
      })
    },
    noticeChange() {
    	let param ={
    		id: this.dataForm.id,
    		hjNotice: this.dataForm.hjNotice==true?0:1
    	}
    	EditConf(param).then(res =>{
    		
    	})
    },
    printXpChange(){
    	let param = {
    		id:this.dataForm.id,
    		printXp: this.dataForm.printXp==true?0:1
    	}
    	EditConf(param).then(res =>{
    		
    	})
    },
    fpZwChange(){
    	let param={
    		id:this.dataForm.id,
    		fpZw: this.dataForm.fpZw==true?0:1
    	}
    	EditConf(param).then(res =>{
    		
    	})
    },
    saveHjdjChange(){
    	let param={
    		id:this.dataForm.id,
    		saveHjdj: this.dataForm.saveHjdj==true?0:1
    	}
    	EditConf(param).then(res =>{
    		
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