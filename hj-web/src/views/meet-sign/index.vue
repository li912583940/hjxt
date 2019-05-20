<template>
  <div class="app-container">

    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row
      style="width: 2171px">
      <el-table-column v-if="buttonRole.distributionPermission==1 || buttonRole.cancelDistributionPermission==1 || buttonRole.artificialPermission==1" align="center" :label="$t('criminal.actions')" width="300" fixed="left" >
        <template slot-scope="scope">
          <el-button v-if="buttonRole.distributionPermission==1" type="primary" size="mini" @click="fpZw(scope.row)">自动分配</el-button>
          <el-button v-if="buttonRole.cancelDistributionPermission==1" type="primary" size="mini" @click="qxFpZw(scope.row)">取消分配</el-button>
          <el-button v-if="buttonRole.artificialPermission==1" type="primary" size="mini" @click="getSurplusZw(scope.row)">人工分配</el-button>
        </template>
      </el-table-column>
      
      <el-table-column width="110" align="center" :label="$t('currency.frNo')">
        <template slot-scope="scope">
          <span>{{scope.row.frNo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160" align="center" :label="$t('currency.frName')">
        <template slot-scope="scope">
          <span>{{scope.row.frName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="90" align="center" label="分管等级">
        <template slot-scope="scope">
          <span>{{scope.row.jbName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" label="重点犯">
        <template slot-scope="scope">
          <span v-if="scope.row.stateZdzf==0">否</span>
          <span v-if="scope.row.stateZdzf==1" style="color: red;">是/{{scope.row.infoZdzf}}</span>
        </template>
      </el-table-column>
      <el-table-column width="90" align="center" label="座位">
        <template slot-scope="scope">
          <span v-if="scope.row.fpFlag ==0">未分配</span>
          <span else>{{scope.row.zw}}</span>
        </template>
      </el-table-column>
      <el-table-column width="110" align="center" label="会见类型">
        <template slot-scope="scope">
          <span v-if="scope.row.hjType==1">亲属会见</span>
          <span v-else-if="scope.row.hjType==2" style="color: red;">监护人会见</span>
          <span v-else-if="scope.row.hjType==3" style="color: red;">律师会见</span>
          <span v-else-if="scope.row.hjType==4" style="color: red;">使领馆探视</span>
          <span v-else-if="scope.row.hjType==5" style="color: red;">提审会见</span>
          <span v-else-if="scope.row.hjType==6" style="color: red;">公务会见</span>
          <span v-else-if="scope.row.hjType==9" style="color: red;">特批会见</span>
          <span v-else-if="scope.row.hjType==99" style="color: red;">其他会见</span>
        </template>
      </el-table-column>
      <el-table-column width="110" align="center" label="会见方式">
        <template slot-scope="scope">
          <span v-if="scope.row.hjMode==1">隔离会见</span>
          <span v-else-if="scope.row.hjMode==2" style="color: red;">非隔离会见</span>
          <span v-else-if="scope.row.hjMode==3" style="color: red;">远程视频会见</span>
          <span v-else-if="scope.row.hjMode==9" style="color: red;">其他方式</span>
        </template>
      </el-table-column>
      <el-table-column width="90" align="center" :label="$t('currency.jqName')">
        <template slot-scope="scope">
          <span>{{scope.row.jqName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="会见说明">
        <template slot-scope="scope">
          <span>{{scope.row.hjInfo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="400" align="center" label="亲属">
        <template slot-scope="scope">
          <span>{{scope.row.qsInfo1}}</span>
        </template>
      </el-table-column>
      <el-table-column width="90" align="center" label="会见时长">
        <template slot-scope="scope">
          <span>{{scope.row.hjTime}}分钟</span>
        </template>
      </el-table-column>
      <el-table-column width="160" align="center" label="登记时间">
        <template slot-scope="scope">
          <span>{{scope.row.djTime | dateFormat}}</span>
        </template>
      </el-table-column>
      <el-table-column v-if="buttonRole.bofangTTSPermission==1" width="160" align="center" label="语音播报" fixed="right">
        <template slot-scope="scope">
          <el-button v-if="buttonRole.bofangTTSPermission==1" type="primary" size="mini" @click="hechengTTS(scope.row)">合成</el-button>
          <el-button v-if="buttonRole.bofangTTSPermission==1" type="primary" size="mini" @click="bofangTTS(scope.row)">播放</el-button>
        </template>
      </el-table-column>
    </el-table>

		<!-- 分页 -->
    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="listQuery.pageNum" :page-sizes="[10,20,30, 50]" :page-size="listQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

    <!-- 人工分配座位 -->
		<el-dialog title="人工分配座位" :visible.sync="dialogRgFpVisible" width="600px" :modal-append-to-body="false">
			<el-form :rules="rules" :model="rgFpDataForm" ref="rgFpDataForm" label-position="right" label-width="120px" style='width: 400px;margin-left: 10%;' >
        <el-form-item label="服刑人员姓名" prop="frName">
          <el-input v-model="rgFpDataForm.frName" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="座位" prop="webid">
          <el-select class="filter-item" v-model="rgFpDataForm.webid" placeholder="请选择座位">
            <el-option v-for="item in zws" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
		  <div slot="footer" class="dialog-footer">
        <el-button @click="dialogRgFpVisible = false">取 消</el-button>
        <el-button type="primary" @click="rgFpZw">确 定</el-button>
      </div>
		</el-dialog>

		<object name="audioTts" id="audioTts" width="0" height="0" classid="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6">
				    <param name="AutoStart" value="1" />
				    <!--是否name动播放-->
				    <param name="Balance" value="0" />
				    <!--调整左右声道平衡,同上面旧播放器代码-->
				    <param name="enabled" value="1" />
				    <!--播放器是否可人为控制-->
				    <param name="EnableContextMenu" value="1" />
				    <!--是否启用上下文菜单-->
				    <param name="url" value="" />
				    <!--播放的文件地址-->
				    <param name="PlayCount" value="1" />
				    <!--播放次数控制,为整数-->
				    <param name="rate" value="1" />
				    <!--播放速率控制,1为正常,允许小数,1.0-2.0-->
				    <param name="currentPosition" value="0" />
				    <!--控件设置:当前位置-->
				    <param name="currentMarker" value="0" />
				    <!--控件设置:当前标记-->
				    <param name="defaultFrame" value="" />
				    <!--显示默认框架-->
				    <param name="invokeURLs" value="0" />
				    <!--脚本命令设置:是否调用URL-->
				    <param name="baseURL" value="" />
				    <!--脚本命令设置:被调用的URL-->
				    <param name="stretchToFit" value="0" />
				    <!--是否按比例伸展-->
				    <param name="volume" value="50" />
				    <!--默认声音大小0%-100%,50则为50%-->
				    <param name="mute" value="0" />
				    <!--是否静音-->
				    <param name="uiMode" value="Full" />
				    <!--播放器显示模式:Full显示全部;mini最简化;None不显示播放控制,只显示视频窗口;invisible全部不显示-->
				    <param name="windowlessVideo" value="1" />
				    <!--如果是0可以允许全屏,否则只能在窗口中查看-->
				    <param name="fullScreen" value="0" />
				    <!--开始播放是否自动全屏-->
				    <param name="enableErrorDialogs" value="0" />
				    <!--是否启用错误提示报告-->
				    <param name="SAMIStyle" value />
				    <!--SAMI样式-->
				    <param name="SAMILang" value />
				    <!--SAMI语言-->
				    <param name="SAMIFilename" value />
				    <!--字幕ID-->
     		</object>
     		
  </div>
</template>

<script>
import { findPojo, FpZw, QxFpZw, GetSurplusZw, RgFpZw, GrantCall, CancelGrantCall, HechengTTS, BofangTTS } from '@/api/meetSign'

import moment from 'moment';
import waves from '@/directive/waves' // 水波纹指令
import { Message, MessageBox } from 'element-ui'

export default {
  name: 'meetSign',
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
      
      //按钮权限   1：有权限， 0：无权限
      buttonRole: { 
      	distributionPermission: 0, 
      	cancelDistributionPermission: 0,
      	artificialPermission: 0,
      	bofangTTSPermission : 0,
      },
      
      rgFpDataForm: {
      	hjId: undefined,
      	frName: undefined,
      	webid: undefined
      },
      
      zws:[],
      dialogRgFpVisible: false,
      
      rules: {
        webid: [{ required: true, message: '请选择座位', trigger: 'blur' }]
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
	  },
	  hjTimeFilter(d){ //会见时长
	  	if(d == undefined){
	  		return "";
	  	}
	  	return d
	  }
  },
  created() {
    this.getList()
  },
  mounted() {
    this.setButtonRole()
    
    if(this.timer){
  		this.clearInterval(this.timer)
  	}else{
  		this.timer = setInterval(() =>{
  			this.getList()
  		}, 30000)
  	}
  },
  destroyed() {
  	clearInterval(this.timer)
  },
  methods: {
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
    		this.buttonRole.distributionPermission= 1
    		this.buttonRole.cancelDistributionPermission= 1
    		this.buttonRole.artificialPermission= 1
    		this.buttonRole.bofangTTSPermission= 1
    	}else{
    		let buttonRoles = JSON.parse(sessionStorage.getItem("buttonRoles"))
    		let meetSign = buttonRoles.meetSign
    		if(meetSign.length>0){
    			for(let value of meetSign){
    				if(value=='distributionPermission'){
    					this.buttonRole.distributionPermission= 1
    				}else if(value=='cancelDistributionPermission'){
    					this.buttonRole.cancelDistributionPermission= 1
    				}else if(value=='artificialPermission'){
    					this.buttonRole.artificialPermission= 1
    				}else if(value=='bofangTTSPermission'){
    					this.buttonRole.bofangTTSPermission= 1
    				}
    			}
    		}
    	}
    },
    
    fpZw(row) {
    	let param = {
    		hjId: row.hjid
    	}
    	FpZw(param).then(res => {
    		Message({
          message: res.errMsg,
          type: 'success',
          duration: 5 * 1000
	      });
	      this.getList()
    	})
    },
    
    qxFpZw(row){
    	let param ={
    		hjId: row.hjid
    	}
    	QxFpZw(param).then(res => {
    		Message({
          message: res.errMsg,
          type: 'success',
          duration: 5 * 1000
	      });
	      
	      this.getList()
    	})
    },
    
    getSurplusZw(row) {
    	this.zws = []
    	
    	this.dialogRgFpVisible=true
    	
    	let param ={
    		hjId: row.hjid
    	}
    	GetSurplusZw(param).then(res => {
    		let jlHjDj = res.jlHjDj
    		this.rgFpDataForm.hjId = jlHjDj.hjid
    		this.rgFpDataForm.frName = jlHjDj.frName
    		
    		let sysHjLineList = res.sysHjLineList
    		for(let x of sysHjLineList){
    			let value = {}
    			value.id= x.webid
    			value.name = x.zw
    			this.zws.push(value)
    		}
    	})
    	
    	
    },
    
    rgFpZw() {
    	this.$refs['rgFpDataForm'].validate((valid) => {
        if (valid) {
	        RgFpZw(this.rgFpDataForm).then(res => {
		    		Message({
		          message: res.errMsg,
		          type: 'success',
		          duration: 5 * 1000
			      });
			      
			      this.dialogRgFpVisible=false
		    	  this.getList();
		    	})
        }
      })
    	
    	
    	
    },
    
    grantCall(row) {
    	let param ={
    		hjId: row.hjid
    	}
    	GrantCall(param).then(res => {
    		Message({
          message: res.errMsg,
          type: 'success',
          duration: 5 * 1000
	      });
	      
    		this.getList();
    	})
    },
    
    cancelGrantCall(row){
    	let param ={
    		hjId: row.hjid
    	}
    	CancelGrantCall(param).then(res => {
    		Message({
          message: res.errMsg,
          type: 'success',
          duration: 5 * 1000
	      });
    		this.getList();
    	})
    	
    },
    hechengTTS(row){
    	let param={
    		hjid:row.hjid
    	}
    	HechengTTS(param).then(res=>{
    		Message({
          message: '请等待2秒之后点击播放按钮',
          type: 'success',
          duration: 5 * 1000
	      });
    	}).catch(error =>{
    	})
    },
    bofangTTS(row){
    	let param={
    		hjid:row.hjid
    	}
    	BofangTTS(param).then(res=>{
    		if(res.url){
    			document.getElementById("audioTts").url=res.url
    		}
    	}).catch(error =>{
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
