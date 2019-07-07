package cn.zhh.core.config;

import cn.zhh.core.executor.JobExecutor;
import lombok.Setter;
import org.springframework.boot.web.servlet.ServletRegistrationBean;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * TODO
 *
 * @author z_hh
 */
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
            System.out.println(req.getParameter("name"));
        }
    }
}
