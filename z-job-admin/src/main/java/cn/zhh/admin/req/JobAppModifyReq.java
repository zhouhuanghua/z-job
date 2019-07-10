package cn.zhh.admin.req;

import cn.zhh.admin.base.ValidateReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 修改任务应用请求
 *
 * @author z_hh
 */
@ApiModel("修改任务应用请求")
@Data
public class JobAppModifyReq extends JobAppAddReq implements ValidateReq {

    @ApiModelProperty(value = "应用id", example = "1", required = true)
    @NotNull(message = "应用id不能为空")
    private Long id;
}
