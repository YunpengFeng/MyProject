package com.personal.feng.project.fyp_user.api;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import com.personal.feng.project.fyp_user.pojo.User;
import com.personal.feng.project.fyp_user.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


/**
 * user控制器
 *
 * @author YaoQi
 */

@Controller
@RequestMapping("/UserCRUD")
public class UserController {

    @Resource
    private IUserService userService;

    /**
     * 查询所有User
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/showUser", method = RequestMethod.GET)
    public String showUsers(Model model) {
        System.out.println("**********showUsers********");
        List<User> userList = new ArrayList<User>();
        userList = userService.getAllUser();
        model.addAttribute("userList", userList); // 填充数据到model
        return "showUser";
    }

    /**
     * 增加一个用户
     *
     * @param userName
     * @param sex
     * @param age
     * @return
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap addUser(String userName, String sex, int age) {
        System.out.println("******addUser********");
        System.out.println(userName + sex + age);
        User user = new User();
        user.setsex(sex);
        user.setUserName(userName);
        user.setAge(age);
        userService.insertUser(user);
        ModelMap model = new ModelMap();
        model.addAttribute("result", "添加成功");
        return model;
    }

    /**
     * 通过userID删除用户
     *
     * @param userID
     */
    @RequestMapping(value = "/delUser/{userID}", method = RequestMethod.GET)
    public ModelAndView delUser(@PathVariable int userID) {
        System.out.println(userID);
        userService.deleteUser(userID);
        ModelAndView mv = new ModelAndView();
        List<User> userList = new ArrayList<User>();
        userList = userService.getAllUser();
        mv.addObject("userList", userList); // 填充数据到model
        mv.setViewName("showUser");
        return mv;
    }

    /**
     * 查询用户
     *
     * @param model
     * @param keyWords
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String findUsers(Model model, String keyWords) {
        System.out.println(keyWords);
        List<User> userList = new ArrayList<User>();
        userList = userService.findUsers(keyWords);
        model.addAttribute("userList", userList); // 填充数据到model
        return "showUser";
    }

    /**
     * 更新用户信息
     * @param userName
     * @param sex
     * @param age
     * @param id
     * @return
     */
    @RequestMapping(value="/editUser",method=RequestMethod.POST)
    public ModelAndView editUser(String userName, String sex, int age, int id) {
        System.out.println(userName + sex + age);
        User user = new User();
        user.setsex(sex);
        user.setUserName(userName);
        user.setAge(age);
        user.setId(id);
        userService.editUser(user);
        ModelAndView mv = new ModelAndView();
        List<User> userList = new ArrayList<User>();
        userList = userService.getAllUser();
        mv.addObject("userList", userList); // 填充数据到model
        mv.setViewName("redirect:/UserCRUD/showUser");
        return mv;
    }

    @RequestMapping(value="/tohello",method=RequestMethod.GET)
    public String tohello(Model model) {
        User user = new User();
        user.setsex("男");
        user.setUserName("冯云鹏");
        user.setAge(23);
        user.setId(12);
        model.addAttribute("user",user);
        return "hello";
    }

    @ResponseBody
    @RequestMapping(value="/synRequest",method=RequestMethod.POST)
    public List<User> synRequest(@RequestParam(value = "testa",required = false) String id
                                 ,Model model) {
        List<User> list = new ArrayList<User>();
        User user1 = new User();
        user1.setsex("男");
        user1.setUserName("冯云鹏");
        user1.setAge(23);
        user1.setId(12);
        list.add(user1);

        User user2 = new User();
        user2.setsex("女");
        user2.setUserName("劉小玉");
        user2.setAge(23);
        user2.setId(11);
        list.add(user2);
        return list;
    }


}
