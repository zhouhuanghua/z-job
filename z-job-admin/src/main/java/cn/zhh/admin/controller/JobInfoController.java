package cn.zhh.admin.controller;

import cn.zhh.admin.entity.JobGroup;
import cn.zhh.admin.entity.JobInfo;
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

import javax.websocket.server.PathParam;
import java.util.Date;

@RestController
@RequestMapping("/job/info")
@Slf4j
public class JobInfoController {

    @Autowired
    private JobInfoService jobInfoService;

    @GetMapping("/page_query")
    public Result<Page<JobInfoPageQueryRsp>> pageQuery(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                       @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Result<Page<JobInfoPageQueryRsp>> pageResult = jobInfoService.queryByPage(pageNum, pageSize);
        return pageResult;
    }

    @PostMapping("/add")
    public Result<JobInfo> add(@RequestBody JobInfoAddReq addReq) {
        JobInfo jobInfo = new JobInfo();
        BeanUtils.copyProperties(addReq, jobInfo);

        jobInfo.setCreator("ZHH");
        jobInfo.setCreateTime(new Date());
        jobInfo.setIsDeleted(IsDeletedEnum.NO.getCode());

        return jobInfoService.insert(jobInfo);
    }

    @PostMapping("/modify")
    public Result<JobInfo>  modify(@RequestBody JobInfoModifyReq modifyReq) {
        JobInfo jobInfo = new JobInfo();
        BeanUtils.copyProperties(modifyReq, jobInfo);

        jobInfo.setUpdateTime(new Date());

        return jobInfoService.update(jobInfo);
    }

    @DeleteMapping("/delete/{id:[\\d]}")
    public Result<?> delete(@PathParam("id") Long id) {
        return jobInfoService.deleteById(id);
    }

}
