package cn.zhh.admin.req;

import lombok.Data;

@Data
public class JobInfoAddReq {

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
     * 启用状态：1-启用，0-停用
     */
    private Byte enable;
}