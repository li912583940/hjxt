<template>
  <div class="app-container">
    <div class="filter-container">
    	<el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入服刑人员编号" v-model="listQuery.frNo" clearable>
      </el-input>
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入服刑人员姓名" v-model="listQuery.frName" clearable>
      </el-input>
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入亲属姓名" v-model="listQuery.qsName" clearable>
      </el-input>
      <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleFilter">{{$t('criminal.search')}}</el-button>
      <!--<el-button v-if="buttonRole.addPermission==1" class="filter-item" style="margin-left: 10px;" @click="handleCreate" type="primary" icon="el-icon-circle-plus-outline">{{$t('criminal.add')}}</el-button>-->
      <el-button v-if="buttonRole.exportPermission==1" class="filter-item" type="primary" v-waves icon="el-icon-download" @click="handleDownload" >{{$t('criminal.export')}}</el-button>
    	<el-upload
    		v-if="buttonRole.importPermission==1"
    		ref="upload"
			  class="filter-item"
			  :action="excelPath"
			  :on-success="excelSuccess"
			  :on-error="excelError"
			  multiple>
			  <el-button icon="el-icon-upload2" style="margin-left: 10px;" type="primary">导入</el-button>
			</el-upload>
    </div>

    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row
      style="width: 1971px;">
      <el-table-column type="index" width="50">
      </el-table-column>
      <el-table-column width="110" align="center"  :label="$t('currency.frNo')">
        <template slot-scope="scope">
          <span>{{scope.row.frNo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160" align="center" :label="$t('currency.frName')">
        <template slot-scope="scope">
          <span>{{scope.row.frName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" label="证件类别">
        <template slot-scope="scope">
          <span v-if="scope.row.qsZjlb==1">身份证</span>
          <span v-if="scope.row.qsZjlb==2">警官证</span>
          <span v-if="scope.row.qsZjlb==3">工作证</span>
          <span v-if="scope.row.qsZjlb==4">港澳通行证</span>
	        <span v-if="scope.row.qsZjlb==5">台湾通行证</span>
	        <span v-if="scope.row.qsZjlb==9">其他</span>
        </template>
      </el-table-column>
      <el-table-column width="180" align="center" label="证件号码">
        <template slot-scope="scope">
          <span>{{scope.row.qsSfz}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" label="亲属姓名">
        <template slot-scope="scope">
          <span>{{scope.row.qsName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" label="关系">
        <template slot-scope="scope">
          <span>{{scope.row.gx}}</span>
        </template>
      </el-table-column>
      <el-table-column width="90" align="center" label="性别">
        <template slot-scope="scope">
          <span>{{scope.row.xb}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="电话号码">
        <template slot-scope="scope">
          <span>{{scope.row.tele}}</span>
        </template>
      </el-table-column>
      <el-table-column width="300" align="center" label="地址">
        <template slot-scope="scope">
          <span>{{scope.row.dz}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160" align="center" label="禁止时间">
        <template slot-scope="scope">
          <span>{{scope.row.hjStopTime | dateFormat}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" label="审批状态">
        <template slot-scope="scope">
          <span v-if="scope.row.spState==1">已通过</span>
          <span v-if="scope.row.spState==0" style="color: red;">未通过</span>
        </template>
      </el-table-column>
      <el-table-column width="180" align="center" label="备注">
        <template slot-scope="scope">
          <span>{{scope.row.bz}}</span>
        </template>
      </el-table-column>
      <el-table-column v-if="buttonRole.editPermission==1 || buttonRole.deletePermission==1" align="center" :label="$t('criminal.actions')" width="200" fixed="right">
        <template slot-scope="scope">
          <el-button v-if="buttonRole.editPermission==1" type="primary" size="mini" icon="el-icon-edit" @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button v-if="buttonRole.deletePermission==1" size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

		<!-- 分页 -->
    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="listQuery.pageNum" :page-sizes="[10,20,30, 50]" :page-size="listQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

    <!-- 新增或编辑 -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" @close="dialogClose" width="600px" :modal-append-to-body="false">
      <el-form :rules="rules" :model="dataForm" ref="dataForm" label-position="right" label-width="120px" style='width: 400px; margin-left:10%;' >
        <el-form-item :label="$t('currency.frName')" prop="frName">
          <el-input v-model="dataForm.frName" :disabled="true"></el-input>
        </el-form-item>
        <!--<el-form-item label="近照" >
		      <!--<span v-if="ie==1">
		        <video id="video" autoplay width="150" height="110" controls>
						</video>
						<canvas id="canvas" width="150" height="110"></canvas>
		      </span>
		      <span v-if="ie==0" id="zp" style="width:160,height:176">
		      </span>-->
					<!--<div>
					  <button id="capture" @click="paizhao">拍照</button>
					</div>
		    </el-form-item>-->
        <el-form-item label="证件类别" prop="qsZjlb">
          <el-select class="filter-item" v-model="dataForm.qsZjlb" placeholder="请选择">
            <el-option v-for="item in qsZjlbs" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="证件号码" prop="qsSfz">
          <el-input v-model="dataForm.qsSfz"></el-input>
        </el-form-item>
        <el-form-item label="亲属姓名" prop="qsName">
          <el-input v-model="dataForm.qsName"></el-input>
        </el-form-item>
        <el-form-item label="关系" prop="gx">
          <el-select class="filter-item" v-model="dataForm.gx" placeholder="请选择">
            <el-option v-for="item in gxs" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="IC卡号" prop="qsCard">
          <el-input v-model="dataForm.qsCard"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="dz">
          <el-input v-model="dataForm.dz"></el-input>
        </el-form-item>
        <el-form-item label="性别" >
        	<el-radio-group v-model="dataForm.xb">
        		<el-radio :label="'男'">男</el-radio>
				    <el-radio :label="'女'">女</el-radio>
				  </el-radio-group>
        </el-form-item>
        <el-form-item label="电话号码" prop="tele">
          <el-input v-model="dataForm.tele"></el-input>
        </el-form-item>
        <el-form-item label="审批状态">
          <el-input v-model="dataForm.spState" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="禁止会见时间" prop="hjStopTime">
          <el-date-picker v-model="dataForm.hjStopTime" type="datetime" placeholder="请选取禁止会见时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="bz">
          <el-input v-model="dataForm.bz"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">确 定</el-button>
        <el-button v-else type="primary" @click="updateData">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { findPojo, findOne, RequestAdd, RequestEdit, RequestDelete, exportExcel } from '@/api/relatives'
import { findList as findGxList} from '@/api/gxManage'

import moment from 'moment'
import waves from '@/directive/waves' // 水波纹指令
import { Message, MessageBox } from 'element-ui'

export default {
  name: 'relatives',
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
        pageSize: 10,
        frNo: undefined,
        frName: undefined,
        qsName: undefined
      },
      
      //按钮权限   1：有权限， 0：无权限
      buttonRole: { 
      	queryPermission: 1, 
      	addPermission: 0,
      	editPermission: 0,
      	deletePermission: 0,
      	exportPermission: 0,
      	importPermission: 0,
      },
      excelPath: process.env.BASE_API+"/jlQs/importExcel", //罪犯excel导入地址
      
      // 新增或编辑弹窗
      dataForm: { 
        webid: undefined,
        frNo: '',
        frName: undefined,
        qsZjlb: 1,
        qsSfz: undefined,
        qsName: undefined,
        gx: undefined,
        qsCard: undefined,
        dz: undefined,
        xb: undefined,
        tele: undefined,
        spState: undefined,
        bz: undefined,
        hjStopTime: undefined,
        jzBase64: undefined,
        zpBase64: undefined,
        jzUrl: undefined,
      },
      gxs: [ // 关系
      	
      ],
      qsZjlbs: [
        {
        	id: 1,
        	name: '身份证'
        },
      	{
      		id: 2,
      		name: '警官证'
      	},
      	{
      		id: 3,
      		name: '工作证'
      	},
      	{
      		id: 4,
      		name: '港澳通行证'
      	},
      	{
      		id: 5,
      		name: '台湾通行证'
      	},
      	{
      		id: 9,
      		name: '其他'
      	}
      ],
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编 辑',
        create: '新 增'
      },
      rules: {
        qsName: [{ required: true, message: '亲属姓名不能为空', trigger: 'blur' }],
        gx: [{ required: true, message: '亲属关系必选', trigger: 'blur' }]
      },
      
      ie: 0,
      
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
    //this.getList()
    this.noSearch()
    
  },
  mounted() {
  	//this.isIe()
    this.setButtonRole()
    this.getGxList()
    //this.openVideo()
    
    //this.openGaoPaiYi()
  },
  methods: {
  	noSearch() {
  		this.total=0
  		this.listLoading = false
  	},
    getList() {
      this.listLoading = true
      findPojo(this.listQuery).then((res) => {
      	 this.list = res.pojo.list
      	 this.total = res.pojo.count
      	 this.listLoading = false
      }).catch(error => {
          this.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.pageNum = 1
      this.getList()
    },
    handleSizeChange(val) {
      this.listQuery.pageSize = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.listQuery.pageNum = val
      this.getList()
    },
    getGxList() { // 获取关系
    	if(this.gxs.length === 0) {
    		findGxList({}).then((res) => {
	    		let list = res.list
	    		for(let x of list){
				  let value = {}
				  value.id = x.qsGx
				  value.name = x.qsGx
				  this.gxs.push(value)
				}
	    	})
    	}
    },
    excelSuccess() {
    	this.$refs.upload.clearFiles()
    	Message({
        message: '文件已上传至服务器，请等待十秒钟左右查询信息',
	      type: 'success',
	      duration: 5 * 1000
      });
      
    },
    excelError() {
    	this.$refs.upload.clearFiles()
    	Message({
        message: '文件上传失败，请检查文件是否为符合要求的EXCEL表格！',
	      type: 'error',
	      duration: 5 * 1000
      });
    },
    
    setButtonRole() { //设置按钮的权限
    	let roles = sessionStorage.getItem("roles")
    	if(roles.includes('admin')){
    		this.buttonRole.addPermission= 1
    		this.buttonRole.editPermission= 1
    		this.buttonRole.deletePermission= 1
    		this.buttonRole.exportPermission= 1
    		this.buttonRole.importPermission= 1
    	}else{
    		let buttonRoles = JSON.parse(sessionStorage.getItem("buttonRoles"))
    		let relatives = buttonRoles.relatives
    		if(relatives.length>0){
    			for(let value of relatives){
    				if(value=='addPermission'){
    					this.buttonRole.addPermission= 1
    				}else if(value=='editPermission'){
    					this.buttonRole.editPermission= 1
    				}else if(value=='deletePermission'){
    					this.buttonRole.deletePermission= 1
    				}else if(value=='exportPermission'){
    					this.buttonRole.exportPermission= 1
    				}else if(value=='importPermission'){
    					this.buttonRole.importPermission= 1
    				}
    			}
    		}
    	}
    },
    openGaoPaiYi() { // 高拍仪
    	var EloamGlobal = document.getElementById("EloamGlobal_ID")
    	if(DeviceAssist)
			{

				VideoAssist = DeviceAssist.CreateVideo(0, 2);
				if (VideoAssist)
				{
					ViewAssist.SelectVideo(VideoAssist);
					ViewAssist.SetText("打开视频中，请等待...", 0);							
				}
			}
    	//辅摄像头
    	var DeviceAssist
    	var deviceType = EloamGlobal.GetEloamType(1, idx)
    	if(2 == deviceType || 3 == deviceType){
    		if(null == DeviceAssist)
				{
					DeviceAssist = EloamGlobal.CreateDevice(1, idx);
					if (DeviceAssist)
					{
						var label =  document.getElementById('lab2');
						label.innerHTML = DeviceAssist.GetFriendlyName();	
						
						var mode = document.getElementById('selMode2');
						var subType = DeviceAssist.GetSubtype();
		
						changesubType2();
						
						if(0 != hasLoadSuccess)
						{
							OpenVideoAssist();
						}
					}
				}
    	}
    	VideoAssist = DeviceAssist.CreateVideo(nResolution2, subtype)
    },
    //关闭视频
//  CloseVideoAssist(){ 
//  	if (VideoAssist)
//			{
//				VideoAssist.Destroy();		
//				ViewAssist.SetText("", 0);				
//			}
//  },
    isIe() {
    	if(navigator.appVersion.indexOf("MSIE") != -1 || (navigator.appVersion.toLowerCase().indexOf("trident") > -1 && navigator.appVersion.indexOf("rv") > -1)){
    		this.ie=0
    	}else{
    		this.ie=1
    	}
    },
    openVideo(){
			if(navigator.appVersion.indexOf("MSIE") != -1 || (navigator.appVersion.toLowerCase().indexOf("trident") > -1 && navigator.appVersion.indexOf("rv") > -1) ){ // IE浏览器
			  console.log(11111)
			  console.log(document.getElementById('zp'))
			  document.getElementById('zp').innerHTML='<object id=\"camera\" classid=\"clsid:792FD9B8-5917-45D2-889D-C49FD174D4E0\" codebase=\"../../ocx/capProj1.ocx#version=1,0,0,0\" width=160 height=176 hspace=0 vspace=0></object>';
	      document.getElementById("camera").start(160,176); // 打开flash拍照控件
			}else{ // 非IE浏览器
				let video = document.getElementById('video');
				if (navigator.mediaDevices.getUserMedia || navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia) {
			      if (navigator.mediaDevices.getUserMedia) {
			        //最新的标准API
			        //调用用户媒体设备, 访问摄像头
				      navigator.mediaDevices.getUserMedia({video : {width: 150, height: 110}}).then(function(stream){
					      //兼容webkit核心浏览器
					      var URL = window.URL || window.webkitURL;
					      //将视频流设置为video元素的源
					      video.src = URL.createObjectURL(stream);
					      //video.srcObject = stream;
					      video.play();
				    	 }).catch(function(error){
				      	console.log(error);
				    	 });
			      } else if (navigator.webkitGetUserMedia) {
			        //webkit核心浏览器
			        navigator.webkitGetUserMedia({video : {width: 150, height: 110}}, 
			        	function(stream){
			        		//兼容webkit核心浏览器
						      var URL = window.URL || window.webkitURL;
						      //将视频流设置为video元素的源
						      //video.src = URL.createObjectURL(stream);
						      video.srcObject = stream;
						      video.play();
			          }, 
			          function(error){
			          	console.log(error);
			          })
			      } else if (navigator.mozGetUserMedia) {
			        //firfox浏览器
			        navigator.mozGetUserMedia({video : {width: 150, height: 110}}, 
			        function(stream){
			        		//兼容webkit核心浏览器
						      var URL = window.URL || window.webkitURL;
						      //将视频流设置为video元素的源
						      //video.src = URL.createObjectURL(stream);
						      video.srcObject = stream;
						      video.play();
			          }, 
			          function(error){
			          	console.log(error);
			          });
			      } else if (navigator.getUserMedia) {
			        //旧版API
			        navigator.getUserMedia({video : {width: 150, height: 110}}, 
			        function(stream){
			        		//兼容webkit核心浏览器
						      var URL = window.URL || window.webkitURL;
						      //将视频流设置为video元素的源
						      //video.src = URL.createObjectURL(stream);
						      video.srcObject = stream;
						      video.play();
			          }, 
			          function(error){
			          	console.log(error);
			          });
			      }
			    } else {
			      alert('不支持访问用户媒体');
			    }
			}
	    

    },
    paizhao(){
    	if(navigator.appVersion.indexOf("MSIE") != -1 || (navigator.appVersion.toLowerCase().indexOf("trident") > -1 && navigator.appVersion.indexOf("rv") > -1)){ // IE浏览器
    		document.getElementById("camera").savefile("D:\\temp.jpg",150,176);
			  this.dataForm.jzBase64=document.getElementById("camera").jpegbase64;
			//document.getElementById("jz").value = document.getElementById("MyFlexApps").paserbytes();
			//document.getElementById("zp").innerHTML="<img src=\"D:\\\\temp.jpg\"/>";
    	}else{
    		let video = document.getElementById('video');
		    let canvas = document.getElementById('canvas');
		    let context = canvas.getContext('2d');
		    context.drawImage(video, 0, 0, 150, 110); 
		      
				//从画布上获取照片数据
				var imgData = canvas.toDataURL("image/png");
				  
				//将图片转换为Base64
				//var BASE64= imgData.substr(22);
				this.dataForm.jzBase64= imgData.substr(22);
    	}
      
    },
		dialogClose(){
			document.getElementById('zp').innerHTML=''
		},
    
    //重置表单
		resetForm(formName) {
			if(this.$refs[formName] !== undefined){
				this.$refs[formName].resetFields();
			}
			this.dataForm.webid = undefined
	  },
    handleCreate() {
    	
//    this.dialogStatus = 'create'
//    this.resetForm('dataForm')
//    this.dialogFormVisible = true
		  this.$router.push({ path: '/addCriQs' })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
        	if(this.dataForm.infoRjsj){
        		this.dataForm.infoRjsj = this.dateFormats(this.dataForm.infoRjsj);
        	}
        	if(this.dataForm.infoCsrq){
        		this.dataForm.infoCsrq = this.dateFormats(this.dataForm.infoCsrq);
        	}
          RequestAdd(this.dataForm).then(() => {
            this.dialogFormVisible = false
            this.getList()
          }).catch(error => {
		        this.dialogFormVisible = false
		      })
        }
      })
    },
    handleUpdate(row) {
    	let param = {
    		id: row.webid
    	}
    	findOne(param).then((res) =>{
    		this.dataForm.webid = res.data.webid
        this.dataForm.frName =  res.data.frName
        this.dataForm.frNo = res.data.frNo
        this.dataForm.qsZjlb = res.data.qsZjlb
        this.dataForm.qsSfz = res.data.qsSfz
        this.dataForm.qsName = res.data.qsName
        this.dataForm.gx = res.data.gx
        this.dataForm.qsCard = res.data.qsCard
        this.dataForm.dz = res.data.dz
        this.dataForm.xb = res.data.xb
        this.dataForm.tele = res.data.tele
        this.dataForm.spState = res.data.spState
        this.dataForm.bz = res.data.bz
    	})
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          RequestEdit(this.dataForm).then(() => {
            this.dialogFormVisible = false
            this.getList()
          }).catch(error => {
		        this.dialogFormVisible = false
		      })
        }
      })
    },
    //删除
		handleDelete(row) {
			this.$confirm('确认删除该记录吗?', '提示', {
				type: 'warning'
			}).then(() => {
				this.listLoading = true;
				let param = {
	    		id: row.webid
	    	}
				RequestDelete(param).then(() => {
	    		this.getList()
	    	}).catch(error => {
	      })
			})
		},
    handleDownload() {
			exportExcel(this.listQuery).then(res => {
	      var blob = new Blob([res], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8' })
	     	if (window.navigator && window.navigator.msSaveOrOpenBlob) { // IE浏览器
        	window.navigator.msSaveOrOpenBlob(blob, '亲属信息.xls');
    		}else{ //非IE浏览器
	     		var downloadElement = document.createElement('a')
		     	var href = window.URL.createObjectURL(blob)
		     	downloadElement.href = href
		     	downloadElement.download = '亲属信息.xls'
		     	document.body.appendChild(downloadElement)
		     	downloadElement.click()
	     		document.body.removeChild(downloadElement) // 下载完成移除元素
		     	window.URL.revokeObjectURL(href) // 释放掉blob对象
	     	}
			}).catch(error => {
         console.log(error)
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
