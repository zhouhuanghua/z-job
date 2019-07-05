package cn.zhh.admin.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class RandomGetUtilsTest {

    @Test
    public void random() throws Exception {
        List<String> strList = Arrays.asList("h", "u", "a", "n", "g");
        IntStream.range(1, 10).forEach(i -> {
            System.out.println(RandomGetUtils.random(strList));
        });
    }

}