package cn.zhh.admin.api;

import cn.zhh.admin.rsp.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 任务日志API
 *
 * @author z_hh
 */
@Api(tags = "任务日志API")
public interface JobLogApi {

    @ApiOperation("根据任务查询日志")
    @GetMapping("/query_by_job")
    Result<?> run(@RequestParam("jobId") Long jobId);

}
