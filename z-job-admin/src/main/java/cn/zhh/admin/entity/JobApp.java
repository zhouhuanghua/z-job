package cn.zhh.admin.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 *
 * @author zhh
 * @date 20190706
 */
@Data
public class JobApp implements Serializable {
  private static final long serialVersionUID = 1L;
  
  /**
   * id
   */
  private Long id;
  
  /**
   * 应用名称
   */
  private String appName;
  
  /**
   * 应用描述
   */
  private String appDesc;
  
  /**
   * 创建人
   */
  private String creator;
  
  /**
   * 创建时间
   */
  private Date createTime;
  
  /**
   * 创建方式：1-自动，2-手工
   */
  private Byte createWay;
  
  /**
   * 最后更新时间
   */
  private Date updateTime;
  
  /**
   * 应用地址列表，多个逗号分隔
   */
  private String addressList;
  
  /**
   * 启用状态：1-启用，0-停用
   */
  private Byte enabled;
  
  /**
   * 是否删除：1-是，0-否
   */
  private Byte isDeleted;
  
}
