package cn.zhh.admin.job;


import cn.zhh.core.handler.JobInvokeReq;
import cn.zhh.core.handler.JobInvokeRsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;
import org.springframework.web.client.RestTemplate;

/**
 * 任务调度器
 *
 * @author z_hh
 */
@Component
public class JobInvoker {

    @Autowired
    private RestTemplate restTemplate;

    private static final String PREFIX = "http://";

    private static final String PATH = "/api/job/invoke";

    /**
     * 任务调度器
     *
     * @param url 目标地址
     * @param jobHandler 任务名称
     * @param params 执行参数
     * @return 调用任务结果
     */
    public JobInvokeRsp invoke(String url, String jobHandler, String params) {
        JobInvokeReq req = new JobInvokeReq();
        req.setName(jobHandler);
        req.setParams(params);

        byte[] dataBytes = SerializationUtils.serialize(req);
        return restTemplate.postForObject(PREFIX + url + PATH, dataBytes, JobInvokeRsp.class);
    }
}
