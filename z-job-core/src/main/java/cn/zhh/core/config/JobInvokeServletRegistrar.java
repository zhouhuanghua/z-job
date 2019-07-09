package cn.zhh.core.config;

import cn.zhh.core.executor.JobExecutor;
import cn.zhh.core.handler.JobInvokeReq;
import cn.zhh.core.handler.JobInvokeRsp;
import cn.zhh.core.util.ThrowableUtils;
import javafx.util.Pair;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.util.SerializationUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Optional;

/**
 * 任务调度Servlet
 *
 * @author z_hh
 */
@Slf4j
public class JobInvokeServletRegistrar<JobInvokeServlet> extends ServletRegistrationBean {

    @Setter
    private JobExecutor jobExecutor;

    public JobInvokeServletRegistrar() {
        super();
    }

    public static JobInvokeServletRegistrar newInstance() {
        JobInvokeServletRegistrar jobInvokeServletRegistrar = new JobInvokeServletRegistrar();
        jobInvokeServletRegistrar.setServlet(jobInvokeServletRegistrar.new JobInvokeServlet());
        jobInvokeServletRegistrar.setUrlMappings(Collections.singletonList("/api/job/invoke"));
        jobInvokeServletRegistrar.setLoadOnStartup(1);

        return jobInvokeServletRegistrar;
    }

    private class JobInvokeServlet extends HttpServlet {

        @Override
        protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            resp.setHeader("Content-Type", "application/json");
            try {
                Pair reqAndRsp = run(req, resp);
                log.info("【任务调度平台】执行作业：req={}，rsp={}", reqAndRsp.getKey(), reqAndRsp.getValue());
            } catch (Throwable t) {
                log.warn("任务调用异常：{}", ThrowableUtils.getThrowableStackTrace(t));
                String result = "{\"code\":0,\"msg\":\"" + ThrowableUtils.sub3000ThrowableStackTrace(t) + "\"}";
                resp.getOutputStream().write(result.getBytes(Charset.defaultCharset()));
            }
        }

        private Pair run(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
            // 反序列化
            ServletInputStream inputStream = req.getInputStream();
            byte[] body = new byte[req.getContentLength()];
            inputStream.read(body);
            JobInvokeReq jobInvokeReq = (JobInvokeReq)SerializationUtils.deserialize(body);

            // 调用任务
            JobInvokeRsp jobInvokeRsp = JobInvokeServletRegistrar.this.jobExecutor.jobInvoke(jobInvokeReq.getName(), jobInvokeReq.getParams());

            // 响应结果
            String result = new StringBuilder().append("{\"code\":").append(Optional.ofNullable(jobInvokeRsp.getCode()).orElse(JobInvokeRsp.SUCCESS_CODE))
                    .append(",\"msg\":\"").append(jobInvokeRsp.getMsg()).append("\"}")
                    .toString();
            resp.getOutputStream().write(result.getBytes(Charset.defaultCharset()));

            // 返回请求和响应提供日志记录
            return new Pair(jobInvokeReq, jobInvokeRsp);
        }
    }
}
