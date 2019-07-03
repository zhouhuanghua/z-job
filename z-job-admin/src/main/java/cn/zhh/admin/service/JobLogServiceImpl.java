package cn.zhh.admin.service;

import cn.zhh.admin.dao.JobLogDao;
import cn.zhh.admin.entity.JobLog;
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
public class JobLogServiceImpl implements JobLogService {

    @Autowired
    private JobLogDao dao;

    @Override
    public Optional<JobLog> getById(Long id) {
        return dao.findById(id);
    }

    @Override
    public JobLog insert(JobLog jobLog) {
        if (Objects.nonNull(jobLog.getId())) {
            jobLog.setId(null);
        }
        return dao.save(jobLog);
    }

    @Override
    public JobLog update(JobLog jobLog) {
        return dao.save(jobLog);
    }

    @Override
    public int deleteById(Long id) {
        Optional<JobLog> jobLogOptional = dao.findById(id);
        if (jobLogOptional.isPresent()) {
            dao.delete(jobLogOptional.get());
            return 1;
        }
        return 0;
    }
}
