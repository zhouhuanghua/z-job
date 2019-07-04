package cn.zhh.core.starter;

import cn.zhh.core.config.JobConfig;
import cn.zhh.core.handler.IJobHandler;
import cn.zhh.core.handler.JobInvokeRsp;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO
 *
 * @author zhh
 */
@Slf4j
public class JobExecutor {

    @Setter
    private JobConfig jobConfig;

    @Setter
    private RestTemplate restTemplate;

    @Autowired
    private ApplicationContext applicationContext;

    private ConcurrentHashMap<String, IJobHandler> jobHandlerRepository = new ConcurrentHashMap();

    public IJobHandler registJobHandler(String name, IJobHandler jobHandler) {
        log.info("成功注册JobHandler >>>>>>>>>> name={}，handler={}", name, jobHandler.getClass().getName());
        return (IJobHandler)jobHandlerRepository.put(name, jobHandler);
    }

    public IJobHandler loadJobHandler(String name) {
        return (IJobHandler)jobHandlerRepository.get(name);
    }

    public void init() {
        // 初始化所有JobHandler
        initJobHandler();

        // 将自己注册到调度中心
        new RegisterAppToAdminThread().start();
    }

    public void destroy() {
        // 将自己从注册中心注销
        // TODO
    }

    public JobInvokeRsp jobInvoke(String name, String params) {
        IJobHandler jobHandler = jobHandlerRepository.get(name);
        if (Objects.isNull(jobHandler)) {
            return JobInvokeRsp.error("任务不存在！");
        }

        try {
            return jobHandler.execute(params);
        } catch (Exception e) {
            log.error("任务{}调用异常：{}", name, e);
            return JobInvokeRsp.error("任务调用异常！");
        }
    }

    private void initJobHandler() {
        String[] beanNames = applicationContext.getBeanNamesForType(IJobHandler.class);
        if (beanNames == null || beanNames.length == 0) {
            return;
        }
        Arrays.stream(beanNames).forEach(beanName -> {
            registJobHandler(beanName, (IJobHandler)applicationContext.getBean(beanName));
        });
    }

    private class RegisterAppToAdminThread extends Thread {

        private  RegisterAppToAdminThread() {
            super("AppToAdmin-T");
        }

        @Override
        public void run() {
            log.info("开始往调度中心注册...");
            Map<String, Object> paramMap = new HashMap<>(4);
            paramMap.put("appName", jobConfig.getAppName());
            paramMap.put("appDesc", jobConfig.getAppDesc());
            paramMap.put("address", jobConfig.getIp() + ":" + jobConfig.getPort());
            try {
                restTemplate.postForObject("http://" + jobConfig.getAdminIp() + ":"
                                + jobConfig.getAdminPort() + "/job/group/auto_register",
                        paramMap,
                        Object.class);
                log.info("应用注册到调度中心成功！");
            } catch (Throwable t) {
                log.info("应用注册到调度中心失败：{}", t.getMessage(), t);
            }
        }
    }

}
