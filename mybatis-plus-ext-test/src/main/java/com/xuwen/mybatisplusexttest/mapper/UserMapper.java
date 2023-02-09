package com.xuwen.mybatisplusexttest.mapper;

import com.xuwen.mybatisplusext.mapper.BaseMapperExt;
import com.xuwen.mybatisplusexttest.entity.User;

/**
 * CREATE TABLE `test_user` (
 *   `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
 *   `name` varchar(32) NOT NULL,
 *   `age` tinyint(4) NOT NULL,
 *   PRIMARY KEY (`id`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
 */
public interface UserMapper extends BaseMapperExt<User> {



}
