package cn.zhh.admin.req;

import cn.zhh.admin.base.ValidateReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 添加任务应用请求
 *
 * @author z_hh
 */
@ApiModel("添加任务应用请求")
@Data
public class JobAppAddReq implements ValidateReq {

    @ApiModelProperty(value = "应用名称", required = true)
    @NotBlank(message = "应用名称不能为空")
    private String appName;

    @ApiModelProperty(value = "应用描述", required = true)
    @NotBlank(message = "应用描述不能为空")
    private String appDesc;

    @ApiModelProperty(value = "应用地址列表，多个逗号分隔", example = "127.0.0.1:8080,192.168.1.1:8888", required = true)
    @NotBlank(message = "应用地址列表不能为空")
    private String addressList;

    @ApiModelProperty(value = "启用状态：1-启用，0-停用", example = "1", required = true)
    @NotNull(message = "启用状态不能为空")
    private Byte enabled;
}
