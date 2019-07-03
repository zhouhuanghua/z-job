package cn.zhh.admin.service;

import cn.zhh.admin.entity.JobGroup;
import cn.zhh.admin.enums.JobGroupAddressTypeEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JobGroupServiceImplTest {

    @Autowired
    private JobGroupService jobGroupService;

    @Test
    public void getById() throws Exception {
        Assert.assertTrue(jobGroupService.getById(1L).isPresent());
    }

    @Test
    public void insert() throws Exception {
        JobGroup jobGroup = new JobGroup();
        jobGroup.setAppName("z-job-demo");
        jobGroup.setTitle("demo");
        jobGroup.setAddressType(JobGroupAddressTypeEnum.MANUAL.getCode());
        jobGroup.setSort((byte)1);
        JobGroup result = jobGroupService.insert(jobGroup);
        Assert.assertNotNull(result.getId());
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void deleteById() throws Exception {
    }

}