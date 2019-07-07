package cn.zhh.admin.service;

import cn.zhh.admin.dao.JobLogDao;
import cn.zhh.admin.entity.JobLog;
import cn.zhh.admin.rsp.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public Result<List<JobLog>> queryByJobId(Long jobId) {
        JobLog jobLog = new JobLog();
        jobLog.setJobId(jobId);
        List<JobLog> jobLogList = dao.findAll(Example.of(jobLog), Sort.by(Sort.Order.desc("triggerStartTime")));

        return Result.ok(jobLogList);
    }
}
