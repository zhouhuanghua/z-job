package cn.zhh.admin.service;

import cn.zhh.admin.entity.JobApp;
import cn.zhh.admin.rsp.Page;
import cn.zhh.admin.rsp.Result;

import java.util.List;
import java.util.Map;

/**
 * 任务应用服务接口
 *
 * @author z_hh
 */
public interface JobAppService {

    Result<JobApp> getById(Long id);

    Result<JobApp> insert(JobApp JobApp);

    Result<JobApp> update(JobApp JobApp);

    Result<Page<JobApp>> queryByPage(Integer pageNum, Integer pageSize);

    Result<List<Map>> queryAllName();
}
