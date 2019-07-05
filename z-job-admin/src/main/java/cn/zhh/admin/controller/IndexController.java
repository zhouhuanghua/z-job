package cn.zhh.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页控制器
 *
 * @author z_hh
 * @date 2019/7/6
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "redirect:index.html";
    }
}
