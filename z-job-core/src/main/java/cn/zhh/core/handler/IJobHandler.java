package cn.zhh.core.handler;

public abstract class IJobHandler {
    public static final JobResponse<String> SUCCESS = new JobResponse(200, (String)null);
    public static final JobResponse<String> FAIL = new JobResponse(500, (String)null);
    public static final JobResponse<String> FAIL_RETRY = new JobResponse(501, (String)null);

    public IJobHandler() {}

    public abstract JobResponse<String> execute(String var1) throws Exception;

}