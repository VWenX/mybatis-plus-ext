package com.xuwen.mybatisplusext.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public interface SimpleMapper<T> extends BaseMapper<T> {

    /** 基础查询构造 */
    default LambdaQueryWrapper<T> queryWrapper(){
        return new LambdaQueryWrapper<>();
    }

    default LambdaQueryWrapper<T> dataPermissionQueryWrapper(){
        return queryWrapper();// 如有需要，请在子类覆写
    }


    /**
     * 查询数据仅取出指定的字段
     * @param selectField 指定要取出的字段
     * @param conditionAppend 增加条件等对查询构造器的处理
     * @return List<指定的字段值>
     */
    default <R> List<R> querySingleFiled(SFunction<T, R> selectField, Consumer<LambdaQueryWrapper<T>> conditionAppend){
        return query(tLambdaQueryWrapper -> conditionAppend.accept(tLambdaQueryWrapper.select(selectField)))
                .stream().map(selectField).collect(Collectors.toList());
    }

    default List<T> query(Consumer<LambdaQueryWrapper<T>> conditionAppend){
        LambdaQueryWrapper<T> queryWrapper = this.queryWrapper();
        conditionAppend.accept(queryWrapper);
        return this.selectList(queryWrapper);
    }


    /** 删除系列 */
    default int delete(Consumer<LambdaQueryWrapper<T>> conditionAppend){
        LambdaQueryWrapper<T> queryWrapper = this.queryWrapper();
        conditionAppend.accept(queryWrapper);
        return this.delete(queryWrapper);
    }

}
