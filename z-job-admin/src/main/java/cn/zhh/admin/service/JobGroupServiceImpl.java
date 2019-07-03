package cn.zhh.admin.service;

import cn.zhh.admin.dao.JobGroupDao;
import cn.zhh.admin.entity.JobGroup;
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
public class JobGroupServiceImpl implements JobGroupService {

    @Autowired
    private JobGroupDao dao;

    @Override
    public Optional<JobGroup> getById(Long id) {
        return dao.findById(id);
    }

    @Override
    public JobGroup insert(JobGroup jobGroup) {
        if (Objects.nonNull(jobGroup.getId())) {
            jobGroup.setId(null);
        }
        return dao.save(jobGroup);
    }

    @Override
    public JobGroup update(JobGroup jobGroup) {
        return dao.save(jobGroup);
    }

    @Override
    public int deleteById(Long id) {
        Optional<JobGroup> jobGroupOptional = dao.findById(id);
        if (jobGroupOptional.isPresent()) {
            dao.delete(jobGroupOptional.get());
            return 1;
        }
        return 0;
    }
}
