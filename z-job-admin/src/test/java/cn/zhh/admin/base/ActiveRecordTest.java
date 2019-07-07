package cn.zhh.admin.base;

import cn.zhh.admin.entity.JobApp;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ActiveRecordTest {
    @Test
    public void testSave() throws Exception {
        JobApp jobApp = new JobApp();
        jobApp.setAppName("example");
        jobApp.setAppDesc("实例应用");
        jobApp.setCreator("ZHH");
        jobApp.setCreateTime(new Date());
        jobApp.setCreateWay((byte) 0);
        jobApp.setAddressList("127.0.0.1:8080");
        jobApp.setEnabled((byte) 0);
        jobApp.setIsDeleted((byte) 0);
        jobApp = jobApp.save();
        Assert.assertNotNull(jobApp.getId());
    }

    @Test
    public void testFindAllByExample() {
        JobApp jobApp = new JobApp();
        jobApp.setId(1L);
        List<JobApp> jobAppList = jobApp.findAllByExample();
        Assert.assertFalse(jobAppList.isEmpty());
    }

    @Test
    public void findById() {
        JobApp jobApp = new JobApp();
        jobApp.setId(1L);
        Optional<JobApp> jobAppOptional = jobApp.findById();
        Assert.assertTrue(jobAppOptional.isPresent());
    }

}