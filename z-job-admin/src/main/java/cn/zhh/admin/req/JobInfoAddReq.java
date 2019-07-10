package cn.zhh.admin.req;

import cn.zhh.admin.base.ValidateReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 添加任务信息请求
 *
 * @author z_hh
 */
@ApiModel("添加任务信息请求")
@Data
public class JobInfoAddReq implements ValidateReq {

    @ApiModelProperty(value = "任务所属应用id", example = "1", required = true)
    @NotNull(message = "任务所属应用id不能为空")
    private Long jobAppId;

    @ApiModelProperty(value = "任务名称", example = "jobExample", required = true)
    @NotBlank(message = "任务名称不能为空")
    private String jobName;

    @ApiModelProperty(value = "任务描述", example = "示例任务", required = true)
    @NotBlank(message = "任务描述不能为空")
    private String jobDesc;

    @ApiModelProperty(value = "报警邮件，多个逗号分隔", example = "xxx@000.com,ooo@xxx.com", required = true)
    @NotBlank(message = "报警邮件不能为空")
    private String alarmEmail;

    @ApiModelProperty(value = "任务执行CRON", example = "0 0/5 * * * ?", required = true)
    @NotBlank(message = "任务执行CRON不能为空")
    private String runCron;

    @ApiModelProperty(value = "任务执行策略：1-随机，2-轮询", example = "1", required = true)
    @NotNull(message = "任务执行策略不能为空")
    private Byte runStrategy;

    @ApiModelProperty(value = "任务执行参数", example = "xxxooo", required = true)
    @NotBlank(message = "任务执行参数不能为空")
    private String runParam;

    @ApiModelProperty(value = "任务执行超时时间，单位秒", example = "60", required = true)
    @NotNull(message = "任务执行超时时间不能为空")
    private Integer runTimeout;

    @ApiModelProperty(value = "任务执行失败重试次数", example = "3", required = true)
    @NotNull(message = "任务执行失败重试次数不能为空")
    private Integer runFailRetryCount;

    @ApiModelProperty(value = "启用状态：1-启用，0-停用", example = "1", required = true)
    @NotNull(message = "启用状态不能为空")
    private Byte enabled;
}