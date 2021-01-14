package com.jk.service.impl;

import com.jk.dao.UserDao;
import com.jk.pojo.UserBean;
import com.jk.service.UserService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: qiantai
 * @description:
 * @author: 刘海
 * @create: 2021-01-13 16:21
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserBean selectByName(String name) {
        return userDao.selectByName(name);
    }

    @Override
    public UserBean findUserByName(String phone) {
        return userDao.findUserByName(phone);
    }

    @Override
    public void addUser(String phone, String name) {
        userDao.addUser(phone,name);
    }
}
