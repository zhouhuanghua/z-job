package cn.zhh.admin.service;

import cn.zhh.admin.entity.JobApp;
import cn.zhh.admin.rsp.Page;
import cn.zhh.admin.rsp.Result;

/**
 * TODO
 *
 * @author Zhou Huanghua
 * @date 2019/7/3 14:49
 */
public interface JobAppService {

    Result<JobApp> getById(Long id);

    Result<JobApp> insert(JobApp JobApp);

    Result<JobApp>  update(JobApp JobApp);

    Result<?> deleteById(Long id);

    void removeAddressByAppName(String appName, String address);

    Result<Page<JobApp>> queryByPage(Integer pageNum, Integer pageSize);
}
