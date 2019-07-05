package cn.zhh.admin.rsp;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务分页查询响应对象
 *
 * @author z_hh
 * @date 2019/7/6
 */
@Data
public class JobInfoPageQueryRsp implements Serializable {
    private static final long serialVersionUID = -4846396014209228656L;

    private Long id;

    /**
     * 执行器名称
     */
    private String appName;

    /**
     * 任务执行CRON
     */
    private String jobCron;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 执行器任务handler
     */
    private String executorHandler;

    /**
     * 下次调度时间
     */
    private Date triggerNextTime;

    /**
     * 启用状态：1-启用，0-停用
     */
    private Byte enable;
}
