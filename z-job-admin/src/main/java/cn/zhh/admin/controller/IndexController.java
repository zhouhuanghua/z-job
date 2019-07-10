package cn.zhh.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 首页控制器
 *
 * @author z_hh
 */
@ApiIgnore
@Controller
public class IndexController {

    /**
     * 首页跳转
     *
     * @return 跳转逻辑视图
     */
    @RequestMapping("/")
    public String index() {
        return "redirect:index.html";
    }
}
