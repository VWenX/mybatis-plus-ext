package io.github.vwenx.mpext.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.vwenx.mpext.entity.User;


public interface UserMapper extends BaseMapperExt<User> {

    @Override
    default LambdaQueryWrapper<User> queryWrapper() {
        return BaseMapperExt.super.queryWrapper().eq(User::getDeletedAt, 0);
    }

    @Override
    default User genSoftDeleteEntity() {
        return User.builder().deletedAt(System.currentTimeMillis()).build();
    }


}
