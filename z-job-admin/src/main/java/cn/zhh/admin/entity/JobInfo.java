package cn.zhh.admin.entity;

import cn.zhh.admin.base.ActiveRecord;
import cn.zhh.admin.dao.JobInfoDao;
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
@Entity(name = "z_job_info")
public class JobInfo extends ActiveRecord<JobInfo, Long, JobInfoDao> implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * id
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * 任务所属应用id
   */
  private Long jobAppId;
  
  /**
   * 任务名称
   */
  private String jobName;
  
  /**
   * 任务描述
   */
  private String jobDesc;
  
  /**
   * 报警邮件，多个逗号分隔
   */
  private String alarmEmail;
  
  /**
   * 创建人
   */
  private String creator;

  /**
   * 创建时间
   */
  private Date createTime;

  /**
   * 创建方式：1-自动，2-手工
   */
  private Byte createWay;
  
  /**
   * 最后更新时间
   */
  private Date updateTime;

  /**
   * 任务执行CRON
   */
  private String runCron;
  
  /**
   * 任务执行策略：1-随机，2-轮询
   */
  private Byte runStrategy;
  
  /**
   * 任务执行参数
   */
  private String runParam;
  
  /**
   * 任务执行超时时间，单位秒
   */
  private Integer runTimeout;
  
  /**
   * 任务执行失败重试次数
   */
  private Integer runFailRetryCount;
  
  /**
   * 上次调度时间
   */
  private Date triggerLastTime;
  
  /**
   * 下次调度时间
   */
  private Date triggerNextTime;
  
  /**
   * 启用状态：1-启用，0-停用
   */
  private Byte enabled;

  /**
   * 是否删除：1-是，0-否
   */
  private Byte isDeleted;
}
