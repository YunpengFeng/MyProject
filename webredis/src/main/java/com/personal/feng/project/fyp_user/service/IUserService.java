package com.personal.feng.project.fyp_user.service;

import com.personal.feng.project.fyp_user.pojo.User;

import java.util.List;

/**
 * user表的操作接口
 * @author James feng
 */
public interface IUserService {

    /**
     * 通过id查询user接口方法
     * @param userId
     * @return
     */
    public User getUserById(int userId);

    /**
     * 查询所有的user
     * @return 返回userList
     */
    public List<User> getAllUser();

    /**
     * 添加一个user
     * @param user
     */
    public void insertUser(User user);

    /**
     * 通过ID删除用户
     * @param id
     */
    public void deleteUser(int id);

    /**
     * 通过关键字查询用户
     * @param keyWords
     * @return
     */
    public List<User> findUsers(String keyWords);

    /**
     * 更新用户
     * @param user
     */
    public void editUser(User user);

}

