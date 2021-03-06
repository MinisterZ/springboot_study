package com.cuit.springboot.controller;


import com.cuit.springboot.entities.User;

import com.cuit.springboot.mapper.UserMapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @version JDK  1.8.151
 * @Author: Mirrors
 * @Description: Chengdu City
 *
 *
 *
 * 供应商的控制层
 */

@Controller
public class UserController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserMapper userMapper;



/** 分页查询测试写的新方法**/
    @GetMapping("/getAllUser")
    public String getAllUser(Model model,@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        PageHelper.startPage(pageNum,5);
        List<User> lists = userMapper.getAllUser();
        PageInfo<User> pageInfo = new PageInfo<User>(lists);
        model.addAttribute("pageInfo",pageInfo);
        return "list";
    }



    @GetMapping("/users")
      public String list(Map<String,Object>map, User user){

          logger.info("用户列表查询"+ user);

          List<User> users = userMapper.getUsers(user);

          map.put("users", users);
          map.put("username", user.getUsername());

        //找classpath:/templates/providers/list 默认.html后缀
        return "user/list";
      }

    /**
     * type=null,进入查看详情页面view.html
     * type=update，则进入update.html
     */
      @GetMapping("/user/{id}")
      public String view(@PathVariable("id") Integer id,
                         //没传值的话默认是view查看操作，传了值是修改复用此查询
                         @RequestParam(value = "type",defaultValue = "view" )String type,
                         Map<String,Object>map){
        logger.info("查询"+id+"的供应商详细信息");
        User user =userMapper.getUserById(id);

        map.put("user",user);

        /*    return "provider/view";
        *     type=null  则进入view.html   type=update 则进入update.html      */
        return  "user/"+type;
    }

    //修改供应商信息
    @PutMapping("/user")
    public  String update(User user){
        logger.info("更改信息。。。");

        //更新
       userMapper.updateUser(user);

        return "redirect:users";
    }

    //前往添加页面
    @GetMapping("/user")
    public String toAddPage(){
          return "user/add";
    }

    //添加操作
    @PostMapping("/user")
    public String add(User user){
        logger.info("添加数据"+user);

        //添加
        userMapper.addUser(user);
        return "redirect:users";
    }

    //删除用户
    @DeleteMapping("/user/{id}" )
    public String delete(@PathVariable("id")Integer id){
          logger.info("删除操作，id="+id);
          userMapper.deleteUserById(id);
          return "redirect:/users";
    }

    @GetMapping("/user/pwd")
    public String toUpdatePwdPage(){
        return "main/password";
    }

    @ResponseBody
    @GetMapping("/user/pwd/{oldPwd}")
    public  Boolean checkPwd(HttpSession session,@PathVariable("oldPwd")String oldPwd){
          logger.info("旧密码："+oldPwd);
        //1.从session中获取当前登录用户的User对象
        User user = (User) session.getAttribute("loginUser");
        if(user.getPassword().equals(oldPwd)){
            return true;
        }
        return false;
    }

    @PostMapping("/user/pwd")
    public String updatePwd(HttpSession session,String password){

          //1.从Session中获取当前用户信息   session在logincontroller中声明了
        User user = (User) session.getAttribute("loginUser");
        //2.更新密码
        user.setPassword(password);
        //3.提交到数据库
        userMapper.updateUser(user);
        //4.注销重新登录
        return "redirect:/logout";
      }

}
