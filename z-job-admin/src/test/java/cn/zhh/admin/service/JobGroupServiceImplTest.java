package cn.zhh.admin.service;

import cn.zhh.admin.entity.JobGroup;
import cn.zhh.admin.enums.JobGroupAddressTypeEnum;
import cn.zhh.admin.rsp.Result;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JobGroupServiceImplTest {

    @Autowired
    private JobGroupService jobGroupService;

    @Test
    public void getById() throws Exception {
        Assert.assertTrue(jobGroupService.getById(1L).isOk());
    }

    @Test
    public void insert() throws Exception {
        JobGroup jobGroup = new JobGroup();
        jobGroup.setAppName("z-job-demo");
        jobGroup.setTitle("demo");
        jobGroup.setAddressType(JobGroupAddressTypeEnum.MANUAL.getCode());
        jobGroup.setSort(1L);
        Result<JobGroup> result = jobGroupService.insert(jobGroup);
        Assert.assertNotNull(result.isOk());
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void deleteById() throws Exception {
    }

}