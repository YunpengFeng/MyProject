package com.personal.feng.project.fyp_user.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.jms.Destination;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import javax.websocket.server.PathParam;

import com.personal.feng.project.active_mq.service.ProducerService;
import com.personal.feng.project.active_mq.service.TopicProducerService;
import com.personal.feng.project.fyp_user.pojo.User;
import com.personal.feng.project.fyp_user.service.IUserService;
import com.personal.feng.utils.ResultJO;
import com.personal.feng.utils.mail.Email;
import org.apache.activemq.command.ActiveMQDestination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


/**
 *
 * @author james feng
 */

@Controller
@RequestMapping("/UserCRUD")
public class UserController {

    /*邮件发送服务*/
    @Resource(name = "EmailService")
    private Email EmailService;

    private Session session;
    @Resource
    private IUserService userService;


    //queues队列
    @Resource(name = "queueDestination")
    private Destination queueDestination;

    //topic发布-订阅
    @Resource(name ="topicDestination")
    private Destination topicDestination;



    //队列消息生产者
    @Resource(name = "producerService")
    private ProducerService producerService;

    //发布-订阅消息生产者
    //@Resource(name = "topicProducerService")
    @Autowired
    private TopicProducerService topicProducerService;

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
     *
     * @param userName
     * @param sex
     * @param age
     * @param id
     * @return
     */
    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public ModelAndView editUser(String userName, String sex, int age, String id) {
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

    @RequestMapping(value = "/tohello", method = RequestMethod.GET)
    public String tohello(Model model) {
        User user = new User();
        user.setsex("男");
        user.setUserName("冯云鹏");
        user.setAge(23);
        user.setId("12");
        model.addAttribute("user", user);
        return "hello";
    }

    /*@ResponseBody
    @RequestMapping(value = "/synRequest", method = RequestMethod.POST)*/
    public List<User> synRequest(@RequestParam(value = "testa", required = false) String id
            , Model model) {
        List<User> list = new ArrayList<User>();
        User user1 = new User();
        user1.setsex("男");
        user1.setUserName("冯云鹏");
        user1.setAge(23);
        user1.setId("12");
        list.add(user1);

        User user2 = new User();
        user2.setsex("女");
        user2.setUserName("劉小玉");
        user2.setAge(23);
        user2.setId("11");
        list.add(user2);
        return list;
    }

    @ResponseBody
    @RequestMapping(value = "/showUsers", method = RequestMethod.POST)
    public List<User> showUser(Model model) {
        System.out.println("**********showUser********");
        return userService.getAllUser();
    }

    /*使用登录成功模拟，登录成功则显示在adminmanage.html页面上相关人的信息
     * 买家下单，
     * 卖家立即能看得到下单信息，并实现邮箱（或者短信）回复
     * */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, String> login(@RequestParam(value = "userid", required = false) String userid,
                                     @RequestParam(value = "pwd", required = false) String pwd,
                                     HttpSession httpsession) {
        Map<String, String> map = new HashMap<>();
        map.put("userid", userid);
        map.put("pwd", pwd);
        String flag = userService.login(map, httpsession);
        User user = (User) httpsession.getAttribute("sys_user");
        if ("success".equals(flag)) {
            try {
                /*此处可以调用业务逻辑代码，将要显示的信息推到前台
                 * 信息可以组成json的格式，前台进行处理
                 * 下一步发送邮箱
                 * */

                /*发送一个消息有个bug当管理员并没有创建一个连接时，会报nullpointexception错误*/
                AdminSocket.sendMessage("新用户登录了" + user.toString());
                String htmlTextMessage = "尊敬的" + user.getUserName() + ",你已经在本系统登录,请保管你的密码，防止被泄露！";

                ActiveMQDestination activeMQDestination = (ActiveMQDestination) queueDestination;
                producerService.sendTxtMessage(activeMQDestination.getCompositeDestinations()[1], htmlTextMessage);

                /*
                 * 已将邮件服务交给 apache的activemq消息进行处理*/
                //EmailService.sendMail("feng_profession@163.com", "尊敬的Miss Liu", htmlTextMessage);

            } catch (IOException e) {
                e.printStackTrace();
            }

            map.put("message", "success");
        } else
            map.put("message", "error");
        return map;
    }


    /*最好封装一下数据，下面的方法已经进行封装*/
    @ResponseBody
    @RequestMapping(value = "/getUserinfo", method = RequestMethod.POST)
    public User getUserinfo(HttpSession httpsession) {
        User user = (User) httpsession.getAttribute("sys_user");
        if (user == null) {
            user.setId("error");
        }
        return user;

    }

    @ResponseBody
    @RequestMapping(value = "/getalluser", method = RequestMethod.POST)
    public ResultJO getalluser(HttpSession httpsession) {
        System.out.println("**********showUser********");
        return ResultJO.backMessage(userService.getAllUser(), "success");
    }

    /*
     *使用restful风格
     * 将其变成一部分url的一部门或者携带参数
     * */
    @ResponseBody
    @RequestMapping(value = "/userinfo/{id}", method = RequestMethod.POST)
    public ResultJO getUserinfoById(@PathVariable("id") String id,
                                    @RequestParam(value = "testParam", required = false) String testParam,
                                    HttpSession httpsession) {
        System.out.println("**********使用restful风格********");

        /*在此处使用pub-sub模式进行试验*/
        ActiveMQDestination activemqTopicDestination = (ActiveMQDestination)topicDestination;
        topicProducerService.sendObjectMessage(activemqTopicDestination.getCompositeDestinations()[1], userService.getUserById(id));

        return ResultJO.backMessage(userService.getUserById(id), "success");


    }

}


