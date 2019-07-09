package cn.zhh.core.executor;

import cn.zhh.core.config.JobProperties;
import cn.zhh.core.handler.IJobHandler;
import cn.zhh.core.handler.JobInvokeRsp;
import cn.zhh.core.util.ThrowableUtils;
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
 * 任务执行器
 *
 * @author z_hh
 */
@Slf4j
public class JobExecutor {

    @Setter
    private JobProperties jobProperties;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ApplicationContext applicationContext;

    private ConcurrentHashMap<String, IJobHandler> jobHandlerRepository = new ConcurrentHashMap();

    public IJobHandler registJobHandler(String name, IJobHandler jobHandler) {
        log.info("【任务调度平台】成功注册JobHandler >>>>>>>>>> name={}，handler={}", name, jobHandler.getClass().getName());
        return (IJobHandler)jobHandlerRepository.put(name, jobHandler);
    }

    public IJobHandler loadJobHandler(String name) {
        return (IJobHandler)jobHandlerRepository.get(name);
    }

    public void init() {
        log.info("【任务调度平台】JobExecutor init...");
        // 初始化所有JobHandler
        initJobHandler();

        // 将自己注册到调度中心
        new RegisterAppToAdminThread().start();
    }

    public void destroy() {
        log.info("【任务调度平台】JobExecutor destroy...");

    }

    public JobInvokeRsp jobInvoke(String name, String params) {
        IJobHandler jobHandler = jobHandlerRepository.get(name);
        if (Objects.isNull(jobHandler)) {
            return JobInvokeRsp.error("任务不存在！");
        }

        try {
            return jobHandler.execute(params);
        } catch (Exception e) {
            log.error("【任务调度平台】任务{}调用异常：{}", name, e);
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
            log.info("【任务调度平台】开始往调度中心注册当前应用信息...");
            Map<String, Object> paramMap = new HashMap<>(4);
            paramMap.put("appName", jobProperties.getAppName());
            paramMap.put("appDesc", jobProperties.getAppDesc());
            paramMap.put("address", jobProperties.getIp() + ":" + jobProperties.getPort());
            try {
                restTemplate.postForObject("http://" + jobProperties.getAdminIp() + ":"
                                + jobProperties.getAdminPort() + "/api/job/app/auto_register",
                        paramMap,
                        Object.class);
                log.info("【任务调度平台】应用注册到调度中心成功！");
            } catch (Throwable t) {
                log.warn("【任务调度平台】应用注册到调度中心失败：{}", ThrowableUtils.getThrowableStackTrace(t));
            }
        }
    }

}
