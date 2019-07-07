package cn.zhh.admin.entity;

import cn.zhh.admin.base.ActiveRecord;
import cn.zhh.admin.dao.JobLogDao;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 *
 * @author zhh
 * @date 20190706
 */
@Data
@Entity(name = "z_job_log")
public class JobLog extends ActiveRecord<JobLog, Long, JobLogDao> implements Serializable {
  private static final long serialVersionUID = 1L;
  
  /**
   * id
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  /**
   * 任务ID
   */
  private Long jobId;
  
  /**
   * 本次运行的地址
   */
  private String runAddressList;
  
  /**
   * 任务执行失败重试次数
   */
  private Integer runFailRetryCount;
  
  /**
   * 调度开始时间
   */
  private Date triggerStartTime;
  
  /**
   * 调度结束时间
   */
  private Date triggerEndTime;
  
  /**
   * 调度结果：1-成功，0-失败
   */
  private Byte triggerResult;
  
  /**
   * 调度日志
   */
  private String triggerMsg;
  
  /**
   * 任务执行结果：1-成功，0-失败
   */
  private Byte jobRunResult;
  
  /**
   * 任务执行日志
   */
  private String jobRunMsg;
  
}
