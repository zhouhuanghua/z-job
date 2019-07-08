package cn.zhh.admin.service;

import cn.zhh.admin.entity.JobLog;
import cn.zhh.admin.rsp.Result;

import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author Zhou Huanghua
 * @date 2019/7/3 14:49
 */
public interface JobLogService {

    Result<JobLog> getById(Long id);

    Result<JobLog> insert(JobLog jobLog);

    Result<List<Map>> queryByJobId(Long jobId);
}
