package cn.zhh.admin.service;

import cn.zhh.admin.entity.JobApp;
import cn.zhh.admin.enums.CreateWayEnum;
import cn.zhh.admin.rsp.Result;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JobAppServiceImplTest {

    @Autowired
    private JobAppService JobAppService;

    @Test
    public void getById() throws Exception {
        Assert.assertTrue(JobAppService.getById(1L).isOk());
    }

    @Test
    public void insert() throws Exception {
        JobApp JobApp = new JobApp();
        JobApp.setAppName("z-job-demo");
        JobApp.setAppDesc("demo");
        JobApp.setCreateWay(CreateWayEnum.MANUAL.getCode());
        Result<JobApp> result = JobAppService.insert(JobApp);
        Assert.assertNotNull(result.isOk());
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void deleteById() throws Exception {
    }

}