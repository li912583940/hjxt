<template>
  <div class="app-container">
    <div class="filter-container">
    	<el-date-picker
    		style="width: 200px"
    		class="filter-item"
	      v-model="listQuery.callTimeStart"
	      align="right"
	      type="date"
	      placeholder="选择开始日期"
	      :picker-options="pickerOptionsStart">
	    </el-date-picker>
	    <el-date-picker
	    	style="width: 200px"
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
      <el-select clearable style="width: 200px" class="filter-item" v-model="listQuery.recRatingState" placeholder="选择评级状态">
        <el-option v-for="item in recRatingStates" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <el-select clearable style="width: 200px" class="filter-item" v-model="listQuery.zw" placeholder="请选择座位">
        <el-option v-for="item in zws" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <el-select clearable style="width: 200px" class="filter-item" v-model="listQuery.hjType" placeholder="请选择会见类型">
        <el-option v-for="item in hjTypes" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <el-select clearable style="width: 200px" class="filter-item" v-model="listQuery.recordOverTime" placeholder="请选择复听超时">
        <el-option v-for="item in recordOverTimes" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <el-select clearable style="width: 200px" class="filter-item" v-model="listQuery.recAssessmentState" placeholder="请选择复听状态">
        <el-option v-for="item in recAssessmentStates" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleFilter">{{$t('criminal.search')}}</el-button>
      <el-button v-if="buttonRole.exportPermission==1" class="filter-item" type="primary" :loading="downloadLoading" v-waves icon="el-icon-download" @click="handleDownload">{{$t('criminal.export')}}</el-button>
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
      <el-table-column width="110" align="center" label="通话时长(秒)">
        <template slot-scope="scope">
          <span>{{scope.row.callTimeLen}}</span>
        </template>
      </el-table-column>
      <el-table-column width="90" align="center" label="座位名称">
        <template slot-scope="scope">
          <span>{{scope.row.zw}}</span>
        </template>
      </el-table-column>
      <el-table-column width="110" align="center" label="会见类型">
        <template slot-scope="scope">
          <span v-if="scope.row.hjType==1">亲属会见</span>
          <span v-else-if="scope.row.hjType==2">监护人会见</span>
          <span v-else-if="scope.row.hjType==3">律师会见</span>
          <span v-else-if="scope.row.hjType==4">使领馆探视</span>
          <span v-else-if="scope.row.hjType==5">提审会见</span>
          <span v-else-if="scope.row.hjType==6">公务会见</span>
          <span v-else-if="scope.row.hjType==9">特批会见</span>
          <span v-else-if="scope.row.hjType==99">其他会见</span>
        </template>
      </el-table-column>
      <el-table-column width="90" align="center" :label="$t('currency.jqName')">
        <template slot-scope="scope">
          <span>{{scope.row.jqName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" :label="$t('currency.frNo')">
        <template slot-scope="scope">
          <span>{{scope.row.frNo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="120" align="center" :label="$t('currency.frName')">
        <template slot-scope="scope">
          <span>{{scope.row.frName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="90" align="center" label="亲属个数">
        <template slot-scope="scope">
          <span>{{scope.row.qsIndex}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="亲属信息">
        <template slot-scope="scope">
          <span>{{scope.row.qsInfo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160" align="center" label="警察信息">
        <template slot-scope="scope">
          <span>{{scope.row.yjNo}}/{{scope.row.yjName}}</span>
        </template>
      </el-table-column>
      <el-table-column v-if="buttonRole.playAudioVideoPermission==1 || buttonRole.downAudioVideoPermission==1" width="130" align="center" label="音视频操作" fixed="right">
        <template slot-scope="scope">
        	<div>
            <el-button v-if="buttonRole.playAudioVideoPermission==1" type="primary" size="mini" @click="playRecor(scope.row)">播放录音录像</el-button>
          </div>
          <div style="margin-top: 2px;">
            <el-button v-if="buttonRole.downAudioVideoPermission==1" type="primary" size="mini" @click="downRecord(scope.row)">下载录音录像</el-button>
       		</div>
        </template>
      </el-table-column>
      <el-table-column v-if="buttonRole.playAudioPermission==1 || buttonRole.downAudioPermission==1" width="100" align="center" label="录音操作" fixed="right">
        <template slot-scope="scope">
        	<div>
            <el-button v-if="buttonRole.playAudioPermission==1" type="primary" size="mini" @click="palyTape(scope.row)">播放录音</el-button>
          </div>
          <div style="margin-top: 2px;">
            <el-button v-if="buttonRole.downAudioPermission==1" type="primary" size="mini" @click="down3(scope.row.callRecfileUrl)">下载录音</el-button>
          </div>
        </template>
      </el-table-column>
      <el-table-column v-if="buttonRole.notesPermission==1 || buttonRole.seeNotesPermission==1" width="80" align="center" label="摘要操作" fixed="right">
        <template slot-scope="scope">
          <div>
            <el-button v-if="buttonRole.notesPermission==1" type="primary" size="mini" @click="zhushi(scope.row)">注释</el-button>
          </div>
          <div style="margin-top: 2px;">
            <el-button v-if="buttonRole.seeNotesPermission==1" type="primary" size="mini" @click="zhushiAll(scope.row)">查看</el-button>
          </div>
        </template>
      </el-table-column>
      <el-table-column width="80" align="center" label="录音评级" >
        <template slot-scope="scope">
          <span v-if="scope.row.recRatingState==0">未评</span>
          <span v-if="scope.row.recRatingState==1">异常</span>
          <span v-if="scope.row.recRatingState==2">正常</span>
        </template>
      </el-table-column>
      <el-table-column v-if="buttonRole.ratingPermission==1 || buttonRole.seeRatingPermission==1" width="80" align="center" label="评级操作" fixed="right">
        <template slot-scope="scope">
        	<div>
            <el-button v-if="buttonRole.ratingPermission==1" type="primary" size="mini" @click="openRatingState(scope.row)">评级</el-button>
          </div>
          <div style="margin-top: 2px;">
            <el-button v-if="buttonRole.seeRatingPermission==1" type="primary" size="mini" @click="openRatingStateAll(scope.row)">查看</el-button>
          </div>
        </template>
      </el-table-column>
      <el-table-column width="80" align="center" label="复听状态">
        <template slot-scope="scope">
          <span v-if="scope.row.recAssessmentState==0">未听</span>
          <span v-if="scope.row.recAssessmentState==1">已听</span>
        </template>
      </el-table-column>
      <el-table-column  v-if="buttonRole.seeAssessmentPermission==1" width="100" align="center" label="详情信息" fixed="right">
        <template slot-scope="scope">
        	<div>
            <el-button type="primary" size="mini" @click="openAllAssessment(scope.row)">查看复听</el-button>
          </div>
          <div style="margin-top: 2px;">
            <el-button type="primary" size="mini" @click="openOtherInfo(scope.row)">查看其他</el-button>
          </div>
        </template>
      </el-table-column>
      <el-table-column width="80" align="center" label="复听超时">
        <template slot-scope="scope">
          <span v-if="scope.row.recordOverTime==0">未超时</span>
          <span v-if="scope.row.recordOverTime==1">已超时</span>
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

		<!-- 下载录音录像  IE浏览器 -->
    <el-dialog title="录音录像下载" :visible.sync="dialogPlayDownVisible" width="40%">
    	<div style="position: relative;margin-top: 10px; margin-bottom: 30px; margin-left: 20%;">
    		<span style="margin-left: 20px;">
    			<el-button type="primary" size="mini" @click="down1(callVideofile1Url)">录像文件1</el-button>
    		</span>
    		<span style="margin-left: 20px;">
    			<el-button type="primary" size="mini" @click="down2(callVideofile2Url)">录像文件2</el-button>
    		</span>
    		<span style="margin-left: 20px;">
    			<el-button type="primary" size="mini" @click="down3(callRecfileUrl)">录音文件</el-button>
    		</span>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogPlayDownVisible = false">关 闭</el-button>
      </div>
    </el-dialog>
    <!-- 下载录音录像 结束 下载 IE浏览器 -->
    
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
    <el-dialog title="播放录音" :visible.sync="dialogTapeVisible" @close='closeTapeDialog'  width="40%">
    	<div style="position: relative;margin-top: 10px; margin-bottom: 30px; margin-left: 25%;">
				<audio id="audio1" :src="callRecfileUrl" controls="controls" controlsList="nodownload">
				</audio>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogTapeVisible = false">关 闭</el-button>
      </div>
    </el-dialog>
    <!-- 播放录音 结束 -->
    
    <!-- 录音评级 开始 -->
    <el-dialog title="录音评级" :visible.sync="dialogRatingStateVisible" >
    	 <el-form :model="dataFormRatingState" ref="dataFormRatingState" label-position="right" label-width="120px" style='width: 400px; margin-left:25%;' >
        <el-form-item label="呼叫ID" prop="callId">
          <el-input v-model="dataFormRatingState.callId" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="用户编号" prop="userNo">
          <el-input v-model="dataFormRatingState.userNo" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="用户姓名" prop="userName">
          <el-input v-model="dataFormRatingState.userName" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="评级状态" prop="recRatingState">
          <el-select class="filter-item" v-model="dataFormRatingState.recRatingState" placeholder="请选择">
            <el-option v-for="item in recRatingStates" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="评级内容" prop="writeTxt">
          <el-input type="textarea" :rows="2" v-model="dataFormRatingState.writeTxt"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogRatingStateVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateRatingStateData">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 录音评级 开始 -->
    
    <!-- 查看所有录音评级  开始  -->
    <el-dialog title="查看所有录音评级" :visible.sync="dialogRatingStateAllVisible" width="50%">
      <el-table :key='ratingStateAllTableKey' :data="ratingStateAllList" v-loading="ratingStateAllListLoading" element-loading-text="给我一点时间" border fit highlight-current-row
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
	      <el-table-column width="" align="center" label="评级内容">
	        <template slot-scope="scope">
	          <span>{{scope.row.writeTxt}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="180" align="center" label="评级时间">
	        <template slot-scope="scope">
	          <span>{{scope.row.createTime | dateFormat}}</span>
	        </template>
	      </el-table-column>
	    </el-table>
	    <!-- 分页 -->
	    <div class="pagination-container">
	      <el-pagination background @size-change="handleRatingStateAllSizeChange" @current-change="handleRatingStateAllCurrentChange" :current-page="ratingStateAllListQuery.pageNum" :page-sizes="[10,20,30, 50]" :page-size="ratingStateAllListQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="ratingStateAllTotal">
	      </el-pagination>
	    </div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogRatingStateAllVisible = false">关闭</el-button>
      </span>
    </el-dialog>
    <!-- 查看所有录音评级  结束  -->
    
    
    <!-- 复听详情  开始  -->
    <el-dialog title="复听详情" :visible.sync="dialogAllAssessmentVisible" width="50%">
      <el-table :key='allAssessmentTableKey' :data="allAssessmentList" v-loading="allAssessmentListLoading" element-loading-text="给我一点时间" border fit highlight-current-row
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
	      <el-table-column width="" align="center" label="评级内容">
	        <template slot-scope="scope">
	          <span>{{scope.row.writeTxt}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="180" align="center" label="评级时间">
	        <template slot-scope="scope">
	          <span>{{scope.row.createTime | dateFormat}}</span>
	        </template>
	      </el-table-column>
	    </el-table>
	    <!-- 分页 -->
	    <div class="pagination-container">
	      <el-pagination background @size-change="handleAllAssessmentSizeChange" @current-change="handleAllAssessmentCurrentChange" :current-page="allAssessmentListQuery.pageNum" :page-sizes="[10,20,30, 50]" :page-size="allAssessmentListQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="allAssessmentTotal">
	      </el-pagination>
	    </div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogAllAssessmentVisible = false">关闭</el-button>
      </span>
    </el-dialog>
    <!-- 复听详情  结束  -->
    
    <!-- 其他详情  开始  -->
    <el-dialog title="其他详情" :visible.sync="dialogOtherInfoVisible" width="50%">
      <el-card class="box-card">
	      <el-table :key='otherInfoTableKey' :data="otherInfoList" v-loading="otherInfoListLoading" element-loading-text="给我一点时间" border fit highlight-current-row
		      style="width: 100%">
		      <el-table-column width="160" align="center" label="会见登记人">
		        <template slot-scope="scope">
		          <span>{{scope.row.djUser}}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="160" align="center" label="登记时间">
		        <template slot-scope="scope">
		          <span>{{scope.row.djTime | dateFormat}}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="" align="center" label="会见室审核人">
		        <template slot-scope="scope">
		          <span>{{scope.row.frInUser}}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="180" align="center" label="到达进入时间">
		        <template slot-scope="scope">
		          <span>{{scope.row.frInTime | dateFormat}}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="180" align="center" label="会见说明">
		        <template slot-scope="scope">
		          <span>{{scope.row.hjInfo}}</span>
		        </template>
		      </el-table-column>
		    </el-table>
		  </el-card>
		  
	    <el-card class="box-card">
	    	<el-table :key='otherQsInfoTableKey' :data="otherQsInfoList" v-loading="otherQsInfoListLoading" element-loading-text="给我一点时间" border fit highlight-current-row
		      style="width: 100%">
		      <el-table-column width="160" align="center" label="亲属姓名">
		        <template slot-scope="scope">
		          <span>{{scope.row.qsName}}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="160" align="center" label="身份证号码">
		        <template slot-scope="scope">
		          <span>{{scope.row.qsSfz}}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="" align="center" label="关系">
		        <template slot-scope="scope">
		          <span>{{scope.row.gx}}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="180" align="center" label="性别">
		        <template slot-scope="scope">
		          <span>{{scope.row.xb}}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="180" align="center" label="照片">
		        <template slot-scope="scope">
		          <span>{{scope.row.zp}}</span>
		        </template>
		      </el-table-column>
		    </el-table>
	    </el-card>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogOtherInfoVisible = false">关闭</el-button>
      </span>
    </el-dialog>
    <!-- 其他详情  结束  -->
    
  </div>
</template>

<script>
import { findPojo, findOne, findJqList, GetZwList, GetZs, AddRecordFlag, GetZsAllPojo, GetRatingState, UpdateRatingState, 
	GetRatingStateAllPojo, GetAllAssessmentPojo, GetOtherInfo, exportExcel } from '@/api/meetRecord'

import moment from 'moment';
import waves from '@/directive/waves' // 水波纹指令
import { parseTime } from '@/utils'
import { Message, MessageBox } from 'element-ui'

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
        qsName: undefined,
        recRatingState: undefined,
        zw: undefined,
        hjType: undefined,
        recordOverTime: undefined,
        recAssessmentState: undefined,
      },
      jqs: [ // 监区下拉选框
      
      ],
      recRatingStates: [
      	{
      		id: 0,
      		name: '未评'
      	},
      	{
      		id: 1,
      		name: '异常'
      	},
      	{
      		id: 2,
      		name: '正常'
      	}
      ],
      zws: [ //座位
      
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
      	},
      ],
      recordOverTimes: [
        {
      		id: 0,
      		name: '未超时'
      	},
      	{
      		id: 1,
      		name: '已超时'
      	},
      ],
      recAssessmentStates:[
        {
      		id: 0,
      		name: '未听'
      	},
      	{
      		id: 1,
      		name: '已听'
      	},
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
	    
	    dialogPlayDownVisible: false,
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
		 
		  /** 录音操作 开始 */
		  dialogTapeVisible: false,
		  /** 录音操作 结束 */
		 
		  /** 录音评级 开始 */
		  dialogRatingStateVisible: false,
		  dataFormRatingState: {
		  	webId: undefined,
		  	callId: undefined,
		  	userNo: undefined,
		  	userName: undefined,
		  	recRatingState: 0,
		  	writeTxt: undefined,
		  },
		  recRatingStates: [
		    {
        	id: 0,
        	name: '未评'
        },
      	{
      		id: 1,
      		name: '异常'
      	},
      	{
      		id: 2,
      		name: '正常'
      	}
		  ],
		  /** 录音评级 结束 */
		 
		 /**  查看所有录音评级  开始 */
		  dialogRatingStateAllVisible: false,
		  ratingStateAllTableKey: 0,
		  ratingStateAllList: null,
		  ratingStateAllTotal: null,
		  ratingStateAllListLoading: true,
		  ratingStateAllListQuery: {
		    pageNum: 1,
		    pageSize: 10,
		    callId: undefined
		  },
		  /**  查看所有录音评级  结束 */
		 
		  /**  复听详情 开始 */
		  dialogAllAssessmentVisible: false,
		  allAssessmentTableKey: 0,
		  allAssessmentList: null,
		  allAssessmentTotal: null,
		  allAssessmentListLoading: true,
		  allAssessmentListQuery: {
		    pageNum: 1,
		    pageSize: 10,
		    callId: undefined
		  },
		  /**  复听详情  结束 */
		 
		  /**  其他详情 开始 */
		  dialogOtherInfoVisible: false,
		  otherInfoTableKey: 0,
		  otherInfoList: null,
		  otherInfoListLoading: true,
		  
		  otherQsInfoTableKey: 1,
		  otherQsInfoList: null,
		  otherQsInfoListLoading: true,
		  /**  其他详情  结束 */
		 
		  //按钮权限   1：有权限， 0：无权限
      buttonRole: { 
      	queryPermission: 1, 
      	exportPermission: 0, //导出
      	playAudioVideoPermission: 0, //播放录音录像
      	downAudioVideoPermission: 0, //下载录音录像
      	notesPermission: 0, //注释
      	seeNotesPermission: 0, //查看所有注释
      	playAudioPermission: 0, //播放录音
      	downAudioPermission: 0, //下载录音
      	ratingPermission: 0, //评级
      	seeRatingPermission: 0, //查看所有评级
      	seeAssessmentPermission: 0, //查看复听详情
      	seeOtherPermission: 0 //查看其它详情
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
    //this.getList()
    this.noSearch()
    
    this.getJqList()
    this.getZwList()
  },
  mounted() {
    this.setButtonRole()
  },
  methods: {
  	noSearch() {
  		this.total=0
  		this.listLoading = false
  	},
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
//    if(!this.listQuery.jq){
//    	this.listQuery.jq = undefined
//    }else{
//    	this.listQuery.jqNo = this.listQuery.jq
//    }
//    if(!this.listQuery.frName){
//    	this.listQuery.frName = undefined
//    }
//    if(!this.listQuery.frNo){
//    	this.listQuery.frNo = undefined
//    }
//    if(!this.listQuery.qsName){
//    	this.listQuery.qsName = undefined
//    }
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
    getZwList(){ // 座位下拉框
    	if(this.zws.length === 0){
    		GetZwList({}).then(res =>{
    			let list = res.list
	    		for(let x of list){
					  let value = {}
					  value.id = x.zw
					  value.name = x.zw
					  this.zws.push(value)
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
    	if(!row.callRecfileUrl){
    		Message({
	        message: '录像文件已被删除，无法播放。',
		      type: 'error',
		      duration: 5 * 1000
	      });
    	}else{
    		this.dialogPlayVisible =true
    	  this.callRecfileUrl = row.callRecfileUrl
    	}
    	
    },
    downRecord(row){ //下载录音录像
    	var userAgent = navigator.userAgent.toLowerCase();
    	if(userAgent.indexOf("chrome")>-1 || userAgent.indexOf("firefox")>-1) { 
       	this.down1(row.callRecfileUrl).then(() => {
	    		this.down2(row.callVideofile1Url).then(() => {
	    			  this.down3(row.callVideofile2Url).then(() => {
	    			})
	    		})
	    	})
    		
      }else{ // 如果是IE浏览器
	 	    this.callRecfileUrl= row.callRecfileUrl
		    this.callVideofile1Url= row.callVideofile1Url
		    this.callVideofile2Url= row.callVideofile2Url
        this.dialogPlayDownVisible = true
      }
    },
    down1(pathUrl){
    	return new Promise((resolve, reject) => {
    		if(pathUrl=='' ){
    			Message({
		        message: '录像文件1已被删除，无法下载。',
			      type: 'error',
			      duration: 5 * 1000
		      });
    		}else{
    			// 1 录像
    			let fileName = pathUrl.substring(pathUrl.lastIndexOf("/")+1)
		    	const downloadElement = document.createElement('a')
		    	downloadElement.href = pathUrl
		    	downloadElement.download=fileName
		    	document.body.appendChild(downloadElement)
		    	downloadElement.click()
		    	document.body.removeChild(downloadElement)
    		}
	    	
      	
				resolve()
      })
    	
    },
    down2(pathUrl){
    	return new Promise((resolve, reject) => {
    		if(pathUrl=='' ){
    			Message({
		        message: '录像文件2已被删除，无法下载。',
			      type: 'error',
			      duration: 5 * 1000
		      });
    		}else{
    			// 2 录像
    			let fileName = pathUrl.substring(pathUrl.lastIndexOf("/")+1)
		    	const downloadElement = document.createElement('a')
		    	downloadElement.href = pathUrl
		    	downloadElement.download=fileName
		    	document.body.appendChild(downloadElement)
		    	downloadElement.click()
		    	document.body.removeChild(downloadElement)
    		}
	    	
      	
				resolve()
      })
    	
    		
    },
    down3(pathUrl){
    	return new Promise((resolve, reject) => {
    		if(pathUrl=='' ){
    			Message({
		        message: '录音文件已被删除，无法下载。',
			      type: 'error',
			      duration: 5 * 1000
		      });
    		}else{
    			// 3 录音
    			let fileName = pathUrl.substring(pathUrl.lastIndexOf("/")+1)
		    	const downloadElement = document.createElement('a')
		    	downloadElement.href = pathUrl
		    	downloadElement.download=fileName
		    	document.body.appendChild(downloadElement)
		    	downloadElement.click()
		    	document.body.removeChild(downloadElement)
    		}
	    	
      	
				resolve()
      })
    	
    	
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
    
    /** 录音操作 开始 */
    palyTape(row) {
    	this.callRecfileUrl = row.callRecfileUrl
    	this.dialogTapeVisible = true
    },
    closeTapeDialog(){
    	var audio1 = document.getElementById("audio1")
    	if(audio1.play){
    		audio1.currentTime = 0;
        audio1.pause();
    	}
    },
    /** 录音操作 结束 */
    
    /** 录音评级 开始 */
    resetFormRatingState() { //重置录音评级表单
			if(this.$refs['dataFormRatingState'] !== undefined){
				this.$refs['dataFormRatingState'].resetFields();
			}
			this.dataFormRatingState.webId = undefined
	  },
	  openRatingState(row){
	  	this.resetFormRatingState()
	  	let user = JSON.parse(sessionStorage.getItem("user"))
	  	let param= {
	   		callId: row.callId
	   	}
	   	GetRatingState(param).then(res => {
	   		let jlHjRecRatingInfo = res.jlHjRecRatingInfo
	   		
	   		this.dataFormRatingState.webId=row.webId
		  	this.dataFormRatingState.callId= row.callId
		  	this.dataFormRatingState.userNo= user.userNo
		  	this.dataFormRatingState.userName= user.userName
		  	this.dataFormRatingState.recRatingState= row.recRatingState
		  	this.dataFormRatingState.writeTxt= jlHjRecRatingInfo.writeTxt
	   	})
	   	this.dialogRatingStateVisible = true
	   	
	  },
	  
	  updateRatingStateData() {
	  	UpdateRatingState(this.dataFormRatingState).then(res => {
	  		this.getList()
	  	})
	  	this.dialogRatingStateVisible = false
	  },
	  /** 录音评级 结束 */
	 
	  /** 查看所有录音评级 开始 */
    openRatingStateAll(row){
    	this.ratingStateAllListQuery.callId=row.callId
    	this.getRatingStateAllList()
    	this.dialogRatingStateAllVisible= true
    },
    getRatingStateAllList(){ // 获取所有注释
    	GetRatingStateAllPojo(this.ratingStateAllListQuery).then(res => {
    		 this.ratingStateAllList = res.pojo.list
      	 this.ratingStateAllTotal = res.pojo.count
      	 this.ratingStateAllListLoading = false
      }).catch(error => {
         this.ratingStateAllListLoading = false
      })
    },
    handleRatingStateAllSizeChange(val) {
      this.ratingStateAllListQuery.pageSize = val
      this.getRatingStateAllList()
    },
    handleRatingStateAllCurrentChange(val) {
      this.ratingStateAllListQuery.pageNum = val
      this.getRatingStateAllList()
    },
    /** 查看所有录音评级 结束 */
   
    /** 复听详情 开始 */
    openAllAssessment(row){
    	this.allAssessmentListQuery.callId=row.callId
    	this.getAllAssessmentList()
    	this.dialogAllAssessmentVisible= true
    },
    getAllAssessmentList(){ // 获取所有注释
    	GetAllAssessmentPojo(this.allAssessmentListQuery).then(res => {
    		 this.allAssessmentList = res.pojo.list
      	 this.allAssessmentTotal = res.pojo.count
      	 this.allAssessmentListLoading = false
      }).catch(error => {
         this.allAssessmentListLoading = false
      })
    },
    handleAllAssessmentSizeChange(val) {
      this.allAssessmentListQuery.pageSize = val
      this.getAllAssessmentList()
    },
    handleAllAssessmentCurrentChange(val) {
      this.allAssessmentListQuery.pageNum = val
      this.getAllAssessmentList()
    },
    /** 复听详情 结束 */
   
    /** 其它详情 开始 */
    openOtherInfo(row) {
    	this.dialogOtherInfoVisible = true
    	let param ={
    		webId: row.webId
    	}
    	GetOtherInfo(param).then(res => {
    		this.otherInfoList = res.jlHjDjList
    		this.otherQsInfoList = jlHjDjQsList
    		
    	})
    	this.otherInfoListLoading = false
    	this.otherQsInfoListLoading = false
    },
    /** 其它详情 结束 */
    
    /** 导出EXCEL 开始 */
    handleDownload() {
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
//			if(!this.listQuery.frName){
//    	this.listQuery.frName = undefined
//    }
//    if(!this.listQuery.frNo){
//    	this.listQuery.frNo = undefined
//    }
//    if(!this.listQuery.jq){
//    	this.listQuery.jq = undefined
//    }

      Message({
        message: '已准备导出会见记录文件，请稍等几秒。',
	      type: 'success',
	      duration: 5 * 1000
      });
      
			exportExcel(this.listQuery).then(res => {
	      var blob = new Blob([res], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8' })
	     	if (window.navigator && window.navigator.msSaveOrOpenBlob) { // IE浏览器
        	window.navigator.msSaveOrOpenBlob(blob, '会见记录.xls');
    		}else{ //非IE浏览器
    			var downloadElement = document.createElement('a')
		     	var href = window.URL.createObjectURL(blob)
		     	downloadElement.href = href
		     	downloadElement.download = '会见记录.xls'
		     	document.body.appendChild(downloadElement)
		     	downloadElement.click()
	     		document.body.removeChild(downloadElement) // 下载完成移除元素
		     	window.URL.revokeObjectURL(href) // 释放掉blob对象
    		}
	     	
			}).catch(error => {
         console.log(error)
      })


    },
    /** 导出EXCEL 结束 */
   
    /** 设置权限 开始 */
    setButtonRole() { //设置按钮的权限
    	let roles = sessionStorage.getItem("roles")
    	if(roles.includes('admin')){
    		this.buttonRole.exportPermission= 1
      	this.buttonRole.playAudioVideoPermission= 1
      	this.buttonRole.downAudioVideoPermission= 1
      	this.buttonRole.notesPermission= 1
      	this.buttonRole.seeNotesPermission= 1
      	this.buttonRole.playAudioPermission= 1
      	this.buttonRole.downAudioPermission= 1
      	this.buttonRole.ratingPermission= 1
      	this.buttonRole.seeRatingPermission= 1
      	this.buttonRole.seeAssessmentPermission= 1
      	this.buttonRole.seeOtherPermission= 1
    	}else{
    		let buttonRoles = JSON.parse(sessionStorage.getItem("buttonRoles"))
    		let meetRecord = buttonRoles.meetRecord
    		if(meetRecord != undefined && meetRecord.length>0){
    			for(let value of meetRecord){
    				if(value=='exportPermission'){
    					this.buttonRole.exportPermission= 1
    				}else if(value=='playAudioVideoPermission'){
    					this.buttonRole.playAudioVideoPermission= 1
    				}else if(value=='downAudioVideoPermission'){
    					this.buttonRole.downAudioVideoPermission= 1
    				}else if(value=='notesPermission'){
    					this.buttonRole.notesPermission= 1
    				}else if(value=='seeNotesPermission'){
    					this.buttonRole.seeNotesPermission= 1
    				}else if(value=='playAudioPermission'){
    					this.buttonRole.playAudioPermission= 1
    				}else if(value=='downAudioPermission'){
    					this.buttonRole.downAudioPermission= 1
    				}else if(value=='ratingPermission'){
    					this.buttonRole.ratingPermission= 1
    				}else if(value=='seeRatingPermission'){
    					this.buttonRole.seeRatingPermission= 1
    				}else if(value=='seeAssessmentPermission'){
    					this.buttonRole.seeAssessmentPermission= 1
    				}else if(value=='seeOtherPermission'){
    					this.buttonRole.seeOtherPermission= 1
    				}
    			}
    		}
    	}
    },
    /** 设置权限 结束 */
   
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

<style>
.box-card {
  margin: 10px;
  }
</style>
