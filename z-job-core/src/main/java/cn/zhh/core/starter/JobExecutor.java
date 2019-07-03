package cn.zhh.core.starter;

import cn.zhh.core.annotation.JobHandler;
import cn.zhh.core.handler.IJobHandler;
import cn.zhh.core.util.NetUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO
 *
 * @author Zhou Huanghua
 * @date 2019/7/3 16:06
 */
@Slf4j
public class JobExecutor {
    @Getter @Setter
    private String adminAddress;
    @Getter @Setter
    private String appName;
    @Getter @Setter
    private Integer port;

    private ConcurrentHashMap<String, IJobHandler> jobHandlerRepository = new ConcurrentHashMap();

    public IJobHandler registJobHandler(String name, IJobHandler jobHandler) {
        log.info("注册JobHandler成功！name={}，handler={}", name, jobHandler);
        return (IJobHandler)jobHandlerRepository.put(name, jobHandler);
    }

    public IJobHandler loadJobHandler(String name) {
        return (IJobHandler)jobHandlerRepository.get(name);
    }

    public void init() {
        // 启动监听服务
        int availablePort = NetUtil.getAvailablePort(port);
        MonitorServer.newInstance(availablePort).start();
    }

    public void destroy() {

    }

    private void startMonitorServer() {

    }



}
