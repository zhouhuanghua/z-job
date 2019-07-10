package cn.zhh.admin.api;

import cn.zhh.admin.entity.JobInfo;
import cn.zhh.admin.req.JobInfoAddReq;
import cn.zhh.admin.req.JobInfoModifyReq;
import cn.zhh.admin.rsp.JobInfoPageQueryRsp;
import cn.zhh.admin.rsp.Page;
import cn.zhh.admin.rsp.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 任务信息API
 *
 * @author z_hh
 */
@Api(tags = "任务信息API")
public interface JobInfoApi {

    @ApiOperation("分页查询任务")
    @GetMapping("/page_query")
    public Result<Page<JobInfoPageQueryRsp>> pageQuery(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                       @RequestParam(required = false, defaultValue = "10") Integer pageSize);

    @ApiOperation("手动添加任务")
    @PostMapping("/add")
    public Result<JobInfo> add(JobInfoAddReq addReq);

    @ApiOperation("修改任务")
    @PostMapping("/modify")
    public Result<JobInfo>  modify(@RequestBody JobInfoModifyReq modifyReq);

    @ApiOperation("逻辑删除任务")
    @GetMapping("/delete/{id:[\\d]+}")
    public Result<?> delete(@PathVariable("id") Long id);

    @ApiOperation("启用任务")
    @GetMapping("/enable/{id:[\\d]+}")
    public Result<?> enable(@PathVariable("id") Long id);

    @ApiOperation("停用任务")
    @GetMapping("/disable/{id:[\\d]+}")
    public Result<?> disable(@PathVariable("id") Long id);

    @ApiOperation("手动调度任务")
    @GetMapping("/run/{id:[\\d]+}")
    public Result<?> run(@PathVariable("id") Long id);
}
