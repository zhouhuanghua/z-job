package cn.zhh.admin.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * z_job_log
 * @author zhh
 */
@Data
@Entity(name = "z_job_log")
public class JobLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    /**
     * 执行器主键ID
     */
    private Long jobGroup;

    /**
     * 任务，主键ID
     */
    private Long jobId;

    /**
     * 执行器地址，本次执行的地址
     */
    private String executorAddress;

    /**
     * 执行器任务handler
     */
    private String executorHandler;

    /**
     * 执行器任务参数
     */
    private String executorParam;

    /**
     * 失败重试次数
     */
    private Integer executorFailRetryCount;

    /**
     * 调度-时间
     */
    private Date triggerTime;

    /**
     * 调度-结果
     */
    private Integer triggerCode;

    /**
     * 执行-时间
     */
    private Date handleTime;

    /**
     * 执行-状态
     */
    private Integer handleCode;

    /**
     * 告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败
     */
    private Byte alarmStatus;
}