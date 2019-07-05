package cn.zhh.admin.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * z_job_info
 * @author zhh
 */
@Data
@Entity(name = "z_job_info")
public class JobInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    /**
     * 执行器主键ID
     */
    private Long jobGroup;

    /**
     * 任务执行CRON
     */
    private String jobCron;

    /**
     * 任务描述
     */
    private String jobDesc;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 报警邮件
     */
    private String alarmEmail;

    /**
     * 执行器路由策略：1-随机，2-轮询
     */
    private String executorRouteStrategy;

    /**
     * 执行器任务handler
     */
    private String executorHandler;

    /**
     * 执行器任务参数
     */
    private String executorParam;

    /**
     * 任务执行超时时间，单位秒
     */
    private Integer executorTimeout;

    /**
     * 失败重试次数
     */
    private Integer executorFailRetryCount;

    /**
     * 上次调度时间
     */
    private Date triggerLastTime;

    /**
     * 下次调度时间
     */
    private Date triggerNextTime;

    /**
     * 是否已删除：1-是，0-否
     */
    private Byte isDeleted;

    /**
     * 启用状态：1-启用，0-停用
     */
    private Byte enable;
}