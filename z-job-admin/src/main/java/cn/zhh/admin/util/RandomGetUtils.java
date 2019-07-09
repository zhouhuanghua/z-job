package cn.zhh.admin.util;

import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机获取工具类
 *
 * @author z_hh
 */
public class RandomGetUtils {

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    public static <T> T random(List<T> elementList) {
        if (CollectionUtils.isEmpty(elementList)) {
            return null;
        }
        int nextInt = RANDOM.nextInt(elementList.size());
        return elementList.get(nextInt);
    }
}
