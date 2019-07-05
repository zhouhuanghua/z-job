package cn.zhh.admin.controller;

import cn.zhh.admin.entity.JobGroup;
import cn.zhh.admin.enums.JobGroupAddressTypeEnum;
import cn.zhh.admin.req.JobGroupAddReq;
import cn.zhh.admin.req.JobGroupModifyReq;
import cn.zhh.admin.rsp.Page;
import cn.zhh.admin.rsp.Result;
import cn.zhh.admin.service.JobGroupService;
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
public class JobGroupController {

    @Autowired
    private JobGroupService jobGroupService;

    @GetMapping("/page_query")
    public Result<Page<JobGroup>> pageQuery(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Result<Page<JobGroup>> pageResult = jobGroupService.queryByPage(pageNum, pageSize);
        return pageResult;
    }

    @PostMapping("/auto_register")
    public void autoRegister(@RequestBody Map<String, String> paramMap) {
        JobGroup jobGroup = new JobGroup();
        jobGroup.setAppName(paramMap.get("appName"));
        jobGroup.setTitle(paramMap.get("appDesc"));
        jobGroup.setAddressType(JobGroupAddressTypeEnum.AUTO.getCode());
        jobGroup.setSort(Integer.valueOf(Integer.MAX_VALUE).longValue());
        jobGroup.setAddressList(paramMap.get("address"));

        jobGroupService.insert(jobGroup);
    }

    @PostMapping(value = "/manual_add")
    public Result<JobGroup> manualAdd(JobGroupAddReq addReq) {
        JobGroup jobGroup = new JobGroup();
        jobGroup.setAppName(addReq.getAppName());
        jobGroup.setTitle(addReq.getTitle());
        jobGroup.setSort(addReq.getSort());
        jobGroup.setAddressType(JobGroupAddressTypeEnum.MANUAL.getCode());
        jobGroup.setAddressList(addReq.getAddressList());

        return jobGroupService.insert(jobGroup);
    }

    @PostMapping("/modify")
    public Result<JobGroup>  modify(@RequestBody JobGroupModifyReq modifyReq) {
        JobGroup jobGroup = new JobGroup();
        BeanUtils.copyProperties(modifyReq, jobGroup);

        return jobGroupService.update(jobGroup);
    }

    @DeleteMapping("/delete/{id:[\\d]}")
    public Result<?> delete(@PathParam("id") Long id) {
        return jobGroupService.deleteById(id);
    }

    @PostMapping("/remove_address")
    public void removeAddress(@RequestBody Map<String, String> paramMap) {
        String appName = paramMap.get("appName");
        String address = paramMap.get("address");

        jobGroupService.removeAddressByAppName(appName, address);
    }
}
