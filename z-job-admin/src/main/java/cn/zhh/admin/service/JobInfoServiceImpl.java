package cn.zhh.admin.service;

import cn.zhh.admin.dao.JobInfoDao;
import cn.zhh.admin.entity.JobInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * TODO
 *
 * @author Zhou Huanghua
 * @date 2019/7/3 14:51
 */
@Service
public class JobInfoServiceImpl implements JobInfoService {

    @Autowired
    private JobInfoDao dao;

    @Override
    public Optional<JobInfo> getById(Long id) {
        return dao.findById(id);
    }

    @Override
    public JobInfo insert(JobInfo jobInfo) {
        if (Objects.nonNull(jobInfo.getId())) {
            jobInfo.setId(null);
        }
        return dao.save(jobInfo);
    }

    @Override
    public JobInfo update(JobInfo jobInfo) {
        return dao.save(jobInfo);
    }

    @Override
    public int deleteById(Long id) {
        Optional<JobInfo> jobInfoOptional = dao.findById(id);
        if (jobInfoOptional.isPresent()) {
            dao.delete(jobInfoOptional.get());
            return 1;
        }
        return 0;
    }
}
