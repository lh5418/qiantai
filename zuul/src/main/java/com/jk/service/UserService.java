package com.jk.service;

import com.jk.pojo.UserBean;

/**
 * @program: qiantai
 * @description:
 * @author: 刘海
 * @create: 2021-01-13 16:20
 */
public interface UserService {
    UserBean selectByName(String name);

    UserBean findUserByName(String phone);

    void addUser(String phone, String name);
}
