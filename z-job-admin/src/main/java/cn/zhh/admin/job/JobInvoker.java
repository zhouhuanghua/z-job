package cn.zhh.admin.job;


import cn.zhh.core.handler.JobInvokeRsp;
import cn.zhh.core.starter.JobInvokeReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class JobInvoker {

    @Autowired
    private RestTemplate restTemplate;

    private static final String PATH = "/job/invoke";

    public JobInvokeRsp invoke(String url, String jobHandler, String params){
        JobInvokeReq req = new JobInvokeReq();
        req.setName(jobHandler);
        req.setParams(params);

        return restTemplate.postForObject(url + PATH, req, JobInvokeRsp.class);
    }
}
