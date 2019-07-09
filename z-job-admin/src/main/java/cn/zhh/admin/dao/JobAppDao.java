package cn.zhh.admin.dao;

import cn.zhh.admin.entity.JobApp;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JobAppDAO
 *
 * @author z_hh
 */
public interface JobAppDao extends JpaRepository<JobApp, Long> {
}
