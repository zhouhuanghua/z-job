package cn.zhh.admin.controller;

import cn.zhh.admin.api.JobLogApi;
import cn.zhh.admin.rsp.Result;
import cn.zhh.admin.service.JobLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 任务日志控制器
 *
 * @author z_hh
 */
@RestController
@RequestMapping("/job/log")
@Slf4j
public class JobLogController implements JobLogApi {

    @Autowired
    private JobLogService jobLogService;


    @Override
    public Result<?> run(@RequestParam("jobId") Long jobId) {
        return jobLogService.queryByJobId(jobId);
    }
}
