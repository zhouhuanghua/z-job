package cn.zhh.admin.service;

import cn.zhh.admin.entity.JobGroup;
import cn.zhh.admin.rsp.Result;

/**
 * TODO
 *
 * @author Zhou Huanghua
 * @date 2019/7/3 14:49
 */
public interface JobGroupService {

    Result<JobGroup> getById(Long id);

    Result<JobGroup> insert(JobGroup jobGroup);

    Result<JobGroup>  update(JobGroup jobGroup);

    Result<?> deleteById(Long id);

    void removeAddressByAppName(String appName, String address);
}
