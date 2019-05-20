<template>
	<div id="" style="margin-left: 10%;margin-top: 40px;">
		
		<el-card shadow="always" style="width: 500px;" >
			<div style="margin-left: 33px;">
				<span>
					<el-switch
					  style="display: block"
					  v-model="dataForm.hjdjSwitch"
					  @change="hjdjSwitchChange"
					  inactive-color="#ff4949"
					  active-color="#13ce66"
					  inactive-text="关闭会见登记验证总开关"
					  active-text="开启会见登记验证总开关"
					  :disabled="permissionBoolean"
					 >
					</el-switch>
				</span>
			</div>
			<div style="margin-top: 10px;">
				<span style="color: red;margin-top: 10px;"> 
					<font size="2">
						注意：当会见验证总开关关闭后。提交会见登记时将没有任何条件限制。相关功能如：<审批><特殊会见日><会见级别禁止>等都将关闭
					</font>
				</span>
			</div>
		</el-card>
		
		<el-card shadow="always" style="width: 500px;margin-top: 40px;" >
			<div style="margin-left: 18px;">
				<span>
					<el-switch
					  style="display: block"
					  v-model="dataForm.hjNotice"
					  @change="noticeChange"
					  inactive-color="#ff4949"
					  active-color="#13ce66"
					  inactive-text="身份验证成功发起会见通知"
					  active-text="登记完成发起会见通知"
					  :disabled="permissionBoolean"
					 >
					</el-switch>
				</span>
			</div>
		</el-card>
		
		<el-card shadow="always" style="width: 500px;margin-top: 40px;" >
			<div style="margin-left: 44px;">
				<span>
					<el-switch
					  style="display: block"
					  v-model="dataForm.printXp"
					  @change="printXpChange"
					  inactive-color="#ff4949"
					  active-color="#13ce66"
					  inactive-text="登记完成自动打印小票"
					  active-text="人工打印小票"
					  :disabled="permissionBoolean"
					 >
					</el-switch>
				</span>
			</div>
			
			<div style="margin-left: 43px; margin-top: 20px;">
				<span>
					<el-switch
					  style="display: block"
					  v-model="dataForm.editDjXp"
					  @change="editDjXpChange"
					  inactive-color="#ff4949"
					  active-color="#13ce66"
					  inactive-text="修改登记自动打印小票"
					  active-text="修改登记不打印小票"
					  :disabled="permissionBoolean"
					 >
					</el-switch>
				</span>
			</div>
			
			<div style="margin-left: 57px; margin-top: 20px;">
				<span>
					<el-switch
					  style="display: block"
					  v-model="dataForm.printFormat"
					  @change="printFormatChange"
					  inactive-color="#ff4949"
					  active-color="#13ce66"
					  inactive-text="打印格式：热敏打印"
					  active-text="打印格式：A4打印"
					  :disabled="permissionBoolean"
					 >
					</el-switch>
				</span>
			</div>
		</el-card>
		
		<el-card shadow="always" style="width: 500px;margin-top: 40px;" >
			<div style="margin-left: 42px;">
				<span>
					<el-switch
					  style="display: block"
					  v-model="dataForm.fpZw"
					  @change="fpZwChange"
					  inactive-color="#ff4949"
					  active-color="#13ce66"
					  inactive-text="登记完成自动分配座位"
					  active-text="登记完成不自动分配座位"
					  :disabled="permissionBoolean"
					 >
					</el-switch>
				</span>
			</div>
		
			<div style="margin-top: 20px;">
				<span>
					<el-switch
					  style="display: block"
					  v-model="dataForm.saveHjdj"
					  @change="saveHjdjChange"
					  inactive-color="#ff4949"
					  active-color="#13ce66"
					  inactive-text="座位不够，可以继续提交登记"
					  active-text="座位不够，不让登记"
					  :disabled="saveHjdjBoolean"
					 >
					</el-switch>
				</span>
			</div>
		</el-card>
		
		<el-card shadow="always" style="width: 500px;margin-top: 40px;" >
			<div style="margin-left: 18px;">
				<span style="color: red;">
					配置会见类型不记录罪犯会见次数 
				</span>
				<span style="margin-left: 10px;">
					<el-button v-if="buttonRole.confPermission==1" size="mini" type="info" icon="el-icon-setting" @click="openHjType">配置</el-button>
				</span>
			</div>
		</el-card>
		
		<!-- 配置AB门 -->
		<el-card shadow="always" style="width: 500px;margin-top: 40px;" >
			<div style="margin-left: 18px;">
				<span>
					<el-switch
					  style="display: block"
					  v-model="dataForm.abmsHttp"
					  @change="abmsHttpChange"
					  inactive-color="#ff4949"
					  active-color="#13ce66"
					  inactive-text="关闭AB门数据推送"
					  active-text="开启AB门数据推送"
					  :disabled="permissionBoolean"
					 >
					</el-switch>
				</span>
			</div>
		</el-card>
		
		<!-- 添加会见类型 -->
		<el-dialog title="添加会见类型" :visible.sync="dialogHjTypeVisible" width="740px" :modal-append-to-body="false">
			<el-card style="width: 540px; margin-left: 10%;">
				<el-transfer
				    filterable
				    :filter-method="hjTypeFilter"
				    filter-placeholder="请输入关键字搜索"
				    v-model="hjTypeValue"
				    :data="hjTypeData"
				    :titles="['未拥有类型', '拥有类型']">
				</el-transfer>
		    </el-card>
		    <div slot="footer" class="dialog-footer">
		        <el-button @click="dialogHjTypeVisible = false">取 消</el-button>
		        <el-button type="primary" @click="updateHjType">确 定</el-button>
	        </div>
		</el-dialog>
		
	</div>
