package cn.zhh.admin.service;

import cn.zhh.admin.entity.JobInfo;
import cn.zhh.admin.rsp.Result;

/**
 * TODO
 *
 * @author Zhou Huanghua
 * @date 2019/7/3 14:49
 */
public interface JobInfoService {

    Result<JobInfo> getById(Long id);

    Result<JobInfo> insert(JobInfo jobInfo);

    Result<JobInfo> update(JobInfo jobInfo);

    Result deleteById(Long id);

    Result register(JobInfo jobInfo);

    Result run(Long id);

    Result disable(Long id);
}
