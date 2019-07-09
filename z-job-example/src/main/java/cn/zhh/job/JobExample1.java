package cn.zhh.job;

import cn.zhh.core.handler.IJobHandler;
import cn.zhh.core.handler.JobInvokeRsp;
import org.springframework.stereotype.Component;

/**
 * 示例任务1
 *
 * @author z_hh
 */
@Component
public class JobExample1 implements IJobHandler {

    @Override
    public JobInvokeRsp execute(String params) throws Exception {
        return JobInvokeRsp.success("我执行成功啦！收到参数：" + params);
    }
}
