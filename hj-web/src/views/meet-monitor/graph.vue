<template>
	<div style="position: relative;width: 100%;margin-top: 50px; margin-bottom: 50px;">
        <el-row :gutter="12">
            <el-col :span="4" v-for="(item, index) in list" :key="index" :offset="1" style="margin-bottom:40px">
                <el-card  :body-style="{ padding: '0px', height:'360px'}" shadow="always" style="width: 260px;height: 320px;border:2px solid #CDC9C9; max-height: 500px;">
                	<div slot="header" class="clearfix">
					    <span style="float: left;">窗口：{{item.zw}}</span>
					    <span v-if="item.monitorState=='通话'">
					    	<el-button v-if="jtState==2 && buttonRole.qieduanPermission==1" style="float: right; padding: 3px 0;" type="text"><a @click="qieduan(item)">切断</a></el-button>
					    	<el-button v-if="jtState==2 && buttonRole.jiantingPermission==1" style="float: right; padding: 3px 0;margin-right: 6px;" type="text"><a @click="jiantingStop(item)">停止监听</a></el-button>
					    	<el-button v-if="jtState==1 && buttonRole.jiantingPermission==1" style="float: right; padding: 3px 0" type="text"><a @click="jianting(item)">监听</a></el-button>
					    </span>
					    <span v-else-if="item.monitorState=='应答'" style="float: right; padding: 3px 10; color: #FF9900;">应答</span>
					    <span v-else style="float: right; padding: 3px 10; color: #33CC00;">空闲</span>
					</div>
                    <div style="padding: 6px;height: 310px;">
                    	<div v-if="item.monitorState=='通话' ">
                    		<div v-if="buttonRole.chahuaPermission==1 || buttonRole.zhushiPermission==1"  style=" background:rgba(0,0,0,.5); position:relative; top:0px; color:#FFF; font-size:12px; padding:6px 0;">
						    	<span v-if="buttonRole.chahuaPermission==1" style="margin-left: 50px;"><a @click="chahua(item)">插话</a></span>
						    	<span v-if="buttonRole.zhushiPermission==1" style="margin-left: 20px;"><a @click="zhushi(item)">注释</a></span>
						    	<!--<span v-if="buttonRole.updateTimePermission==1" style="margin-left: 20px;"><a @click="xiugaiTime(item)">修改时间</a></span>-->
						    </div>
						    
	                        <div class="text item" style="margin-top: 10px;">监区 ： {{item.monitorJq}}</div>
						    <div class="text item">姓名 ： {{item.monitorFr}}</div>
						    <span v-for="(o, i) in item.qsList">
						    	<div class="text item">亲属{{i+1}}：{{o}}</div>
						    </span>
						    <div class="text item">剩余 ： {{item.monitorTime}}</div>
					    </div>
					    
                    </div>
                </el-card>
            </el-col>
        </el-row>
        <!-- 分页 -->
	    <div class="pagination-container" style="margin-left: 5%;">
	      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="listQuery.pageNum" :page-sizes="[10,20,30, 50]" :page-size="listQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
	      </el-pagination>
	    </div>
    
    	<el-dialog title="修改时间" :visible.sync="dialogSJVisible" width="600px" :modal-append-to-body="false">
	      <el-form :rules="rules" :model="dataForm" ref="dataForm" label-position="right" label-width="120px" style='width: 400px; margin-left:10%;' >
	        <el-form-item label="姓名" >
	          <el-input v-model="dataForm.frName" :disabled="true"></el-input>
	        </el-form-item>
	        <el-form-item label="时间" prop="timeUp" >
	          <el-input v-model="dataForm.timeUp" placeholder="10：延长10分钟，-10：减少10分钟"></el-input>
	        </el-form-item>
	      </el-form>
	      <div slot="footer" class="dialog-footer">
	        <el-button @click="dialogSJVisible = false">取 消</el-button>
	        <el-button type="primary" @click="updateSJ">确 定</el-button>
	      </div>
	    </el-dialog>
    	
    	<el-dialog title="插话" :visible.sync="dialogCHVisible" width="600px" :modal-append-to-body="false">
	      <el-form :rules="rulesCH" :model="dataFormCH" ref="dataFormCH" label-position="right" label-width="120px" style='width: 400px; margin-left:10%;' >
	        <el-form-item label="姓名" >
	          <el-input v-model="dataFormCH.frName" :disabled="true"></el-input>
	        </el-form-item>
	        <el-form-item label="插话" prop="vocId" >
	        	<el-select class="filter-item" v-model="dataFormCH.vocId" placeholder="请选择">
		            <el-option v-for="item in monitorVocList" :key="item.vocId" :label="item.vocInfo" :value="item.vocId"></el-option>
		        </el-select>
	        </el-form-item>
	      </el-form>
	      <div slot="footer" class="dialog-footer">
	        <el-button @click="dialogCHVisible = false">取 消</el-button>
	        <el-button type="primary" @click="updateCH">确 定</el-button>
	      </div>
	    </el-dialog>
    	
    	<el-dialog title="注释" :visible.sync="dialogZSVisible" width="600px" :modal-append-to-body="false">
	      <el-form  :model="dataFormZS" ref="dataFormZS" label-position="right" label-width="120px" style='width: 400px; margin-left:10%;' >
	        <el-form-item label="姓名" >
	          <el-input v-model="dataFormZS.frName" :disabled="true"></el-input>
	        </el-form-item>
	        <el-form-item label="注释" >
	          <el-input type="textarea" :rows="2" v-model="dataFormZS.writeTxt"></el-input>
	        </el-form-item>
	      </el-form>
	      <div slot="footer" class="dialog-footer">
	        <el-button @click="dialogZSVisible = false">取 消</el-button>
	        <el-button type="primary" @click="updateZS">确 定</el-button>
	      </div>
	    </el-dialog>
	    
	</div>
