package cn.zhh.core.handler;

/**
 * 任务处理器接口
 *
 * @author z_hh
 */
public interface IJobHandler {

    JobInvokeRsp execute(String params) throws Exception;

}