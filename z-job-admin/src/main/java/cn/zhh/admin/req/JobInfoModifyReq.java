package cn.zhh.admin.req;

import lombok.Data;

/**
 * 修改任务信息请求
 *
 * @author z_hh
 */
@Data
public class JobInfoModifyReq extends JobInfoAddReq {

    private Long id;
}
