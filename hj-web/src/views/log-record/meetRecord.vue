<template>
  <div class="app-container">
    <div class="filter-container">
    	<el-date-picker
    		class="filter-item"
	      v-model="listQuery.callTimeStart"
	      align="right"
	      type="date"
	      placeholder="选择开始日期"
	      :picker-options="pickerOptionsStart">
	    </el-date-picker>
	    <el-date-picker
	    	class="filter-item"
	      v-model="listQuery.callTimeEnd"
	      align="right"
	      type="date"
	      placeholder="选择结束日期">
	    </el-date-picker>
	    <el-select clearable style="width: 200px" class="filter-item" v-model="listQuery.jq" placeholder="选择监区">
        <el-option v-for="item in jqs" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
    	<el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入服刑人员编号" v-model="listQuery.frNo">
      </el-input>
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入服刑人员姓名" v-model="listQuery.frName">
      </el-input>
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入亲属姓名" v-model="listQuery.qsName">
      </el-input>
      <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleFilter">{{$t('criminal.search')}}</el-button>
      <el-button class="filter-item" type="primary" :loading="downloadLoading" v-waves icon="el-icon-download" @click="handleDownload">{{$t('criminal.export')}}</el-button>
    </div>

    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row
      style="width: 100%">
      <el-table-column width="160" align="center" label="通话开始时间">
        <template slot-scope="scope">
          <span>{{scope.row.callTimeStart}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160" align="center" label="通话结束时间">
        <template slot-scope="scope">
          <span>{{scope.row.callTimeEnd}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160" align="center" label="通话时长">
        <template slot-scope="scope">
          <span>{{scope.row.callTimeLen}}秒</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="座位名称">
        <template slot-scope="scope">
          <span>{{scope.row.zw}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="会见类型">
        <template slot-scope="scope">
          <span v-if="scope.row.type==1">普见</span>
          <span v-if="scope.row.type==2">宽见</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="监区名称">
        <template slot-scope="scope">
          <span>{{scope.row.jqName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="300" align="center" label="罪犯编号">
        <template slot-scope="scope">
          <span>{{scope.row.frNo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="罪犯姓名">
        <template slot-scope="scope">
          <span>{{scope.row.frName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="亲属个数">
        <template slot-scope="scope">
          <span>{{scope.row.qsIndex}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="亲属信息">
        <template slot-scope="scope">
          <span>{{scope.row.qsInfo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="警察信息">
        <template slot-scope="scope">
          <span>{{scope.row.yjNo}}/{{scope.row.yjName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="280" align="center" label="音视频操作">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="playRecor(scope.row)">播放录音录像</el-button>
          <el-button type="primary" size="mini" @click="downRecord(scope.row)">下载录音录像</el-button>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="摘要操作">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="zhushi(scope.row)">注释</el-button>
          <el-button type="primary" size="mini" @click="zhushiAll(scope.row)">查看所有注释</el-button>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="录音操作">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="palyTape(scope.row)">播放录音</el-button>
          <a :href="callRecfileUrl"><el-button type="primary" size="mini">下载录音</el-button></a>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="录音评级">
        <template slot-scope="scope">
          <span v-if="scope.row.recRatingState==0">未评</span>
          <span v-if="scope.row.recRatingState==1">异常</span>
          <span v-if="scope.row.recRatingState==2">正常</span>
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="handleUpdate(scope.row)">编辑</el-button>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="评级详情">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="handleUpdate(scope.row)">查看</el-button>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="复听状态">
        <template slot-scope="scope">
          <span v-if="scope.row.recAssessmentState==0">未听</span>
          <span v-if="scope.row.recAssessmentState==1">已听</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="复听详情">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="handleUpdate(scope.row)">查看</el-button>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="复听超时">
        <template slot-scope="scope">
          <span v-if="scope.row.recordOverTime==0">未超时</span>
          <span v-if="scope.row.recordOverTime==1">已超时</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="其它详情">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="handleUpdate(scope.row)">查看</el-button>
        </template>
      </el-table-column>
    </el-table>

		<!-- 分页 -->
    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="listQuery.pageNum" :page-sizes="[10,20,30, 50]" :page-size="listQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

    <!-- 播放录音录像 开始 -->
    <el-dialog title="播放录音录像 " :visible.sync="dialogPlayVisible" @close='closePlayDialog'>
    	<div style="position: relative;margin-top: 10px; margin-bottom: 30px;">
    		<el-row :gutter="12">
    			<el-col :span="8" :offset="1" style="margin-left: 50px;" >
				    	<div>
				    		<video id="video1" width="360" height="240" controls="controls">
				    			<source :src="callRecfileUrl" type="video/ogg" />
				    			<!--<source :src="callRecfileUrl" type="video/mp4" />-->
				    			<!--<audio :src="callRecfileUrl" controls="controls" controlsList="nodownload"></audio>-->
				    		</video>
				    	</div>
				  </el-col>
				  
				  <el-col :span="8" :offset="1" style="margin-left: 120px;">
				    	<div>
				    		<video id="video2" width="360" height="240" controls="controls">
				    			<source :src="callRecfileUrl" type="video/ogg" />
				    			<!--<source :src="callRecfileUrl" type="video/mp4" />-->
				    			<!--<audio :src="callRecfileUrl" controls="controls" controlsList="nodownload"></audio>-->
				    		</video>
				    	</div>
				  </el-col>
				  
		    </el-row>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogPlayVisible = false">关 闭</el-button>
      </div>
    </el-dialog>
    <!-- 播放录音录像 结束 -->

    <!-- 注释 开始 -->
    <el-dialog title="注释" :visible.sync="dialogZSVisible"  width="50%">
	      <el-form  :model="dataFormZS" ref="dataFormZS" label-position="right" label-width="120px" style='width: 400px; margin-left:25%;' >
	        <el-form-item label="呼叫ID" >
	          <el-input v-model="dataFormZS.callId" :disabled="true"></el-input>
	        </el-form-item>
	        <el-form-item label="罪犯姓名" >
	          <el-input v-model="dataFormZS.frName" :disabled="true"></el-input>
	        </el-form-item>
	        <el-form-item label="注释">
	          <el-input type="textarea" :rows="2" v-model="dataFormZS.writeTxt"></el-input>
	        </el-form-item>
	      </el-form>
	      <div slot="footer" class="dialog-footer">
	        <el-button @click="dialogZSVisible = false">取 消</el-button>
	        <el-button type="primary" @click="updateZS">确 定</el-button>
	      </div>
	  </el-dialog>
	  <!-- 注释 结束 -->
	  
	  <!-- 查看所有注释  开始  -->
    <el-dialog title="查看所有注释" :visible.sync="dialogZsAllVisible" width="50%">
      <el-table :key='zsAllTableKey' :data="zsAllList" v-loading="zsAllListLoading" element-loading-text="给我一点时间" border fit highlight-current-row
	      style="width: 100%">
	      <el-table-column width="160" align="center" label="用户编号">
	        <template slot-scope="scope">
	          <span>{{scope.row.userNo}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="160" align="center" label="用户姓名">
	        <template slot-scope="scope">
	          <span>{{scope.row.userName}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="" align="center" label="注释摘要">
	        <template slot-scope="scope">
	          <span>{{scope.row.writeTxt}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="180" align="center" label="摘要录入时间">
	        <template slot-scope="scope">
	          <span>{{scope.row.createTime | dateFormat}}</span>
	        </template>
	      </el-table-column>
	    </el-table>
	    <!-- 分页 -->
	    <div class="pagination-container">
	      <el-pagination background @size-change="handleZsAllSizeChange" @current-change="handleZsAllCurrentChange" :current-page="zsAllListQuery.pageNum" :page-sizes="[10,20,30, 50]" :page-size="zsAllListQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="zsAllTotal">
	      </el-pagination>
	    </div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogZsAllVisible = false">关闭</el-button>
      </span>
    </el-dialog>
    <!-- 查看所有注释  结束  -->
    
    <!-- 播放录音 开始 -->
    <el-dialog title="播放录音" :visible.sync="dialogTapeVisible" @close='closeTapeDialog'>
    	<div style="position: relative;margin-top: 10px; margin-bottom: 30px;">
				<audio :src="callRecfileUrl" controls="controls" controlsList="nodownload">
				</audio>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogTapeVisible = false">关 闭</el-button>
      </div>
    </el-dialog>
    <!-- 播放录音 结束 -->
    
  </div>
</template>

<script>
import { findPojo, findOne, findJqList, GetZs, AddRecordFlag, GetZsAllPojo } from '@/api/meetRecord'

import moment from 'moment';
import waves from '@/directive/waves' // 水波纹指令
import { parseTime } from '@/utils'


export default {
  name: 'meetRecord',
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
        callTimeStart: undefined,
        callTimeEnd: undefined,
        jq: undefined,
        frNo: undefined,
        frName: undefined,
        qsName: undefined
      },
      statusOptions: ['published', 'draft', 'deleted'],
      jqs: [ // 监区下拉选框
      
      ],
      downloadLoading: false,
	    pickerOptionsStart: {
	      shortcuts: [{
	        text: '今天',
	        onClick(picker) {
	          picker.$emit('pick', new Date());
	        }
	      }, {
	        text: '昨天',
	        onClick(picker) {
	          const date = new Date();
	          date.setTime(date.getTime() - 3600 * 1000 * 24);
	          picker.$emit('pick', date);
	        }
	      }, {
	        text: '最近一周',
	        onClick(picker) {
	          const date = new Date();
	          date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
	          picker.$emit('pick', date);
	        }
	      }, {
	        text: '最近一个月',
	        onClick(picker) {
	          const date = new Date();
	          date.setTime(date.getTime() - 3600 * 1000 * 24 * 30);
	          picker.$emit('pick', date);
	        }
	      }, {
	        text: '最近三个月',
	        onClick(picker) {
	          const date = new Date();
	          date.setTime(date.getTime() - 3600 * 1000 * 24 * 90);
	          picker.$emit('pick', date);
	        }
	      }, {
	        text: '最近一年',
	        onClick(picker) {
	          const date = new Date();
	          date.setTime(date.getTime() - 3600 * 1000 * 24 * 365);
	          picker.$emit('pick', date);
	        }
	      }]
	    },
	    
	    /** 播放录音录像 开始 */
	    dialogPlayVisible: false,
	    
	    asl: '/阳光电影www.ygdy8.com.巴比龙.BD.720p.中英双字幕.mkv',
	    asla: '/20180707154146278_011@1.mp4',
	    aslb: 'F:/DD/Lollipop.mp3',
	    callRecfileUrl: undefined,
	    callVideofile1Url: undefined,
	    callVideofile2Url: undefined,
	    /** 播放录音录像 结束 */
	   
	   /** 注释 开始 */
      dialogZSVisible: false, 
      dataFormZS: {
      	callId: undefined,
      	frName: undefined,
      	writeTxt: undefined,
      	
      },
      /** 注释 结束 */
     
      /**  查看所有注释  开始 */
		  dialogZsAllVisible: false,
		  zsAllTableKey: 0,
		  zsAllList: null,
		  zsAllTotal: null,
		  zsAllListLoading: true,
		  zsAllListQuery: {
		    pageNum: 1,
		    pageSize: 10,
		    callId: undefined
		  },
		  /**  查看所有注释  结束 */
		 
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
    this.getJqList()
  },
  methods: {
    getList() {
      this.listLoading = true
      if(!this.listQuery.callTimeStart){
      	this.listQuery.callTimeStart = undefined
      }else{
      	this.listQuery.callTimeStart = this.dateFormatYMD(this.listQuery.callTimeStart)+" 00:00:00";
      }
      if(!this.listQuery.callTimeEnd){
      	this.listQuery.callTimeEnd = undefined
      }else{
      	this.listQuery.callTimeEnd = this.dateFormatYMD(this.listQuery.callTimeEnd)+" 23:59:59";
      }
      if(!this.listQuery.jq){
      	this.listQuery.jq = undefined
      }else{
      	this.listQuery.jqNo = this.listQuery.jq
      }
      if(!this.listQuery.frName){
      	this.listQuery.frName = undefined
      }
      if(!this.listQuery.frNo){
      	this.listQuery.frNo = undefined
      }
      if(!this.listQuery.qsName){
      	this.listQuery.qsName = undefined
      }
      findPojo(this.listQuery).then((res) => {
      	 this.list = res.pojo.list
      	 this.total = res.pojo.count
      	 this.listLoading = false
      }).catch(error => {
          this.listLoading = false
      })
    },
    getJqList() { //监区下拉框
    	if(this.jqs.length === 0) {
    		findJqList({}).then((res) => {
	    		let list = res.list
	    		for(let x of list){
					  let value = {}
					  value.id = x.jqNo
					  value.name = x.jqName
					  this.jqs.push(value)
					}
	    	})
    	}
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
    /** 播放录音录像 开始 */
    playRecor(row){ //播放录音录像
    	this.dialogPlayVisible =true
    	//this.callRecfileUrl = row.callRecfile
    	this.callRecfileUrl = "/api/file"+this.asl
    },
    downRecord(row){ //下载录音录像
    	console.log(row.callVideofile1Url)
    	// 1 录像
    	const downloadElement1 = document.createElement('a')
    	downloadElement1.href = row.callVideofile1Url
    	downloadElement1.download=""
    	document.body.appendChild(downloadElement1)
    	downloadElement1.click()
    	document.body.removeChild(downloadElement1)
    	
    	console.log(row.callVideofile2Url)
    	// 2 录像
    	const downloadElement2 = document.createElement('a')
    	downloadElement2.href = row.callVideofile2Url
    	downloadElement2.download=""
    	document.body.appendChild(downloadElement2)
    	downloadElement2.click()
    	document.body.removeChild(downloadElement2)
    	
    	console.log(row.callRecfileUrl)
    	// 2 录音
    	const downloadElement3 = document.createElement('a')
    	downloadElement3.href = row.callRecfileUrl
    	downloadElement3.download=""
    	document.body.appendChild(downloadElement3)
    	downloadElement3.click()
    	document.body.removeChild(downloadElement3)
    },
    
    closePlayDialog(){ 
    	var video1 = document.getElementById("video1")
    	if(video1.play){
    		video1.currentTime = 0;
        video1.pause();
    	}
    	var video2 = document.getElementById("video2")
    	if(video2.play){
    		video2.currentTime = 0;
        video2.pause();
    	}
    },
    
    /** 播放录音录像 结束 */
   
		/** 注释 开始 */
		resetFormZS() { //重置表单
			this.dataFormZS.callId = undefined
			this.dataFormZS.frName = undefined
			this.dataFormZS.writeTxt = undefined
	  },
    getZs(callId){ //获取注释
    	GetZs({callId: callId}).then(res => {
    		this.dataFormZS.writeTxt = res.data.writeTxt
    	})
    },
		zhushi(row){
			this.resetFormZS()
			this.dialogZSVisible = true
			this.dataFormZS.callId = row.callId
			this.dataFormZS.frName = row.frName
			
			this.getZs(this.dataFormZS.callId)
		},
		updateZS(){
			AddRecordFlag(this.dataFormZS).then(res => {
				Message({
		          message: res.errMsg,
		          type: 'success',
		          duration: 5 * 1000
		        });
			})
			this.dialogZSVisible = false
		},
		/** 注释 结束 */
   
    /** 查看所有注释 开始 */
    zhushiAll(row){
    	this.dialogZsAllVisible= true
    	this.zsAllListQuery.callId=row.callId
    	this.getZsAllList()
    },
    getZsAllList(){ // 获取所有注释
    	GetZsAllPojo(this.zsAllListQuery).then(res => {
    		 this.zsAllList = res.pojo.list
      	 this.zsAllTotal = res.pojo.count
      	 this.zsAllListLoading = false
      }).catch(error => {
         this.zsAllListLoading = false
      })
    },
    handleZsAllSizeChange(val) {
      this.zsAllListQuery.pageSize = val
      this.getZsAllList()
    },
    handleZsAllCurrentChange(val) {
      this.zsAllListQuery.pageNum = val
      this.getZsAllList()
    },
    /** 查看所有注释 结束 */
    
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
	    		id: row.webId
	    	}
				RequestDelete(param).then(() => {
	    		this.getList()
	    	}).catch(error => {
	        this.dialogFormVisible = false
	      })
			})
		},
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['timestamp']
        const filterVal = ['timestamp']
        const data = this.formatJson(filterVal, this.list)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: 'table-list'
        })
        this.downloadLoading = false
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    },
    dateFormat(row, column) {
			//时间格式化  
	    let date = row[column.property];  
	    if (date == undefined) {  
	      return "";  
	    }  
	    return moment(date).format("YYYY-MM-DD HH:mm:ss");  
		},
		dateFormats: function (val) {
			if(!val){
				return undefined
			}
			return moment(val).format("YYYY-MM-DD HH:mm:ss");
		},
		dateFormatYMD: function (val) {
			if(!val){
				return undefined
			}
			return moment(val).format("YYYY-MM-DD");
		},
  }
}
</script>
