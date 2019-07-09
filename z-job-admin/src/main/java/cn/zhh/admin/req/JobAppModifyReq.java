package cn.zhh.admin.req;

import lombok.Data;

/**
 * 修改任务应用请求
 *
 * @author z_hh
 */
@Data
public class JobAppModifyReq extends JobAppAddReq {

    private Long id;
}
