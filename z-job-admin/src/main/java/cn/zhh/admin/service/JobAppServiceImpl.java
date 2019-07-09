package cn.zhh.admin.service;

import cn.zhh.admin.dao.JobAppDao;
import cn.zhh.admin.entity.JobApp;
import cn.zhh.admin.enums.CreateWayEnum;
import cn.zhh.admin.enums.EnabledEnum;
import cn.zhh.admin.enums.IsDeletedEnum;
import cn.zhh.admin.rsp.Page;
import cn.zhh.admin.rsp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 任务应用服务
 *
 * @author z_hh
 */
@Service
@Slf4j
public class JobAppServiceImpl implements JobAppService {

    @Autowired
    private JobAppDao dao;

    @Override
    public Result<JobApp> getById(Long id) {
        Optional<JobApp> jobAppOptional = dao.findById(id);
        if (!jobAppOptional.isPresent()) {
            return Result.err("数据不存在！");
        }
        JobApp jobApp = jobAppOptional.get();
        if (Objects.equals(jobApp.getIsDeleted(), IsDeletedEnum.YES.getCode())) {
            return Result.err("数据不存在！");
        }
        return Result.ok(jobApp);
    }

    @Override
    @Transactional
    public Result<JobApp> insert(JobApp jobApp) {
        if (Objects.nonNull(jobApp.getId())) {
            jobApp.setId(null);
        }

        // 根据appName查询数据
        JobApp exampleObj = new JobApp();
        exampleObj.setAppName(jobApp.getAppName());
        exampleObj.setIsDeleted(IsDeletedEnum.NO.getCode());
        Example<JobApp> example = Example.of(exampleObj);
        Optional<JobApp> JobAppOptional = dao.findOne(example);

        // 如果不存在，直接保存
        if (!JobAppOptional.isPresent()) {
            return Result.ok(save(jobApp));
        }

        // 如果是手动添加，提示重复
        if (Objects.equals(jobApp.getCreateWay(), CreateWayEnum.MANUAL.getCode())) {
            return Result.err("应用名称已存在！");
        }

        // 比较地址
        JobApp existsJobApp = JobAppOptional.get();
        List<String> addressList = Arrays.stream(existsJobApp.getAddressList().split(","))
                .filter(StringUtils::hasText)
                .collect(Collectors.toList());
        // 存在，但是地址已经包含，忽略
        if (addressList.contains(jobApp.getAddressList())) {
            return Result.ok(existsJobApp);
        }
        // 存在，但是地址还没包含，合并地址
        addressList.add(jobApp.getAddressList());
        String newAddressList = addressList.stream().reduce((s1, s2) -> s1 + "," + s2).orElse("");
        existsJobApp.setAddressList(newAddressList);
        return Result.ok(save(existsJobApp));
    }

    @Override
    @Transactional
    public Result<JobApp>  update(JobApp JobApp) {
        return Result.ok(save(JobApp));
    }

    @Override
    public Result<Page<JobApp>> queryByPage(Integer pageNum, Integer pageSize) {
        // 第一页是0
        org.springframework.data.domain.Page<JobApp> originalPage = dao
                .findAll(PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Order.asc("id"))));
        Page<JobApp> page = Page.parse(originalPage);
        return Result.ok(page);
    }

    @Override
    public Result<List<Map>> queryAllName() {
        JobApp jobApp = new JobApp();
        jobApp.setEnabled(EnabledEnum.YES.getCode());
        jobApp.setIsDeleted(IsDeletedEnum.NO.getCode());

        // 只需要id和name
        List<Map> mapList = jobApp.findAllByExample().stream()
                .map(ja -> {
                    Map resultMap = new HashMap(2);
                    resultMap.put("appId", ja.getId());
                    resultMap.put("appName", ja.getAppName());
                    return resultMap;
                }).collect(Collectors.toList());

        return Result.ok(mapList);
    }

    private JobApp save(JobApp JobApp) {
        return dao.save(JobApp);
    }
}
