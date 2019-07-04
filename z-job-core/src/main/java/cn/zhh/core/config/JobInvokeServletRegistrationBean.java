package cn.zhh.core.config;

import cn.zhh.core.handler.JobInvokeRsp;
import cn.zhh.core.starter.JobExecutor;
import lombok.Setter;
import org.springframework.boot.web.servlet.ServletRegistrationBean;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * TODO
 *
 * @author zhh
 */
public class JobInvokeServletRegistrationBean<T extends Servlet> extends ServletRegistrationBean {

    @Setter
    private JobExecutor jobExecutor;

    public JobInvokeServletRegistrationBean() {
        super();
    }

    public static JobInvokeServletRegistrationBean newInstance() {
        JobInvokeServletRegistrationBean servletRegistrationBean = new JobInvokeServletRegistrationBean();
        servletRegistrationBean.setServlet(servletRegistrationBean.new JobInvokeServlet());
        servletRegistrationBean.setUrlMappings(Collections.singletonList("/job/invoke"));
        servletRegistrationBean.setLoadOnStartup(1);

        return servletRegistrationBean;
    }

    private class JobInvokeServlet extends HttpServlet {

        @Override
        protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            String jobHandlerName = req.getParameter("name");
            String invokeParams = req.getParameter("params");

            JobInvokeRsp jobInvokeRsp = jobExecutor.jobInvoke(jobHandlerName, invokeParams);
            System.out.println(jobInvokeRsp);
        }
    }
}
