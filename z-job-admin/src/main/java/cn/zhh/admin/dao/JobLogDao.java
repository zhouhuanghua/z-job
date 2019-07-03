package cn.zhh.admin.dao;

import cn.zhh.admin.entity.JobLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JobLogDao
 *
 * @author Zhou Huanghua
 * @date 2019/7/3 14:44
 */
public interface JobLogDao extends JpaRepository<JobLog, Long> {
}
