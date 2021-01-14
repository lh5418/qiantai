package com.jk.controller;

import com.alibaba.fastjson.JSONObject;
import com.jk.pojo.UserBean;
import com.jk.service.UserService;
import com.jk.utils.HttpClientUtil;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

/**
 * @program: qiantai
 * @description:
 * @author: 刘海
 * @create: 2021-01-13 16:08
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @Author lh 
     * @Description  登录页面
     * @Date 20:34 2021/1/13
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("toLogin")
    public String toLogin(){
        return "index";
    }
    /**
     * @Author lh 
     * @Description  快速登录页面
     * @Date 20:34 2021/1/13
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("toindex2")
    public String toindex2(){
        return "index2";
    }

    /**
     * @Author lh
     * @Description  登录
     * @Date 19:03 2021/1/13
     * @Param [userBean, session]
     * @return java.lang.String
     **/
    @RequestMapping("login")
    @ResponseBody
    public String login(UserBean userBean, HttpSession session){
        UserBean user= userService.selectByName(userBean.getName());
        if (user==null){
            return "账号错误";
        }
        if(!user.getPwd().equals(userBean.getPwd())){
            return "密码错误";
        }
        session.setAttribute("user",user);
        return "登录成功！！";
    }

    /**
     * 短信验证码
     * @param phone
     * @return
     */
    @RequestMapping("gainMsgCode")
    @ResponseBody
    public String gainMsgCode(String phone,String name){
        UserBean user = userService.findUserByName(phone);
        if(user==null){
            userService.addUser(phone,name);
        }
        String key = "msgcode"+phone;
        if(redisTemplate.hasKey(key)){
            return "短信已经发送成功！！！";
        }
        JSONObject obj = HttpClientUtil.sendMsgCode(phone);
        int code = obj.getIntValue("code");
        if(code!=200){
            return "发送短信失败,请稍后重试！！";
        }

        String msgCode = obj.getString("obj");
        redisTemplate.opsForValue().set(key, msgCode, 5, TimeUnit.DAYS);
        return "短信发送成功，请注意查收！！！";
    }

    /**
     * 登录
     * @param phone
     * @param code
     * @param
     * @return
     */
    @RequestMapping("login2")
    @ResponseBody
    public String login2(String phone, String code,HttpSession session){
        UserBean user = userService.findUserByName(phone);
        String key = "msgcode"+phone;
        if(!redisTemplate.hasKey(key)){
            return "验证码错误";
        }
        String redisCode  = (String) redisTemplate.opsForValue().get(key);
        if(!redisCode.equals(code)){
            return "验证码错误！！！";
        }
        session.setAttribute("user", user);
       // redisTemplate.delete(key);
        return "登录成功！！";
    }

}
