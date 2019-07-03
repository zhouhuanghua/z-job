//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.zhh.core.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Objects;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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


    public static int getAvailablePort(int defaultPort) {
        int portTmp;
        for(portTmp = defaultPort; portTmp < 65535; ++portTmp) {
            if (!isPortUsed(portTmp)) {
                return portTmp;
            }
        }

        for(portTmp = defaultPort--; portTmp > 0; --portTmp) {
            if (!isPortUsed(portTmp)) {
                return portTmp;
            }
        }

        throw new RuntimeException("no available port.");
    }

    public static boolean isPortUsed(int port) {
        boolean used = false;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            used = false;
        } catch (IOException var12) {
            log.debug("port[{}] is in use.", port);
            used = true;
        } finally {
            if (Objects.nonNull(serverSocket)) {
                try {
                    serverSocket.close();
                } catch (IOException var11) {
                    log.info(var11.getMessage(), var11);
                }
            }
        }

        return used;
    }

}