</template>

<script>
import { findPojo, UpdateSJ, GetMonitorVocList, AddMonitorFlag, GetZs, QieduanHj } from '@/api/meetMonitor'
import {findList as GetHjServerList} from '@/api/sysParam'

import moment from 'moment';
import waves from '@/directive/waves' // 水波纹指令
import { Message, MessageBox } from 'element-ui'

export default {
  name: 'criminal',
  directives: {
    waves
  },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: null,
      listQuery: {
        pageNum: 1,
        pageSize: 20
      },
      
      hjServerList: null, // 服务器server
      
      jtState: 1,
      
      /** 修改时间 开始 */ 
      dialogSJVisible: false,
      
      dataForm: {
      	webid: undefined,
      	frName: undefined,
      	timeUp: undefined
      },
      rules: {
        timeUp: [{ required: true, message: '时间不能为空', trigger: 'blur' }]
      },
      /** 修改时间 结束 */ 
      
      /** 插话 开始 */
      
      // 插话
      monitorVocList: [],
      dialogCHVisible: false,
      
      dataFormCH: {
      	frName: undefined,
      	serverName: undefined,
      	lineNo:undefined,
      	vocId: undefined,
      },
      rulesCH: {
        vocId: [{ required: true, message: '请选择一条内容', trigger: 'blur' }]
      },
      /** 插话 结束 */
     
      /** 注释开始 */
      dialogZSVisible: false, 
      dataFormZS: {
      	monitorCallid: undefined,
      	frName: undefined,
      	writeTxt: undefined,
      	
      },
      /** 注释结束 */
     
     //按钮权限   1：有权限， 0：无权限
      buttonRole: { 
      	queryPermission: 1,      //查询
      	jiantingPermission: 0,   //监听
      	qieduanPermission: 0,    //切断
      	chahuaPermission: 0,     //插话
      	//updateTimePermission: 0, //修改时间
      	zhushiPermission: 0      //注释
      },
      
    }
  },
  filters: {
    
  },
  created() {
    this.getList()
  },
  mounted() {
  	this.setButtonRole()
  	
  	this.getMonitorVocList()
  	
  	this.openServerOcx()
  	
  	if(this.timer){
  		this.clearInterval(this.timer)
  	}else{
  		this.timer = setInterval(() =>{
  			this.getList()
  		}, 10000)
  	}
  },
  destroyed() {
  	//this.closeServerOcx()
  	
  	clearInterval(this.timer)
  },
  methods: {
    getList() {
      findPojo(this.listQuery).then((res) => {
      	 this.list = res.pojo.list
      	 this.total = res.pojo.count
      })
    },
    handleSizeChange(val) {
      this.listQuery.pageSize = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.listQuery.pageNum = val
      this.getList()
    },
    
    setButtonRole() { //设置按钮的权限
    	let roles = sessionStorage.getItem("roles")
    	if(roles.includes('admin')){
    		this.buttonRole.jiantingPermission= 1
    		this.buttonRole.qieduanPermission= 1
    		this.buttonRole.chahuaPermission= 1
    		//this.buttonRole.updateTimePermission= 1
    		this.buttonRole.zhushiPermission= 1
    	}else{
    		let buttonRoles = JSON.parse(sessionStorage.getItem("buttonRoles"))
    		let meetMonitorGraph = buttonRoles.meetMonitorGraph
    		if(meetMonitorGraph.length>0){
    			for(let value of meetMonitorGraph){
    				if(value=='jiantingPermission'){
    					this.buttonRole.jiantingPermission= 1
    				}else if(value=='qieduanPermission'){
    					this.buttonRole.qieduanPermission= 1
    				}else if(value=='chahuaPermission'){
    					this.buttonRole.chahuaPermission= 1
    				}
//  				else if(value=='updateTimePermission'){
//  					this.buttonRole.updateTimePermission= 1
//  				}
    				else if(value=='zhushiPermission'){
    					this.buttonRole.zhushiPermission= 1
    				}
    			}
    		}
    	}
    },
    /** 监听 开始 */
    jianting(row){
    	if(row.monitorState=='通话'){
    		document.getElementById(row.jy).ListenTele(row.lineNo);
    		this.jtState = 2
    		
    		Message({
		        message: '当前线路不在通话状态',
			    type: 'error',
			    duration: 5 * 1000
		    })
    	}
    },
    /** 监听 结束 */
   
    /** 停止监听 开始 */
    jiantingStop(row){
    	document.getElementById(row.jy).ListenStop(row.lineNo);
    	this.jtState = 1
    	Message({
	        message: '停止监听',
		    type: 'success',
		    duration: 5 * 1000
	    })
    },
    /** 停止监听 结束 */
    
    /** 切断 开始 */
    qieduan(row){
    	this.$confirm('是否确定切断通话？注意：切断后，需要前往会见登记处重新登记会见！', '提示', {
			type: 'warning'
		}).then(() => {
			let param = {
				hjid:row.hjid
			}
			QieduanHj(param).then(res => {
				this.jtState = 1
				this.getList()
				
				document.getElementById(row.jy).StopTele(row.lineNo);
				
				Message({
			        message: '已成功切断当前通话',
				    type: 'success',
				    duration: 5 * 1000
			    })
			}).catch(error =>{
				Message({
			        message: '系统原因，切断当前通话失败',
				    type: 'error',
				    duration: 5 * 1000
			    });
			})
		})
    },
    /** 切断 结束 */
  
  
    /** 修改时间 开始 */
    
	resetForm() {//重置表单
		this.dataForm.webid = undefined
		this.dataForm.frName = undefined
		this.dataForm.timeUp = undefined
    },
	xiugaiTime(row){ //修改时间
		
		this.resetForm()
		this.dialogSJVisible =true
		this.dataForm.webid = row.webid
		this.dataForm.frName = row.monitorFr
	},
	updateSJ(){ //修改时间
		UpdateSJ(this.dataForm).then(res => {
		    Message({
	          message: res.errMsg,
	          type: 'success',
	          duration: 5 * 1000
	        });
		})
		this.dialogSJVisible =false
	},
	/** 修改时间 结束 */
	
	
	/** 用于插话  开始 */
	getMonitorVocList(){ // 获取插话内容
		GetMonitorVocList({}).then(res => {
			this.monitorVocList = res.list
		})
	},
	//重置表单
	resetFormCH() {
		if(this.$refs['dataFormCH'] !== undefined){
			this.$refs['dataFormCH'].resetFields();
		}
		this.dataFormCH.serverName = undefined
      	this.dataFormCH.lineNo = undefined
    },
	chahua(row){
		if(!row.monitorCallid){
			Message({
	          message: '当前线路未处于通话状态，无法插话',
	          type: 'error',
	          duration: 5 * 1000
	        });
	        return false;
		}
		this.resetFormCH()
		this.dialogCHVisible = true
		this.dataFormCH.frName = row.monitorFr
		this.dataFormCH.serverName = row.jy
      	this.dataFormCH.lineNo = row.lineNo
	},
	updateCH(){
		var serverName = this.dataFormCH.serverName
		console.log(serverName)
		var lineNo = this.dataFormCH.lineNo 
		var vocId = this.dataFormCH.vocId
		
		this.$refs['dataFormCH'].validate((valid) => {
	        if (valid) {
	           document.getElementById(serverName).InsertVoc(lineNo,vocId);
	        }
	    })
		
		this.dialogCHVisible = false
	},
	/** 用于插话  结束 */
	
	/** 注释 开始 */
	//重置表单
	resetFormZS() {
		this.dataFormZS.monitorCallid = undefined
		this.dataFormZS.frName = undefined
		this.dataFormZS.writeTxt = undefined
      	
    },
    getZs(monitorCallid){ //获取注释
    	GetZs({monitorCallid: monitorCallid}).then(res => {
    		this.dataFormZS.writeTxt = res.data.writeTxt
    	})
    },
	zhushi(row){
		this.resetFormZS()
		this.dialogZSVisible = true
		this.dataFormZS.monitorCallid = row.monitorCallid
		this.dataFormZS.frName = row.monitorFr
		
		this.getZs(this.dataFormZS.monitorCallid)
	},
	updateZS(){
		AddMonitorFlag(this.dataFormZS).then(res => {
			Message({
	          message: res.errMsg,
	          type: 'success',
	          duration: 5 * 1000
	        });
		})
		this.dialogZSVisible = false
	},
	/** 注释 结束 */
	
	openServerOcx() { // 获取服务器用于控件连接
		if(navigator.appVersion.indexOf("MSIE") != -1 || (navigator.appVersion.toLowerCase().indexOf("trident") > -1 && navigator.appVersion.indexOf("rv") > -1) ){ // IE浏览器
			GetHjServerList({}).then(res => {
				this.hjServerList = res.list
				for(let x of this.hjServerList){
					document.getElementById(x.serverName).ConnectSvr(x.ip, x.port);//修改
				}
			})
		}
	},
	closeServerOcx(){ //关闭服务器控件连接
		if(this.hjServerList){
			for(let x of this.hjServerList){
					document.getElementById(x.serverName).DisconnectSvr();//修改
				}
		}
	},
		
	dateFormats(val) {
		if(!val){
			return undefined
		}
		return moment(val).format("YYYY-MM-DD HH:mm:ss");
	},
  }
}
</script>

<style>
  .text {
    font-size: 14px;
  }

  .item {
  	margin-left: 20px;
    margin-bottom: 15px;
  }
  
  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }
</style>
