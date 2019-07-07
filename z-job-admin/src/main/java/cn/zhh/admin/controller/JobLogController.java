package cn.zhh.admin.controller;

import cn.zhh.admin.rsp.Result;
import cn.zhh.admin.service.JobInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job/log")
@Slf4j
public class JobLogController {

    @Autowired
    private JobInfoService jobInfoService;



    @GetMapping("/run/{id:[\\d]+}")
    public Result<?> run(@PathVariable("id") Long id) {
        return jobInfoService.run(id);
    }
}
