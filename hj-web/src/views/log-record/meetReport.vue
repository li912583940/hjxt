<template>

<div class="app-container">
	<div class="filter-container">
	  <el-cascader
	  	class="filter-item"
		:options="yearList"
		v-model="fromYears"
		@change="handleChange"
		change-on-select clearable>
      </el-cascader>
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入服刑人员编号" v-model="listQuery.frNo" clearable>
      </el-input>
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入服刑人员姓名" v-model="listQuery.frName" clearable>
      </el-input>
      <el-select clearable style="width: 200px" class="filter-item" v-model="listQuery.jq" placeholder="选择监区">
        <el-option v-for="item in jqs" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <el-select clearable style="width: 200px" class="filter-item" v-model="listQuery.hjType" placeholder="请选择会见类型">
        <el-option v-for="item in hjTypes" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <!--<el-select clearable style="width: 200px" class="filter-item" v-model="listQuery.hjMode" placeholder="请选择会见类型">
        <el-option v-for="item in hjModes" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>-->
      <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleFilter">{{$t('criminal.search')}}</el-button>
    </div>

	
  <div class="dashboard-editor-container">
  	
  	<!--<div>-->
    <!--<panel-group @handleSetLineChartData="handleSetLineChartData"/>-->

    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <div id="reportClass" style="height: 450px;width: 100%;"></div>
    </el-row>

  </div>
  </div>
</template>

<script>
import { RequestReport, findJqList, findYearList } from '@/api/meetReport'
	
	
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import waves from '@/directive/waves' // 水波纹指令


export default {
  name: 'meetReport',
  directives: {
    waves
  },
  data() {
    return {
      listQuery: {
        years: undefined,
        frNo: undefined,
        frName: undefined,
        jq: undefined,
        hjType: undefined,
        hjMode: undefined
      },
      fromYears: [],
      
      hjCount: [], // y坐标数据
      djCount: [],
      titleDate: [], // x坐标显示标题
      
      yearList: [], //年份级联框
      jqs: [ // 监区下拉选框
      
      ],
      hjTypes:[
        {
      		id: 1,
      		name: '亲属会见'
      	},
      	{
      		id: 2,
      		name: '监护人会见'
      	},
      	{
      		id: 3,
      		name: '律师会见'
      	},
      	{
      		id: 4,
      		name: '使领馆探视'
      	},
      	{
      		id: 5,
      		name: '提审会见'
      	},
      	{
      		id: 6,
      		name: '公务会见'
      	},
      	{
      		id: 9,
      		name: '特批会见'
      	},
      	{
      		id: 99,
      		name: '其他会见'
      	}
      ],
      hjModes: [
        {
      		id: 1,
      		name: '隔离会见'
      	},
      	{
      		id: 2,
      		name: '非隔离会见'
      	},
      	{
      		id: 3,
      		name: '远程视频会见'
      	},
      	{
      		id: 9,
      		name: '其他方式'
      	},
      ],
    }
  },
  created() {
  	this.getJqList()
  	this.getYearList() // 获取会见记录的年份记录
  	
  },
  mounted() {
  	this.initChart()
  },
  methods: {
  	getList() {
  		this.hjCount=[]
  		this.titleDate=[]
  		this.djCount=[]
  		if(this.fromYears.length > 0){
  			let y = ''
  			let i=1
  			for(let t of this.fromYears){
  				if(i==1){
  					y=t
  				}else{
  					y+=','+t
  				}
  				
  				i++
  			}
  			this.listQuery.years=y
  		}else{
  			this.listQuery.years=undefined
  		}
  		
  		RequestReport(this.listQuery).then((res) => {
  			this.hjCount = res.hjRecList
  			this.titleDate = res.dateList
  			this.djCount = res.hjdjList
      	  this.initChart()
        }).catch(error => {
        
        })
        
  	},
  	handleFilter() {
      this.getList()
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
    getYearList(){
    	findYearList({}).then(res => {
    	this.yearList=res.list
    	})
    },
    handleChange(value) {
        console.log(value);
    },
    
    initChart() {
      this.chart = echarts.init(document.getElementById('reportClass'), 'macarons')
     /*this.chart = echarts.init(this.$el, 'macarons')*/

      this.chart.setOption({
        tooltip: {
          trigger: 'axis',
          axisPointer: { // 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        grid: {
          top: 10,
          left: '2%',
          right: '2%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [{
          type: 'category',
          data: this.titleDate,
          axisTick: {
            alignWithLabel: true
          }
        }],
        yAxis: [{
          type: 'value',
          axisTick: {
            show: false
          }
        }],
        series: [
        	{
	          name: '会见批次', itemStyle: {
	            normal: {
	              color: '#FF005A',
	              lineStyle: {
	                color: '#FF005A',
	                width: 1
	              }
	            }
	          },
	          smooth: true,
	          type: 'bar',
	          data: this.hjCount,
	          animationDuration: 2800,
	          animationEasing: 'cubicInOut',
	          label: {
                normal: {
                    show: true,
                    position: 'inside'
                }
              }
	        },
	        {
	          name: '登记批次', itemStyle: {
	            normal: {
	              color: '#1874CD',
	              lineStyle: {
	                color: '#1874CD',
	                width: 1
	              }
	            }
	          },
	          smooth: true,
	          type: 'bar',
	          data: this.djCount,
	          animationDuration: 2800,
	          animationEasing: 'cubicInOut',
	          label: {
                normal: {
                    show: true,
                    position: 'inside'
                }
              }
	        }
        ]
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}
</style>
