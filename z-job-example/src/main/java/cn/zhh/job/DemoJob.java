package cn.zhh.job;

import cn.zhh.core.handler.IJobHandler;
import cn.zhh.core.handler.JobInvokeRsp;
import org.springframework.stereotype.Component;

/**
 * Demo Job
 *
 * @author z_hh
 */
@Component
public class DemoJob implements IJobHandler {

    @Override
    public JobInvokeRsp execute(String params) throws Exception {
        return JobInvokeRsp.success("我执行完成啦！");
    }
}
