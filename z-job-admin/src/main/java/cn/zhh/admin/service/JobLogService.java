package cn.zhh.admin.service;

import cn.zhh.admin.entity.JobLog;

import java.util.Optional;

/**
 * TODO
 *
 * @author Zhou Huanghua
 * @date 2019/7/3 14:49
 */
public interface JobLogService {

    Optional<JobLog> getById(Long id);

    JobLog insert(JobLog jobLog);

    JobLog update(JobLog jobLog);

    int deleteById(Long id);
}
