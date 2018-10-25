package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlFrVO;
import com.sl.ue.entity.jl.vo.JlHjDjVO;
import com.sl.ue.entity.jl.vo.JlQsVO;
import com.sl.ue.service.jl.JlFrService;
import com.sl.ue.service.jl.JlHjDjService;
import com.sl.ue.service.jl.JlQsService;
import com.sl.ue.util.anno.IgnoreSecurity;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjDj")
public class JlHjDjWeb extends Result{

    @Autowired
    private JlHjDjService jlHjDjSQL;

    @Autowired
    private JlFrService jlFrSQL;
	
	@Autowired
    private JlQsService jlQsSQL;
	
    @RequestMapping("/findList")
    public String findList(JlHjDjVO model,Integer pageSize, Integer pageNum){
        List<JlHjDjVO> list = jlHjDjSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlHjDjVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlHjDjSQL.findPojoJoin(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlHjDjVO model){
        Integer count = jlHjDjSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjDjVO model = jlHjDjSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlHjDjVO model){
        jlHjDjSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjDjVO model){
        jlHjDjSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlHjDjSQL.deleteKey(id);
        return this.toResult();
    }

    /**
	 * 说明 [查询罪犯]
	 * L_晓天  @2018年10月6日
	 */
	@RequestMapping("/findFrPojo")
    public String findFrPojo(JlFrVO model, Integer pageSize, Integer pageNum, String qsName, String qsSfz){
        Map<String, Object> map = jlHjDjSQL.findPojoJoin(model, pageSize, pageNum, qsName, qsSfz);
        this.putPojo(map);
        return this.toResult();
    }
	
	/**
	 * 说明 [查询家属]
	 * L_晓天  @2018年10月6日
	 */
	@RequestMapping("/findQsPojo")
    public String findQsPojo(JlQsVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlQsSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }
	
	/**
	 * 说明 [提交会见登记]
	 * L_晓天  @2018年10月9日
	 */
	@RequestMapping("/addHjdj")
	public String addHjdj(
			String frNo, // 罪犯编号
			String qsIds, // 亲属id集合
			Integer hjsc, // 会见时长  单位：分钟
			String hjsm, // 会见说明
			Integer hjType, // 会见类型
			Integer callNo, //排队号
			Integer tpQsNum, //特批亲属个数
			Integer qzSp // 强制审批
			){
		return jlHjDjSQL.addHjdj(frNo, qsIds, hjsc, hjsm, hjType, callNo, tpQsNum, qzSp);
		
	}
	
	
	/**
	 * 说明 [打印小票]
	 * L_晓天  @2018年10月12日
	 */
	@RequestMapping("/printXp")
	public String printXp(Long hjid){ // 会见ID
		if(hjid == null){
			this.error(error_102);
			return this.toResult();
		}
		return jlHjDjSQL.printXp(hjid);
	}
	
	
	/**
	 * 说明 [取消登记]
	 * L_晓天  @2018年10月12日
	 */
	@RequestMapping("/cancelDj")
	public String cancelDj(Long id, String cancelInfo){ // 会见ID
		if(id == null){
			this.error(error_102);
			return this.toResult();
		}
		return jlHjDjSQL.cancelDj(id, cancelInfo);
	}
}
