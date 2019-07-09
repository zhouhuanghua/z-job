package cn.zhh.admin.dao;

import cn.zhh.admin.entity.JobLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JobLogDAO
 *
 * @author z_hh
 */
public interface JobLogDao extends JpaRepository<JobLog, Long> {
}
