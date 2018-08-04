package com.personal.feng.project.fyp_user.dao;

import com.personal.feng.project.fyp_user.pojo.User;

import java.util.List;



public interface UserMapper {

    public User selectByPrimaryKey(int userId);

    public List<User> selectAllUser();

    public void insertUser(User user);

    public void deleteUser(int id);

    public List<User> findUsers(String keyWords);

    public void editUser(User user);
}