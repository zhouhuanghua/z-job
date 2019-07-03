package cn.zhh.core.handler;

public abstract class IJobHandler {
    public static final ReturnT<String> SUCCESS = new ReturnT(200, (String)null);
    public static final ReturnT<String> FAIL = new ReturnT(500, (String)null);
    public static final ReturnT<String> FAIL_RETRY = new ReturnT(501, (String)null);

    public IJobHandler() {
    }

    public abstract ReturnT<String> execute(String var1) throws Exception;

}