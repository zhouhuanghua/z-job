package cn.zhh.admin.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailSendServiceTest {

    @Autowired
    private MailSendService mailSendService;

    @Test
    public void sendSimpleMail() throws Exception {
        mailSendService.sendSimpleMail("xxx@ooo.com", "测试邮件", "hello 小周！");
    }

}