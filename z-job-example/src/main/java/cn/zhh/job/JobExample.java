package cn.zhh.job;

import cn.zhh.core.handler.IJobHandler;
import cn.zhh.core.handler.JobInvokeRsp;
import org.springframework.stereotype.Component;

/**
 * 示例任务3
 *
 * @author z_hh
 */
@Component
public class JobExample implements IJobHandler {

    @Override
    public JobInvokeRsp execute(String params) throws Exception {
        return JobInvokeRsp.error("我执行异常啦！收到参数：" + params);
    }
}
