/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shiguo.good.dao;

import com.shiguo.good.dto.UserBean;
import java.util.List;
import shixj.test.model.User;

/**
 *
 * @author shixj
 */
public interface UserDAO {


    public void addUser(User user);

    public User getUserById(int parseInt);

    public List<User> getAllUser(UserBean bean, int cursor, int limit);

    public long getCount(UserBean bean);

    public void updateUser(User user);

    public void deleteUser(int id);
    
}
