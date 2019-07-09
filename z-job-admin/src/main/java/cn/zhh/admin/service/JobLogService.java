package cn.zhh.admin.service;

import cn.zhh.admin.entity.JobLog;
import cn.zhh.admin.rsp.Result;

import java.util.List;
import java.util.Map;

/**
 * 任务日志服务接口
 *
 * @author z_hh
 */
public interface JobLogService {

    Result<JobLog> getById(Long id);

    Result<JobLog> insert(JobLog jobLog);

    Result<List<Map>> queryByJobId(Long jobId);
}
