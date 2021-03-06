package cn.zhh.admin.controller;

import cn.zhh.admin.api.JobInfoApi;
import cn.zhh.admin.entity.JobInfo;
import cn.zhh.admin.enums.CreateWayEnum;
import cn.zhh.admin.enums.IsDeletedEnum;
import cn.zhh.admin.req.JobInfoAddReq;
import cn.zhh.admin.req.JobInfoModifyReq;
import cn.zhh.admin.rsp.JobInfoPageQueryRsp;
import cn.zhh.admin.rsp.Page;
import cn.zhh.admin.rsp.Result;
import cn.zhh.admin.service.JobInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 任务信息控制器
 *
 * @author z_hh
 */
@RestController
@RequestMapping("/job/info")
@Slf4j
public class JobInfoController implements JobInfoApi {

    @Autowired
    private JobInfoService jobInfoService;

    @Override
    public Result<Page<JobInfoPageQueryRsp>> pageQuery(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                       @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Result<Page<JobInfoPageQueryRsp>> pageResult = jobInfoService.queryByPage(pageNum, pageSize);
        return pageResult;
    }

    @Override
    public Result<JobInfo> add(JobInfoAddReq addReq) {
        JobInfo jobInfo = new JobInfo();
        BeanUtils.copyProperties(addReq, jobInfo);

        jobInfo.setCreator("ZHH");
        jobInfo.setCreateTime(new Date());
        jobInfo.setCreateWay(CreateWayEnum.MANUAL.getCode());
        jobInfo.setIsDeleted(IsDeletedEnum.NO.getCode());

        return jobInfoService.insert(jobInfo);
    }

    @Override
    public Result<JobInfo>  modify(@RequestBody JobInfoModifyReq modifyReq) {
        JobInfo jobInfo = new JobInfo();
        BeanUtils.copyProperties(modifyReq, jobInfo);

        jobInfo.setUpdateTime(new Date());

        return jobInfoService.update(jobInfo);
    }

    @Override
    public Result<?> delete(@PathVariable("id") Long id) {
        return jobInfoService.deleteById(id);
    }

    @Override
    public Result<?> enable(@PathVariable("id") Long id) {
        return jobInfoService.enable(id);
    }

    @Override
    public Result<?> disable(@PathVariable("id") Long id) {
        return jobInfoService.disable(id);
    }

    @Override
    public Result<?> run(@PathVariable("id") Long id) {
        return jobInfoService.run(id);
    }
}
