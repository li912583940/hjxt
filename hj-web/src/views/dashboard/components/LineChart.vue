<template>
  <div :class="className" :style="{height:height,width:width}"/>
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import { debounce } from '@/utils'

import {GetWeekCount } from '@/api/meetRecord'

export default {
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '400px'
    },
    autoResize: {
      type: Boolean,
      default: true
    }

  },
  data() {
    return {
      chart: null,
      weekCount: [],
      weekDate: []
    }
  },

  created() {
     this.getWeekCount()
  },
  mounted() {
  },
  methods: {
    getWeekCount(){
  		GetWeekCount({}).then(res => {
  			let list = res.list
  			for(let x of list){
  				this.weekCount.push(x.count)
  				this.weekDate.push(x.timedate)
  			}
  			this.initChart()
  		})
  	},
  	
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons')
//    this.setOptions(this.chartData)
			this.chart.setOption({
				title : {
	        text: '一周内会见次数变化',
	        subtext: '只能查看权限内数据'
    		},
        xAxis: {
          data: this.weekDate,
          boundaryGap: false,
          axisTick: {
            show: false
          }
        },
        grid: {
          left: 20,
          right: 30,
          bottom: 20,
          top: 60,
          containLabel: true
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          },
          padding: [5, 10]
        },
        yAxis: {
          axisTick: {
            show: false
          }
        },
//      legend: {
//        data: ['expected', 'actual']
//      },
        series: [
	        {
	          name: '次数', itemStyle: {
	            normal: {
	              color: '#FF005A',
	              lineStyle: {
	                color: '#FF005A',
	                width: 2
	              }
	            }
	          },
	          smooth: true,
	          type: 'line',
	          data: this.weekCount,
	          animationDuration: 2800,
	          animationEasing: 'cubicInOut'
	        }
        ] 
      })
    }
  }
}
</script>
