package cn.zhh.admin.service;

import cn.zhh.admin.dao.JobGroupDao;
import cn.zhh.admin.entity.JobGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public JobGroup insert(JobGroup jobGroup) {
        if (Objects.nonNull(jobGroup.getId())) {
            jobGroup.setId(null);
        }
        // 根据appName查询数据
        JobGroup exampleObj = new JobGroup();
        exampleObj.setAppName(jobGroup.getAppName());
        Example<JobGroup> example = Example.of(exampleObj);
        Optional<JobGroup> jobGroupOptional = dao.findOne(example);

        // 如果不存在，直接保存
        if (!jobGroupOptional.isPresent()) {
            return save(jobGroup);
        }

        JobGroup existsJobGroup = jobGroupOptional.get();
        // 存在，但是地址已经包含，忽略
        if (existsJobGroup.getAddressList().contains(jobGroup.getAddressList())) {
            return existsJobGroup;
        }
        // 存在，但是地址还没包含，合并地址
        existsJobGroup.setAddressList(existsJobGroup.getAddressList() + "," + jobGroup.getAddressList());
        return save(existsJobGroup);
    }

    @Override
    @Transactional
    public JobGroup update(JobGroup jobGroup) {
        return save(jobGroup);
    }

    @Override
    @Transactional
    public int deleteById(Long id) {
        Optional<JobGroup> jobGroupOptional = dao.findById(id);
        if (jobGroupOptional.isPresent()) {
            dao.delete(jobGroupOptional.get());
            return 1;
        }
        return 0;
    }

    private JobGroup save(JobGroup jobGroup) {
        return dao.save(jobGroup);
    }
}
