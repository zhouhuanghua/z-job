package cn.zhh.admin.rsp;

import org.junit.Test;

public class ResultTest {
    @Test
    public void ok() throws Exception {
        Result result = Result.ok();
        System.out.println(result);
    }
}