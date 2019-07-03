package cn.zhh.admin.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class QuartzJobTest {

    @Autowired
    private Scheduler scheduler;

    public static class DemoJob implements Job {

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("context = [" + context + "]");
        }
    }

    @Test
    public void runJob() throws Exception {
        // 创建jobDetail实例，绑定Job实现类
        // 指明job的名称，所在组的名称，以及绑定job类，同时传入其他参数
        JobDetail jobDetail = JobBuilder.newJob(DemoJob.class)
                .withIdentity("demoJob", "default") // 任务名称和组构成任务key
                .withDescription("")
                .build();

        // 定义调度触发规则
        // 使用cornTrigger规则
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("demoJob", "default")// 触发器key
                .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ? ")).startNow().build();

        // 把作业和触发器注册到任务调度中
        scheduler.scheduleJob(jobDetail, trigger);

        // 启动
        if (!scheduler.isShutdown()) {
            scheduler.start();
        }

        System.in.read();
    }



}