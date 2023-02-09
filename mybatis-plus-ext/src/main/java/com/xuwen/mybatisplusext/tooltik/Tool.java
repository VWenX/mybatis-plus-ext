package com.xuwen.mybatisplusext.tooltik;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xuwen.mybatisplusext.exception.MybatisPlusExtException;

import java.util.List;
import java.util.function.Supplier;

/**
 * @author Wen
 * @createDate 2022-11-30
 */
public class Tool {


    // 解析LMD引用Getter方法的实际Bean字段名称
    public static String parseGetterFieldName(SFunction<?, ?> getterLMD){
        // 解析方法名
        String methodName = LambdaUtils.extract(getterLMD).getImplMethodName();

        // 截取字段名
        if (methodName.startsWith("get")) {
            return methodName.substring(3);
        } else if (methodName.startsWith("is")) {
            return methodName.substring(2);
        }
        return methodName;
    }

    // 填充主键值(如可以)
    public static <E> void fillPK(List<E> list){
        TableInfo tableInfo = TableInfoHelper.getTableInfo(list.get(0).getClass());
        if (!tableInfo.havePK()) {
            return;
        }

        String keyProperty = tableInfo.getKeyProperty();
        Class<?> keyType = tableInfo.getKeyType();
        if (tableInfo.getIdType() == IdType.ASSIGN_ID){
            // 确定生成器
            final Supplier<?> idGen;
            if (String.class == keyType){
                idGen = IdWorker::getIdStr;
            }else if (Long.class == keyType){
                idGen = IdWorker::getId;
            }else {
                throw new MybatisPlusExtException("系统异常 无法匹配id生成器");
            }

            for (E entity : list) {
                // 对为设值的主键填充
                if (tableInfo.getPropertyValue(entity, keyProperty) == null){
                    tableInfo.setPropertyValue(entity, keyProperty, idGen.get());
                }
            }
        }
    }

}
