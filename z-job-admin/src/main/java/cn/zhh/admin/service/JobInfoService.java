package cn.zhh.admin.service;

import cn.zhh.admin.entity.JobInfo;

import java.util.Optional;

/**
 * TODO
 *
 * @author Zhou Huanghua
 * @date 2019/7/3 14:49
 */
public interface JobInfoService {

    Optional<JobInfo> getById(Long id);

    JobInfo insert(JobInfo jobInfo);

    JobInfo update(JobInfo jobInfo);

    int deleteById(Long id);
}