</template>

<script>
import { FindConf, EditConf, GetCheckedHjType } from '@/api/confSet'

import moment from 'moment';
import waves from '@/directive/waves' // 水波纹指令
import { Message, MessageBox } from 'element-ui'

export default {
  name: 'noticeSet',
  directives: {
    waves
  },
  data() {
    return {
      dataForm:{
      	id: undefined,
      	hjdjSwitch: true,
      	hjNotice: true,
      	printXp: true,
      	editDjXp:true,
      	printFormat:true,
      	fpZw: true,
      	saveHjdj: true,
      	hjTypes: undefined,
      	abmsHttp: true,
      },
      
      permissionBoolean:true,
      
      saveHjdjBoolean:false,
      
      //按钮权限   1：有权限， 0：无权限
      buttonRole: { 
      	queryPermission: 1, 
      	confPermission: 0,
      },
      
      hjTypeData:[
        {
      		key: 1,
      		label: '亲属会见'
      	},
      	{
      		key: 2,
      		label: '监护人会见'
      	},
      	{
      		key: 3,
      		label: '律师会见'
      	},
      	{
      		key: 4,
      		label: '使领馆探视'
      	},
      	{
      		key: 5,
      		label: '提审会见'
      	},
      	{
      		key: 6,
      		label: '公务会见'
      	},
      	{
      		key: 9,
      		label: '特批会见'
      	},
      	{
      		key: 99,
      		label: '其他会见'
      	},
      ],
      hjTypeValue:[],
      dialogHjTypeVisible:false,
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
      	data.hjdjSwitch==0?this.dataForm.hjdjSwitch=true:this.dataForm.hjdjSwitch=false
      	data.hjNotice==0?this.dataForm.hjNotice=true:this.dataForm.hjNotice=false
      	data.printXp==0?this.dataForm.printXp=true:this.dataForm.printXp=false
      	data.editDjXp==0?this.dataForm.editDjXp=true:this.dataForm.editDjXp=false
      	data.printFormat==0?this.dataForm.printFormat=true:this.dataForm.printFormat=false
      	data.fpZw==0?this.dataForm.fpZw=true:this.dataForm.fpZw=false
      	if(data.fpZw==0){
      		this.saveHjdjBoolean=true
      	}else{
      		this.saveHjdjBoolean=false
      	}
      	data.saveHjdj==0?this.dataForm.saveHjdj=true:this.dataForm.saveHjdj=false
      	data.abmsHttp==0?this.dataForm.abmsHttp=true:this.dataForm.abmsHttp=false
      })
    },
    hjdjSwitchChange(){
    	let param ={
    		id: this.dataForm.id,
    		hjdjSwitch: this.dataForm.hjdjSwitch==true?0:1
    	}
    	EditConf(param).then(res =>{
    		Message({
		        message: res.errMsg,
			    type: 'success',
			    duration: 5 * 1000
		    });
    	})
    },
    noticeChange() {
    	let param ={
    		id: this.dataForm.id,
    		hjNotice: this.dataForm.hjNotice==true?0:1
    	}
    	EditConf(param).then(res =>{
    		Message({
		        message: res.errMsg,
			    type: 'success',
			    duration: 5 * 1000
		    });
    	})
    },
    printXpChange(){
    	let param = {
    		id:this.dataForm.id,
    		printXp: this.dataForm.printXp==true?0:1
    	}
    	EditConf(param).then(res =>{
    		Message({
		        message: res.errMsg,
			    type: 'success',
			    duration: 5 * 1000
		    });
    	})
    },
    editDjXpChange(){
    	let param = {
    		id:this.dataForm.id,
    		editDjXp: this.dataForm.editDjXp==true?0:1
    	}
    	EditConf(param).then(res =>{
    		Message({
		        message: res.errMsg,
			    type: 'success',
			    duration: 5 * 1000
		    });
    	})
    },
    printFormatChange(){
    	let param = {
    		id:this.dataForm.id,
    		printFormat: this.dataForm.printFormat==true?0:1
    	}
    	EditConf(param).then(res =>{
    		Message({
		        message: res.errMsg,
			    type: 'success',
			    duration: 5 * 1000
		    });
    	})
    },
    
    fpZwChange(){
    	if(this.dataForm.fpZw==true){
    		this.saveHjdjBoolean=true
    	}else{
    		this.saveHjdjBoolean=false
    	}
    	let param={
    		id:this.dataForm.id,
    		fpZw: this.dataForm.fpZw==true?0:1
    	}
    	EditConf(param).then(res =>{
    		Message({
		        message: res.errMsg,
			    type: 'success',
			    duration: 5 * 1000
		    });
    	})
    },
    saveHjdjChange(){
    	let param={
    		id:this.dataForm.id,
    		saveHjdj: this.dataForm.saveHjdj==true?0:1
    	}
    	EditConf(param).then(res =>{
    		Message({
		        message: res.errMsg,
			    type: 'success',
			    duration: 5 * 1000
		    });
    	})
    },
	setButtonRole() { //设置按钮的权限
    	let roles = sessionStorage.getItem("roles")
    	if(roles.includes('admin')){
    		this.buttonRole.confPermission= 1
    		this.permissionBoolean=false
    	}else{
    		let buttonRoles = JSON.parse(sessionStorage.getItem("buttonRoles"))
    		let confSet = buttonRoles.confSet
    		if(confSet.length>0){
    			for(let value of confSet){
    				if(value=='confPermission'){
    					this.buttonRole.confPermission= 1
    					this.permissionBoolean=false
    				}
    			}
    		}
    	}
    },
    openHjType(){
    	this.hjTypeValue=[] //先清空
    	this.dialogHjTypeVisible = true
    	GetCheckedHjType({}).then(res => {
	 		this.hjTypeValue = res.data
	 	})
    },
    updateHjType(){
    	let hjTypeValue = this.hjTypeValue.join()
		let param = {
			id:this.dataForm.id,
			hjTypes: hjTypeValue
		}
		EditConf(param).then(res =>{
    		Message({
		        message: res.errMsg,
			    type: 'success',
			    duration: 5 * 1000
		    });
    	})
		this.dialogHjTypeVisible = false
    },
    hjTypeFilter(query, item){ //穿梭框搜索功能
	  	return item.label.indexOf(query) > -1;
	},
	
	abmsHttpChange(){
		let param = {
    		id:this.dataForm.id,
    		abmsHttp: this.dataForm.abmsHttp==true?0:1
    	}
    	EditConf(param).then(res =>{
    		Message({
		        message: res.errMsg,
			    type: 'success',
			    duration: 5 * 1000
		    });
    	})
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