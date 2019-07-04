package cn.zhh.core.handler;

/**
 * TODO
 *
 * @author zhh
 */
public abstract class IJobHandler {

    public IJobHandler() {}

    public abstract JobInvokeRsp execute(String params) throws Exception;

}