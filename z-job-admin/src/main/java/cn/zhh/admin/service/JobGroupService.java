package cn.zhh.admin.service;

import cn.zhh.admin.entity.JobGroup;

import java.util.Optional;

/**
 * TODO
 *
 * @author Zhou Huanghua
 * @date 2019/7/3 14:49
 */
public interface JobGroupService {

    Optional<JobGroup> getById(Long id);

    JobGroup insert(JobGroup jobGroup);

    JobGroup update(JobGroup jobGroup);

    int deleteById(Long id);
}
