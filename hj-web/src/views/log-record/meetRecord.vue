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
      <el-table-column width="180" align="center" label="音视频操作">
        <template slot-scope="scope">
          <el-button type="primary" size="mini"  @click="handleUpdate(scope.row)">播放录音录像</el-button>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="摘要操作">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">录入回放</el-button>
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">查看</el-button>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="录音操作">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">播放录音</el-button>
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">下载录音</el-button>
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

    <!-- 新增或编辑 -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form :rules="rules" :model="dataForm" ref="dataForm" label-position="right" label-width="120px" style='width: 400px; margin-left:25%;' >
      	<el-input v-if="false" v-model="dataForm.frNo" ></el-input>
        <el-form-item label="服刑人员姓名" prop="frName">
          <el-input v-model="dataForm.frName"></el-input>
        </el-form-item>
        <el-form-item label="证件号码" prop="qsSfz">
          <el-input v-model="dataForm.qsSfz"></el-input>
          <el-button  size="mini" type="primary" @click="handleDistinguish()">识别</el-button>
        </el-form-item>
        <el-form-item label="亲属姓名" prop="qsName">
          <el-input v-model="dataForm.qsName"></el-input>
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
import { findPojo, findOne, findJqList } from '@/api/meetRecord'

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
      // 新增或编辑弹窗
      dataForm: { 
        webId: undefined,
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
        bz: undefined
      },
      jqs: [ // 监区下拉选框
      
      ],
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编 辑',
        create: '新 增'
      },
      rules: {
        qsName: [{ required: true, message: '亲属姓名不能为空', trigger: 'blur' }]
      },
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
    }
  },
  filters: {
    
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
    handleUpdate(row) {
    	let param = {
    		id: row.webId
    	}
    	findOne(param).then((res) =>{
    		this.dataForm.webId = res.data.webId,
        this.dataForm.frName =  res.data.frName,
        this.dataForm.frNo = res.data.frNo,
        this.dataForm.qsZjlb = res.data.qsZjlb,
        this.dataForm.qsSfz = res.data.qsSfz,
        this.dataForm.qsName = res.data.qsName,
        this.dataForm.gx = res.data.gx,
        this.dataForm.qsCard = res.data.qsCard,
        this.dataForm.dz = res.data.dz,
        this.dataForm.xb = res.data.xb,
        this.dataForm.tele = res.data.tele,
        this.dataForm.spState = res.data.spState,
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
