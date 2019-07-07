package cn.zhh.admin.controller;

import cn.zhh.admin.entity.JobApp;
import cn.zhh.admin.enums.CreateWayEnum;
import cn.zhh.admin.req.JobAppAddReq;
import cn.zhh.admin.req.JobAppModifyReq;
import cn.zhh.admin.rsp.Page;
import cn.zhh.admin.rsp.Result;
import cn.zhh.admin.service.JobAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Map;

/**
 * 任务组控制器
 *
 * @author z_hh
 * @date 2019/7/3
 */
@RestController
@RequestMapping("/job/group")
@Slf4j
public class JobAppController {

    @Autowired
    private JobAppService JobAppService;

    @GetMapping("/page_query")
    public Result<Page<JobApp>> pageQuery(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                          @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Result<Page<JobApp>> pageResult = JobAppService.queryByPage(pageNum, pageSize);
        return pageResult;
    }

    @PostMapping("/auto_register")
    public void autoRegister(@RequestBody Map<String, String> paramMap) {
        JobApp JobApp = new JobApp();
        JobApp.setAppName(paramMap.get("appName"));
        JobApp.setTitle(paramMap.get("appDesc"));
        JobApp.setAddressType(CreateWayEnum.AUTO.getCode());
        JobApp.setSort(Integer.valueOf(Integer.MAX_VALUE).longValue());
        JobApp.setAddressList(paramMap.get("address"));

        JobAppService.insert(JobApp);
    }

    @PostMapping(value = "/manual_add")
    public Result<JobApp> manualAdd(JobAppAddReq addReq) {
        JobApp JobApp = new JobApp();
        JobApp.setAppName(addReq.getAppName());
        JobApp.setTitle(addReq.getTitle());
        JobApp.setSort(addReq.getSort());
        JobApp.setAddressType(CreateWayEnum.MANUAL.getCode());
        JobApp.setAddressList(addReq.getAddressList());

        return JobAppService.insert(JobApp);
    }

    @PostMapping("/modify")
    public Result<JobApp>  modify(@RequestBody JobAppModifyReq modifyReq) {
        JobApp JobApp = new JobApp();
        BeanUtils.copyProperties(modifyReq, JobApp);

        return JobAppService.update(JobApp);
    }

    @DeleteMapping("/delete/{id:[\\d]}")
    public Result<?> delete(@PathParam("id") Long id) {
        return JobAppService.deleteById(id);
    }

    @PostMapping("/remove_address")
    public void removeAddress(@RequestBody Map<String, String> paramMap) {
        String appName = paramMap.get("appName");
        String address = paramMap.get("address");

        JobAppService.removeAddressByAppName(appName, address);
    }
}
