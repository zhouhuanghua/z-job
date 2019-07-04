package cn.zhh.admin;

import cn.zhh.admin.entity.JobGroup;
import cn.zhh.admin.enums.JobGroupAddressTypeEnum;
import cn.zhh.admin.service.JobGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/auto_register")
    public void autoRegister(@RequestBody Map<String, String> paramMap) {
        JobGroup jobGroup = new JobGroup();
        jobGroup.setAppName(paramMap.get("appName"));
        jobGroup.setTitle(paramMap.get("appDesc"));
        jobGroup.setAddressType(JobGroupAddressTypeEnum.AUTO.getCode());
        jobGroup.setAddressList(paramMap.get("address"));

        jobGroupService.insert(jobGroup);
    }
}
