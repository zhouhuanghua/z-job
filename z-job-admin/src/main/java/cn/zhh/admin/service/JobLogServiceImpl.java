package cn.zhh.admin.service;

import cn.zhh.admin.dao.JobLogDao;
import cn.zhh.admin.entity.JobLog;
import cn.zhh.admin.rsp.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author Zhou Huanghua
 * @date 2019/7/3 14:51
 */
@Service
public class JobLogServiceImpl implements JobLogService {

    @Autowired
    private JobLogDao dao;

    @Autowired
    private JobInfoService jobInfoService;

    @Override
    public Result<JobLog> getById(Long id) {
        Optional<JobLog> jobLogOptional = dao.findById(id);
        return jobLogOptional.isPresent() ? Result.ok(jobLogOptional.get()) : Result.err("数据不存在！");
    }

    @Override
    public Result<JobLog> insert(JobLog jobLog) {
        if (Objects.nonNull(jobLog.getId())) {
            jobLog.setId(null);
        }
        return Result.ok(dao.save(jobLog));
    }

    @Override
    public Result<List<Map>> queryByJobId(Long jobId) {
        JobLog jobLogExample = new JobLog();
        jobLogExample.setJobId(jobId);
        // 只查10条
        Page<JobLog> jobLogPage = dao.findAll(Example.of(jobLogExample),
                PageRequest.of(0, 10, Sort.by(Sort.Order.desc("triggerStartTime"))));
        List<Map> logMapList = Collections.emptyList();
        if (jobLogPage.hasContent()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            logMapList = jobLogPage.getContent().stream()
                    .map((JobLog log) -> {
                        Map logMap = new HashMap(6);
                        logMap.put("triggerStartTime", dateFormat.format(log.getTriggerStartTime()));
                        logMap.put("triggerEndTime", dateFormat.format(log.getTriggerEndTime()));
                        Byte triggerResult = log.getTriggerResult();
                        logMap.put("triggerResult", Objects.equals(triggerResult, (byte)1) ? "成功" : "失败");
                        Byte jobRunResult = log.getJobRunResult();
                        logMap.put("jobRunResult", Objects.equals(jobRunResult, (byte)1) ? "成功" : "失败");
                        logMap.put("runFailRetryCount", log.getRunFailRetryCount());
                        logMap.put("runAddressList", log.getRunAddressList());
                        return logMap;
                    }).collect(Collectors.toList());
        }

        return Result.ok(logMapList);
    }
}
