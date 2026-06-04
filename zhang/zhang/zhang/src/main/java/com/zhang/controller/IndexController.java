package com.zhang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    // 🌟 我们的“记账本”（假数据库，存放在内存里）
    private static List<String> orderList = new ArrayList<>();

    // 1. 负责【显示】首页的大堂经理
    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("dynamicData", "欢迎光临纯注解餐厅！");

        // 每次打开首页，都把记账本里的最新订单端给 FTL 页面
        model.addAttribute("orders", orderList);

        return "index";
    }

    // 2. 负责【接收】表单数据的接单员！
    // 注意：接单通常必须用 POST 方式
    @RequestMapping(value = "/placeOrder", method = RequestMethod.POST)
    public String placeOrder(String customerName, String dishName) {

        // 把客官名字和菜名拼起来，记在账本上
        String newOrder = customerName + " 点了：" + dishName;
        orderList.add(newOrder);

        // ⭐ 极其重要：重定向（Redirect）
        // 点完菜后，立刻把顾客请出大门，让他重新以 GET 方式访问首页看订单列表
        return "redirect:/";
    }
    @RequestMapping(value = "/cancelOrder")
    public String cancelOrder(String orderName) {
        // 把订单从账本里移除
        orderList.remove(orderName);
        return "redirect:/";
    }
}