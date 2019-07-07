package cn.zhh.admin.rsp;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 任务分页查询响应对象
 *
 * @author z_hh
 * @date 2019/7/6
 */
@Data
public class JobInfoPageQueryRsp implements Serializable {
    private static final long serialVersionUID = -4846396014209228656L;

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 任务所属应用名称
     */
    private String jobAppName;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务描述
     */
    private String jobDesc;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private String createTime;

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
     * 下次调度时间
     */
    private String triggerNextTime;

    /**
     * 启用状态：1-启用，0-停用
     */
    private Byte enabled;
}
