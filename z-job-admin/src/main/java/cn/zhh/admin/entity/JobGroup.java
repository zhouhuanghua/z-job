package cn.zhh.admin.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * z_job_group
 * @author zhh
 */
@Data
@Entity(name = "z_job_group")
public class JobGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 执行器AppName
     */
    private String appName;

    /**
     * 执行器名称
     */
    private String title;

    /**
     * 排序
     */
    private Long sort;

    /**
     * 执行器地址类型：0=自动注册、1=手动录入
     */
    private Byte addressType;

    /**
     * 执行器地址列表，多地址逗号分隔
     */
    private String addressList;
}