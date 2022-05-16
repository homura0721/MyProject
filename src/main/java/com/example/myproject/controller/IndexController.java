package com.example.myproject.controller;

import com.example.myproject.util.*;
import com.example.myproject.model.User;
import com.example.myproject.service.imp.UserServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class IndexController {
    @Resource
    private UserServiceImp userServiceImp;
    @Resource
    private RedisUtils redisUtils;

    @GetMapping("/")
    public ModelAndView login(){
        return new ModelAndView("login");
    }

    @PostMapping("/doLogin")
    @ResponseBody
    public Result<String> doLogin(@RequestBody User user) {
        int id = userServiceImp.login(user);
        if (id == -1) {
            return Result.fail(RequestCode.FAIL.getCode(),"当前账号不存在！");
        }
        String token = TokenUtil.sign(id + "");
        return Result.success(token);
    }

    @PostMapping("/doRegister")
    @ResponseBody
    public Result<String> doRegister(@RequestBody User user) {
        int code = userServiceImp.register(user);
        if (code == 200) {
            return Result.success("注册成功");
        } else {
            return Result.fail(RequestCode.FAIL.getCode(), "注册失败，账号已存在！");
        }
    }


    @PostMapping("/doLogout")
    @ResponseBody
    public Result<String> doLogout(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            redisUtils.del(token);
            return Result.success("退出登录！");
        } catch (Exception e){
            e.printStackTrace();
            return Result.fail(RequestCode.FAIL.getCode(), "失败");
        }
    }

    /**
     * 生成验证码
     */
    @GetMapping(value = "/getVerify/{random}")
    public void getVerify(@PathVariable String random,  HttpServletRequest request,HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            VerifyUtil randomValidateCode = new VerifyUtil();
            randomValidateCode.getVerifyImage(request, response);//输出验证码图片方法
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/checkVerify")
    @ResponseBody
    public boolean checkVerify(@RequestBody Map<String, Object> requestMap, HttpSession session) {
        try{
            //从session中获取随机数
            String inputStr = requestMap.get("inputStr").toString();
            String random = (String) session.getAttribute("RANDOMVALIDATECODEKEY");
            if (random == null) {
                return false;
            }
            if (random.equalsIgnoreCase(inputStr)) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            System.out.println("验证码校验失败" + e);
            return false;
        }
    }

    @GetMapping("/test")
    @ResponseBody
    public String test(HttpServletRequest request) {
        System.out.println("test接口");
        String token = request.getHeader("Authorization");
        System.out.println(TokenUtil.verify(token));
        redisUtils.del("test");
        return "测试";
    }
}
