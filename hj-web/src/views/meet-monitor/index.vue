<template>
  <div class="app-container">
    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间"  border fit highlight-current-row
      style="width: 1901px">
      <el-table-column width="100" align="center" label="座位号">
        <template slot-scope="scope">
          <span>{{scope.row.zw}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" label="状态">
        <template slot-scope="scope">
          <span v-if="scope.row.monitorState =='通话'" style="color: red;">通话</span>
          <span v-if="scope.row.monitorState =='空闲'">空闲</span>
          <span v-if="scope.row.monitorState =='应答'" style="color:#409EFF;">应答</span>
        </template>
      </el-table-column>
      <el-table-column width="120" align="center" :label="$t('currency.jqName')">
        <template slot-scope="scope">
          <span>{{scope.row.monitorJq}}</span>
        </template>
      </el-table-column>
      <el-table-column width="120" align="center" :label="$t('currency.frNo')">
        <template slot-scope="scope">
          <span>{{scope.row.frNo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="120" align="center" :label="$t('currency.frName')">
        <template slot-scope="scope">
          <span>{{scope.row.monitorFr}}</span>
        </template>
      </el-table-column>
      <el-table-column width="400" align="center" label="亲属信息">
        <template slot-scope="scope">
          <span>{{scope.row.qsInfos}}</span>
        </template>
      </el-table-column>
      <el-table-column width="300" align="center" label="罪名">
        <template slot-scope="scope">
          <span>{{scope.row.infoZm}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" label="剩余时间">
        <template slot-scope="scope">
          <span>{{scope.row.monitorTime}}</span>
        </template>
      </el-table-column>
      <el-table-column width="120" align="center" label="监听警察">
        <template slot-scope="scope">
          <span>{{scope.row.yjName}}</span>
        </template>
      </el-table-column>
      <el-table-column v-if="buttonRole.jiantingPermission==1 || buttonRole.qieduanPermission==1 || buttonRole.chahuaPermission==1 || buttonRole.zhushiPermission==1" align="center" :label="$t('criminal.actions')" width="420" fixed="right">
        <template slot-scope="scope">
          <el-button v-if="jtState!=scope.row.lineNo && buttonRole.jiantingPermission==1 && scope.row.videochan1Server!=null" type="primary" size="mini" icon="el-icon-service" @click="jianting(scope.row)">监听音视频</el-button>
           <el-button v-if="jtState!=scope.row.lineNo && buttonRole.jiantingPermission==1 && scope.row.lineType!=1" type="primary" size="mini" icon="el-icon-service" @click="jiantingAudio(scope.row)">监听音频</el-button>
          <el-button v-if="jtState==scope.row.lineNo && buttonRole.jiantingPermission==1" size="mini" type="info" icon="el-icon-phone" @click="jiantingStop(scope.row)">停止监听</el-button>
          <el-button v-if="jtState==scope.row.lineNo && buttonRole.qieduanPermission==1" size="mini" type="danger" icon="el-icon-phone" @click="qieduan(scope.row)">切断</el-button>
          <el-button v-if="jtState==scope.row.lineNo && buttonRole.chahuaPermission==1" type="primary" size="mini" icon="el-icon-phone-outline" @click="chahua(scope.row)">插话</el-button>
          <el-button v-if="jtState==scope.row.lineNo && buttonRole.zhushiPermission==1" size="mini" type="info" icon="el-icon-document" @click="zhushi(scope.row)">注释</el-button>
        </template>
      </el-table-column>
    </el-table>

		<!-- 分页 -->
    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="listQuery.pageNum" :page-sizes="[10,20,30, 50]" :page-size="listQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

		<el-dialog title="修改时间" :visible.sync="dialogSJVisible" width="600px" :modal-append-to-body="false">
      <el-form :rules="rules" :model="dataForm" ref="dataForm" label-position="right" label-width="120px" style='width: 400px;margin-left: 10%;' >
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
      <el-form :rules="rulesCH" :model="dataFormCH" ref="dataFormCH" label-position="right" label-width="120px" style='width: 400px;margin-left: 10%;' >
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
      <el-form :model="dataFormZS" ref="dataFormZS" label-position="right" label-width="120px" style='width: 400px;margin-left: 10%;' >
        <el-form-item label="姓名" >
          <el-input v-model="dataFormZS.frName" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="注释" >
          <el-input type="textarea" :rows="8" v-model="dataFormZS.writeTxt"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogZSVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateZS">确 定</el-button>
      </div>
    </el-dialog>
    
    <div id="info2"></div>
    
  </div>
</template>

<script>
	
import { findPojo, UpdateSJ, UpdateYJ, GetMonitorVocList, AddMonitorFlag, GetZs, QieduanHj, RequestCH } from '@/api/meetMonitor'
import {findList as GetHjServerList} from '@/api/sysParam'

import moment from 'moment';
import waves from '@/directive/waves' // 水波纹指令
import { Message, MessageBox } from 'element-ui'
import { getToken } from '@/utils/auth'

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
      listLoading: true,
      listQuery: {
        pageNum: 1,
        pageSize: 20
      },
      hjServerList: null, // 服务器server
      
      jtState: null, //监听状态
      
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
      	webid: undefined,
      	frName: undefined,
      	serverName: undefined,
      	lineNo:undefined,
      	vocId: undefined,
      	monitorCallid: undefined,
      	hjid: undefined
      },
      rulesCH: {
        vocId: [{ required: true, message: '请选择一条内容', trigger: 'blur' }]
      },
      /** 插话 结束 */
     
      /** 注释开始 */
      dialogZSVisible: false, 
      dataFormZS: {
      	hjid: undefined,
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
    	this.listLoading = true
      findPojo(this.listQuery).then((res) => {
      	 this.list = res.pojo.list
      	 this.total = res.pojo.count
      })
      this.listLoading = false
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
    		this.buttonRole.zhushiPermission= 1
    	}else{
    		let buttonRoles = JSON.parse(sessionStorage.getItem("buttonRoles"))
    		let meetMonitor = buttonRoles.meetMonitor
    		if(meetMonitor.length>0){
    			for(let value of meetMonitor){
    				if(value=='jiantingPermission'){
    					this.buttonRole.jiantingPermission= 1
    				}else if(value=='qieduanPermission'){
    					this.buttonRole.qieduanPermission= 1
    				}else if(value=='chahuaPermission'){
    					this.buttonRole.chahuaPermission= 1
    				}else if(value=='zhushiPermission'){
    					this.buttonRole.zhushiPermission= 1
    				}
    			}
    		}
    	}
    },
    /** 监听 开始 */
    jianting(row){ //监听音视频
    	if(row.lineType==0){
    		if(this.jtState!=null){
	    		document.getElementById(row.jy).ListenStop(this.jtState);
	    	}
    	}
    	if(row.monitorState=='通话'){
    		let param ={
    			webid:row.webid,
    			monitorCallid: row.monitorCallid,
    			hjid: row.hjid,
    			state:1
    		}
    		UpdateYJ(param).then(res =>{
    			this.getList()
    		})
    		
    		if(row.lineType==0){
    			document.getElementById(row.jy).ListenTele(row.lineNo);
    		}
    		this.jtState = row.lineNo
    		
    		var httpPath = process.env.BASE_API
    		var tokenValue = getToken()
				window.open("/static/html/yushiPingtai/WebDemo(实况回放).html?id="+row.webid+"&httpPath="+httpPath+"&token="+tokenValue,
				"","width=900,height=480,left=700,top=300,dependent=yes,scroll:no,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,directories=no,status=no")
				
    	}else{
    		Message({
	        message: '当前线路不在通话状态',
		      type: 'error',
		      duration: 5 * 1000
	      });
    	}
    },
    jiantingAudio(row){ //监听音频
    	if(this.jtState!=null){
    		document.getElementById(row.jy).ListenStop(this.jtState);
    	}
    	if(row.monitorState=='通话'){
    		let param ={
    			webid:row.webid,
    			monitorCallid: row.monitorCallid,
    			hjid: row.hjid,
    			state:1
    		}
    		UpdateYJ(param).then(res =>{
    			this.getList()
    		})
    		
    		document.getElementById(row.jy).ListenTele(row.lineNo);
    		this.jtState = row.lineNo
    	}else{
    		Message({
	        message: '当前线路不在通话状态',
		      type: 'error',
		      duration: 5 * 1000
	      });
    	}
    },
    /** 监听 结束 */
   
    /** 停止监听 开始 */
    jiantingStop(row){
    	let param ={
    		webid:row.webid,
    		monitorCallid: row.monitorCallid,
    		hjid: row.hjid,
    		state:2
    	}
    	UpdateYJ(param).then(res =>{
				this.getList()
			})
    	if(row.lineType==0){
    		document.getElementById(row.jy).ListenStop(row.lineNo);
    	}
    	this.jtState = null
    	
    	Message({
        message: '停止监听',
	      type: 'success',
	      duration: 5 * 1000
      });
    },
    /** 停止监听 结束 */
    
    /** 切断 开始 */
    qieduan(row){
    	this.$confirm('是否确定切断通话？注意：切断后，需要前往会见登记处重新登记会见！', '提示', {
				type: 'warning'
			}).then(() => {
				let param = {
					hjid:row.hjid,
					lineId:row.webid
				}
				QieduanHj(param).then(res => {
					let param ={
		    		webid:row.webid,
		    		monitorCallid: row.monitorCallid,
		    		hjid: row.hjid,
		    		state:3
		    	}
					UpdateYJ(param).then(res =>{
	    			this.getList()
	    		})
					if(row.lineType==0){
						document.getElementById(row.jy).StopTele(row.lineNo);
					}
					
					this.jtState = null
					
					this.getList()
					
					Message({
		        message: '已成功切断当前通话',
			      type: 'success',
			      duration: 5 * 1000
		      });
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
	    this.dataFormCH.monitorCallid = undefined
	    this.dataFormCH.hjid = undefined
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
			this.dataFormCH.webid = row.webid
			this.dataFormCH.frName = row.monitorFr
			this.dataFormCH.serverName = row.jy
	    this.dataFormCH.lineNo = row.lineNo
	    this.dataFormCH.monitorCallid = row.monitorCallid
	    this.dataFormCH.hjid = row.hjid
		},
		updateCH(){
			var serverName = this.dataFormCH.serverName
			var lineNo = this.dataFormCH.lineNo 
			var vocId = this.dataFormCH.vocId
			
			this.$refs['dataFormCH'].validate((valid) => {
        if (valid) {
          document.getElementById(serverName).InsertVoc(lineNo,vocId);
          
          RequestCH(this.dataFormCH).then(res =>{
          	
          })
        }
	    })
			
			this.dialogCHVisible = false
		},
		/** 用于插话  结束 */
		
		/** 注释 开始 */
		//重置表单
		resetFormZS() {
			this.dataFormZS.hjid = undefined
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
			this.dataFormZS.hjid = row.hjid
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
		dateFormats: function (val) {
			if(!val){
				return undefined
			}
			return moment(val).format("YYYY-MM-DD HH:mm:ss");
		},
  }
}
</script>
