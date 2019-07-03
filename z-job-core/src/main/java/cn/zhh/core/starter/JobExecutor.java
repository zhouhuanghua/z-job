package cn.zhh.core.starter;

import cn.zhh.core.handler.IJobHandler;
import cn.zhh.core.util.HttpClientUtil;
import cn.zhh.core.util.NetUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
        String ip = NetUtil.getIp();
        int availablePort = NetUtil.getAvailablePort(port);
        MonitorServer.newInstance(ip, port).start();

        // 将地址注册到调度中心
        String dataStr = new StringBuilder()
            .append("{\"").append("appName\":\"").append(appName).append("\",")
            .append("\"address\":\"").append(ip).append(":").append(availablePort).append("\"}")
            .toString();
        Map<String, String> headerMap = new HashMap<>(1);
        headerMap.put("Content-Type", "application/json");
        try {
            byte[] bytes = HttpClientUtil.postRequest(adminAddress, dataStr.getBytes(Charset.defaultCharset()), headerMap);
            String r = new String(bytes);
            if (Objects.nonNull(bytes) && bytes.length > 0) {
                String result = Objects.equals(new String(bytes, Charset.defaultCharset()), "OK") ? "成功" : "失败";
                log.info("应用注册到任务调度中心，结果：{}", result);
            }
        } catch (Exception e) {
            log.warn("应用注册到任务调度中心失败！", e);
        }
    }

    public void destroy() {

    }

    private void startMonitorServer() {

    }



}
