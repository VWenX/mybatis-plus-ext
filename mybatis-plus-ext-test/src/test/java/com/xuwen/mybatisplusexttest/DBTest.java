package com.xuwen.mybatisplusexttest;


import com.xuwen.mybatisplusext.tooltik.PageQueryHelper;
import com.xuwen.mybatisplusexttest.entity.User;
import com.xuwen.mybatisplusexttest.mapper.UserMapper;
import com.xuwen.mybatisplusexttest.param.UserPageParam;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class DBTest extends MybatisPlusExtTestApplicationTests {

    @Resource
    UserMapper userMapper;


    @Test
    public void insert(){

        User[] users = {
                User.builder().name("张三").age(10).build(),
                User.builder().name("李四").age(16).build(),
                User.builder().name("王五").age(18).build(),
                User.builder().name("陈柳").age(3).build(),
        };
        int batchInsert = userMapper.batchInsert(Arrays.asList(users));
        log.info("批量插入: {}", batchInsert);
    }

    @Test
    public void page(){
        UserPageParam param = new UserPageParam();
        param.setMinAge(10);
        param.setPageNo(1);
        param.setPageSize(2);
        List<User> userPage = PageQueryHelper.listOrPage(param, p ->
                userMapper.query(qw -> qw.ge(p.getMinAge() != null, User::getAge, p.getMinAge()))
        );
        log.info("查询分页:{}", userPage);

        param.setPageSize(null);
        param.setPageNo(null);
        List<User> users = PageQueryHelper.listOrPage(param, p ->
                userMapper.query(qw -> qw.ge(p.getMinAge() != null, User::getAge, p.getMinAge()))
        );
        log.info("不分页:{}", users);
    }

}
