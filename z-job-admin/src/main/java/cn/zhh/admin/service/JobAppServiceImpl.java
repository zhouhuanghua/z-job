package cn.zhh.admin.service;

import cn.zhh.admin.dao.JobAppDao;
import cn.zhh.admin.rsp.Page;
import cn.zhh.admin.rsp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
public class JobAppServiceImpl implements JobAppService {

    @Autowired
    private JobAppDao dao;

    @Override
    public Result<JobApp> getById(Long id) {
        Optional<JobApp> JobAppOptional = dao.findById(id);
        if (JobAppOptional.isPresent()) {
            return Result.ok(JobAppOptional.get());
        }

        return Result.err("数据不存在");
    }

    @Override
    @Transactional
    public Result<JobApp> insert(JobApp JobApp) {
        if (Objects.nonNull(JobApp.getId())) {
            JobApp.setId(null);
        }
        // 根据appName查询数据
        JobApp exampleObj = new JobApp();
        exampleObj.setAppName(JobApp.getAppName());
        Example<JobApp> example = Example.of(exampleObj);
        Optional<JobApp> JobAppOptional = dao.findOne(example);

        // 如果不存在，直接保存
        if (!JobAppOptional.isPresent()) {
            return Result.ok(save(JobApp));
        }

        JobApp existsJobApp = JobAppOptional.get();
        List<String> addressList = Arrays.stream(existsJobApp.getAddressList().split(","))
                .filter(StringUtils::hasText)
                .collect(Collectors.toList());
        // 存在，但是地址已经包含，忽略
        if (addressList.contains(JobApp.getAddressList())) {
            return Result.ok(existsJobApp);
        }
        // 存在，但是地址还没包含，合并地址
        addressList.add(JobApp.getAddressList());
        String newAddressList = addressList.stream().reduce("", (s1, s2) -> s1 + "," + s2);
        existsJobApp.setAddressList(newAddressList);
        return Result.ok(save(existsJobApp));
    }

    @Override
    @Transactional
    public Result<JobApp>  update(JobApp JobApp) {
        return Result.ok(save(JobApp));
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Result<?> deleteById(Long id) {
        Optional<JobApp> JobAppOptional = dao.findById(id);
        if (JobAppOptional.isPresent()) {
            dao.delete(JobAppOptional.get());
            return Result.ok();
        }
        return Result.err("数据不存在！");
    }

    @Override
    public void removeAddressByAppName(String appName, String address) {
        // 根据appName查询数据
        JobApp exampleObj = new JobApp();
        exampleObj.setAppName(appName);
        Example<JobApp> example = Example.of(exampleObj);
        Optional<JobApp> JobAppOptional = dao.findOne(example);

        // 移除地址
        if (JobAppOptional.isPresent()) {
            JobApp JobApp = JobAppOptional.get();
            String newAddressList = Arrays.stream(JobApp.getAddressList().split(","))
                    .filter(s -> !Objects.equals(s, address))
                    .reduce("", (s1, s2) -> s1 + "," + s2);
            if (!Objects.equals(JobApp.getAddressList(), newAddressList)) {
                JobApp.setAddressList(newAddressList);
                save(JobApp);
                log.info("应用{}移除了地址{}！", appName, address);
            }
        }
    }

    @Override
    public Result<Page<JobApp>> queryByPage(Integer pageNum, Integer pageSize) {
        // 第一页是0
        org.springframework.data.domain.Page<JobApp> originalPage = dao
                .findAll(PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Order.asc("sort"))));
        Page<JobApp> page = Page.parse(originalPage);
        return Result.ok(page);
    }

    private JobApp save(JobApp JobApp) {
        return dao.save(JobApp);
    }
}
