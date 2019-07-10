package cn.zhh.admin.api;

import cn.zhh.admin.entity.JobApp;
import cn.zhh.admin.req.JobAppAddReq;
import cn.zhh.admin.req.JobAppModifyReq;
import cn.zhh.admin.rsp.Page;
import cn.zhh.admin.rsp.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 任务应用API
 *
 * @author z_hh
 */
@Api(tags = "任务应用API")
public interface JobAppApi {

    final String BASE_PATH = "/job/app";

    @ApiOperation("分页查询应用")
    @GetMapping(BASE_PATH + "/page_query")
    Result<Page<JobApp>> pageQuery(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                   @RequestParam(required = false, defaultValue = "10") Integer pageSize);

    @ApiOperation("自动注册应用")
    @PostMapping("/api/" + BASE_PATH + "/auto_register")
    void autoRegister(@RequestBody Map<String, String> paramMap);

    @ApiOperation("手动添加应用")
    @PostMapping(BASE_PATH + "/manual_add")
    Result<JobApp> manualAdd(JobAppAddReq addReq);

    @ApiOperation("修改应用")
    @PostMapping(BASE_PATH + "/modify")
    Result<JobApp>  modify(@RequestBody JobAppModifyReq modifyReq);

    @ApiOperation("查询全部应用名")
    @GetMapping(BASE_PATH + "/query_allname")
    Result<List<Map>> queryAllName();
}
