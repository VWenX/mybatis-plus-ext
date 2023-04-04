package io.github.vwenx.mpext.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import io.github.vwenx.mpext.exception.MybatisPlusExtException;

import java.util.function.Consumer;

public interface SoftDeleteMapper<T> extends SimpleMapper<T> {

    default <V> int softDeleteBy(Consumer<LambdaQueryWrapper<T>> conditionAppend){
        LambdaQueryWrapper<T> queryWrapper = this.queryWrapper();
        conditionAppend.accept(queryWrapper);
        return update(genSoftDeleteEntity(), queryWrapper);
    }

    default <V> int softDeleteBy(SFunction<T, V> field, V value){
        return update(this.genSoftDeleteEntity(), this.queryWrapper().eq(field, value));
    }

    default T genSoftDeleteEntity(){
        throw new MybatisPlusExtException("需覆写软删除标记实体生成方法！因为使用了SoftDeleteMapper方法！");
    }

}
