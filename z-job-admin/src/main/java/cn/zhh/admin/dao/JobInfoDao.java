package cn.zhh.admin.dao;

import cn.zhh.admin.entity.JobInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JobInfoDAO
 *
 * @author z_hh
 */
public interface JobInfoDao extends JpaRepository<JobInfo, Long> {
}
