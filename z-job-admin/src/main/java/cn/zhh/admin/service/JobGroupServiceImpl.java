package cn.zhh.admin.service;

import cn.zhh.admin.dao.JobGroupDao;
import cn.zhh.admin.entity.JobGroup;
import cn.zhh.admin.rsp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author Zhou Huanghua
 * @date 2019/7/3 14:51
 */
@Service
@Slf4j
public class JobGroupServiceImpl implements JobGroupService {

    @Autowired
    private JobGroupDao dao;

    @Override
    public Result<JobGroup> getById(Long id) {
        Optional<JobGroup> jobGroupOptional = dao.findById(id);
        if (jobGroupOptional.isPresent()) {
            return Result.ok(jobGroupOptional.get());
        }

        return Result.err("数据不存在");
    }

    @Override
    @Transactional
    public Result<JobGroup> insert(JobGroup jobGroup) {
        if (Objects.nonNull(jobGroup.getId())) {
            jobGroup.setId(null);
        }
        // 根据appName查询数据
        JobGroup exampleObj = new JobGroup();
        exampleObj.setAppName(jobGroup.getAppName());
        Example<JobGroup> example = Example.of(exampleObj);
        Optional<JobGroup> jobGroupOptional = dao.findOne(example);

        // 如果不存在，直接保存
        if (!jobGroupOptional.isPresent()) {
            return Result.ok(save(jobGroup));
        }

        JobGroup existsJobGroup = jobGroupOptional.get();
        List<String> addressList = Arrays.stream(existsJobGroup.getAddressList().split(","))
                .filter(StringUtils::hasText)
                .collect(Collectors.toList());
        // 存在，但是地址已经包含，忽略
        if (addressList.contains(jobGroup.getAddressList())) {
            return Result.ok(existsJobGroup);
        }
        // 存在，但是地址还没包含，合并地址
        addressList.add(jobGroup.getAddressList());
        String newAddressList = addressList.stream().reduce("", (s1, s2) -> s1 + "," + s2);
        existsJobGroup.setAddressList(newAddressList);
        return Result.ok(save(existsJobGroup));
    }

    @Override
    @Transactional
    public Result<JobGroup>  update(JobGroup jobGroup) {
        return Result.ok(save(jobGroup));
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Result<?> deleteById(Long id) {
        Optional<JobGroup> jobGroupOptional = dao.findById(id);
        if (jobGroupOptional.isPresent()) {
            dao.delete(jobGroupOptional.get());
            return Result.ok();
        }
        return Result.err("数据不存在！");
    }

    @Override
    public void removeAddressByAppName(String appName, String address) {
        // 根据appName查询数据
        JobGroup exampleObj = new JobGroup();
        exampleObj.setAppName(appName);
        Example<JobGroup> example = Example.of(exampleObj);
        Optional<JobGroup> jobGroupOptional = dao.findOne(example);

        // 移除地址
        if (jobGroupOptional.isPresent()) {
            JobGroup jobGroup = jobGroupOptional.get();
            String newAddressList = Arrays.stream(jobGroup.getAddressList().split(","))
                    .filter(s -> !Objects.equals(s, address))
                    .reduce("", (s1, s2) -> s1 + "," + s2);
            if (!Objects.equals(jobGroup.getAddressList(), newAddressList)) {
                jobGroup.setAddressList(newAddressList);
                save(jobGroup);
                log.info("应用{}移除了地址{}！", appName, address);
            }
        }
    }

    private JobGroup save(JobGroup jobGroup) {
        return dao.save(jobGroup);
    }
}
