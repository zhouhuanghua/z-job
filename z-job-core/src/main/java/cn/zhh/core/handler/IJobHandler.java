package cn.zhh.core.handler;

public abstract class IJobHandler {

    public IJobHandler() {}

    public abstract JobInvokeRsp execute(String params) throws Exception;

}