package cn.zhh.admin.service;

import cn.zhh.admin.entity.JobInfo;
import cn.zhh.admin.rsp.JobInfoPageQueryRsp;
import cn.zhh.admin.rsp.Page;
import cn.zhh.admin.rsp.Result;

import java.util.List;

/**
 * 任务信息服务接口
 *
 * @author z_hh
 */
public interface JobInfoService {

    Result<JobInfo> getById(Long id);

    Result<JobInfo> insert(JobInfo jobInfo);

    Result<JobInfo> update(JobInfo jobInfo);

    Result deleteById(Long id);

    Result register(JobInfo jobInfo);

    Result run(Long id);

    Result disable(Long id);

    Result enable(Long id);

    Result<Page<JobInfoPageQueryRsp>> queryByPage(Integer pageNum, Integer pageSize);

    List<JobInfo> queryAll();
}
