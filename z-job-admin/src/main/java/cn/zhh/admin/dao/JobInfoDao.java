package cn.zhh.admin.dao;

import cn.zhh.admin.entity.JobInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JobAppDao
 *
 * @author Zhou Huanghua
 * @date 2019/7/3 14:44
 */
public interface JobInfoDao extends JpaRepository<JobInfo, Long> {
}
