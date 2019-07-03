package cn.zhh.admin.dao;

import cn.zhh.admin.entity.JobGroup;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JobGroupDao
 *
 * @author Zhou Huanghua
 * @date 2019/7/3 14:44
 */
public interface JobGroupDao extends JpaRepository<JobGroup, Long> {
}
