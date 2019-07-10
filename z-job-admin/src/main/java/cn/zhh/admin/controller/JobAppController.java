package cn.zhh.admin.controller;

import cn.zhh.admin.api.JobAppApi;
import cn.zhh.admin.entity.JobApp;
import cn.zhh.admin.enums.CreateWayEnum;
import cn.zhh.admin.enums.EnabledEnum;
import cn.zhh.admin.enums.IsDeletedEnum;
import cn.zhh.admin.req.JobAppAddReq;
import cn.zhh.admin.req.JobAppModifyReq;
import cn.zhh.admin.rsp.Page;
import cn.zhh.admin.rsp.Result;
import cn.zhh.admin.service.JobAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 任务应用控制器
 *
 * @author z_hh
 */
@RestController
@Slf4j
public class JobAppController implements JobAppApi{

    @Autowired
    private JobAppService JobAppService;

    /** 自动注册锁，保证并发安全 */
    private final Lock autoRegisterLock = new ReentrantLock(true);

    @Override
    public Result<Page<JobApp>> pageQuery(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                          @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Result<Page<JobApp>> pageResult = JobAppService.queryByPage(pageNum, pageSize);
        return pageResult;
    }

    @Override
    public void autoRegister(@RequestBody Map<String, String> paramMap) {
        JobApp jobApp = new JobApp();
        // 设置相关属性
        jobApp.setAppName(paramMap.get("appName"));
        jobApp.setAppDesc(paramMap.get("appDesc"));
        jobApp.setCreator("SYSTEM");
        jobApp.setCreateTime(new Date());
        jobApp.setCreateWay(CreateWayEnum.AUTO.getCode());
        jobApp.setAddressList(paramMap.get("address"));
        jobApp.setEnabled(EnabledEnum.YES.getCode());
        jobApp.setIsDeleted(IsDeletedEnum.NO.getCode());
        // 加锁
        autoRegisterLock.lock();
        try {
            JobAppService.insert(jobApp);
        } finally {
            autoRegisterLock.unlock();
        }
    }

    @Override
    public Result<JobApp> manualAdd(JobAppAddReq addReq) {
        JobApp jobApp = new JobApp();
        // 设置相关属性
        jobApp.setAppName(addReq.getAppName());
        jobApp.setAppDesc(addReq.getAppDesc());
        jobApp.setCreator("ZHOU");
        jobApp.setCreateTime(new Date());
        jobApp.setCreateWay(CreateWayEnum.MANUAL.getCode());
        jobApp.setAddressList(addReq.getAddressList());
        jobApp.setEnabled(addReq.getEnabled());
        jobApp.setIsDeleted(IsDeletedEnum.NO.getCode());

        return JobAppService.insert(jobApp);
    }

    @Override
    public Result<JobApp>  modify(@RequestBody JobAppModifyReq modifyReq) {
        JobApp JobApp = new JobApp();
        BeanUtils.copyProperties(modifyReq, JobApp);

        return JobAppService.update(JobApp);
    }

    @Override
    public Result<List<Map>> queryAllName() {
        return JobAppService.queryAllName();
    }
}
