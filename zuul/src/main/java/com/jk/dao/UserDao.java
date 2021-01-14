package com.jk.dao;

import com.jk.pojo.UserBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @program: qiantai
 * @description:
 * @author: 刘海
 * @create: 2021-01-13 16:21
 */
@Repository
public interface UserDao {
    UserBean selectByName(String name);

    UserBean findUserByName(String phone);

    void addUser(@Param("phone") String phone, @Param("name") String name);
}
