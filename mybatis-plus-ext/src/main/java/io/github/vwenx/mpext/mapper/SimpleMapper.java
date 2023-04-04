package io.github.vwenx.mpext.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public interface SimpleMapper<T> extends BaseMapper<T> {

    /* 基础查询构造 */
    default LambdaQueryWrapper<T> queryWrapper(){
        return new LambdaQueryWrapper<>();
    }


    /* 查询系列 */
    default List<T> query(Consumer<LambdaQueryWrapper<T>> conditionAppend){
        LambdaQueryWrapper<T> queryWrapper = this.queryWrapper();
        conditionAppend.accept(queryWrapper);
        return this.selectList(queryWrapper);
    }

    /**
     * 查询数据仅取出指定的字段
     * @param selectField 指定要取出的字段
     * @param conditionAppend 增加条件等对查询构造器的处理
     * @return List《指定的字段值》
     * @param <R> 字段类型
     */
    default <R> List<R> querySingleFiled(SFunction<T, R> selectField, Consumer<LambdaQueryWrapper<T>> conditionAppend){
        return query(tLambdaQueryWrapper -> conditionAppend.accept(tLambdaQueryWrapper.select(selectField)))
                .stream().map(selectField).collect(Collectors.toList());
    }

    default <K, V> Map<K, V> queryMappingVal(SFunction<T, K> keyField, Collection<K> keys, SFunction<T, V> valueField){
        return queryMappingVal(keyField, keys, valueField, null);
    }

    /**
     * 查询映射值
     * @param keyField key字段
     * @param keys 要查询映射值的key集合
     * @param valueField 映射值字段
     * @param otherCondition 其它附加查询条件
     * @return key与指定字段值的映射关系
     * @param <K> 映射key类型
     * @param <V> 映射值类型
     */
    default <K, V> Map<K, V> queryMappingVal(SFunction<T, K> keyField, Collection<K> keys, SFunction<T, V> valueField, Consumer<LambdaQueryWrapper<T>> otherCondition){
        if (keys == null || keys.isEmpty()) return Collections.emptyMap();

        LambdaQueryWrapper<T> queryWrapper =
                queryWrapper().in(keyField, keys).select(keyField, valueField);
        if (otherCondition != null) otherCondition.accept(queryWrapper);
        return selectList(queryWrapper).stream()
                .collect(Collectors.toMap(keyField, valueField));
    }


    /* 删除系列 */
    default int delete(Consumer<LambdaQueryWrapper<T>> conditionAppend){
        LambdaQueryWrapper<T> queryWrapper = this.queryWrapper();
        conditionAppend.accept(queryWrapper);
        return this.delete(queryWrapper);
    }

}
