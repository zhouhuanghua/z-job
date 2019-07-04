//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.zhh.core.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * TODO
 *
 * @author z_hh
 */
@Slf4j
public class NetUtil {

    private NetUtil() {}

    public static String getIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("获取IP地址异常！");
        }
    }

}
