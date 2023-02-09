package com.xuwen.mybatisplusexttest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@TableName("test_user")
@Data
@Builder
public class User {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer age;

}
