package cn.zhh.job;

import cn.zhh.core.handler.IJobHandler;
import cn.zhh.core.handler.JobInvokeRsp;
import org.springframework.stereotype.Component;

/**
 * Demo Job
 *
 * @author z_hh
 * @date 2019/7/5
 */
@Component
public class DemoJob extends IJobHandler {

    @Override
    public JobInvokeRsp execute(String params) throws Exception {
        return JobInvokeRsp.success("我执行完成啦！");
    }
}
