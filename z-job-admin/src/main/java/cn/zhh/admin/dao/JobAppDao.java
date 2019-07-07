package cn.zhh.admin.dao;

import cn.zhh.admin.entity.JobApp;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JobAppDao
 *
 * @author Zhou Huanghua
 * @date 2019/7/3 14:44
 */
public interface JobAppDao extends JpaRepository<JobApp, Long> {
}
