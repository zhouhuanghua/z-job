package cn.zhh.job;

import cn.zhh.core.handler.IJobHandler;
import cn.zhh.core.handler.JobInvokeRsp;
import org.springframework.stereotype.Component;

/**
 * 示例任务2
 *
 * @author z_hh
 */
@Component
public class JobExample2 implements IJobHandler {

    @Override
    public JobInvokeRsp execute(String params) throws Exception {
        return JobInvokeRsp.success("我执行失败啦！收到参数：" + params);
    }
}
