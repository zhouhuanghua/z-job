package cn.zhh.admin.req;

import cn.zhh.admin.base.ValidateReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 修改任务信息请求
 *
 * @author z_hh
 */
@ApiModel("修改任务信息请求")
@Data
public class JobInfoModifyReq extends JobInfoAddReq implements ValidateReq {

    @ApiModelProperty(value = "任务id", example = "2", required = true)
    @NotNull(message = "任务id不能为空")
    private Long id;
}
