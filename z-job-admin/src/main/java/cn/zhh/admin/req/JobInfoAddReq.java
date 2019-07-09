package cn.zhh.admin.req;

import lombok.Data;

/**
 * 添加任务信息请求
 *
 * @author z_hh
 */
@Data
public class JobInfoAddReq {

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
     * 启用状态：1-启用，0-停用
     */
    private Byte enabled;
}