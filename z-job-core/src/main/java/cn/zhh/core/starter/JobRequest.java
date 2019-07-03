package cn.zhh.core.starter;

import lombok.Data;

import java.io.Serializable;

/**
 * 任务请求
 *
 * @author z_hh
 * @date 2019/7/3
 */
@Data
public class JobRequest implements Serializable {

    private String name;

    private String params;
}
