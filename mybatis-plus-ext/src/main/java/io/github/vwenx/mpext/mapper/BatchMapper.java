package io.github.vwenx.mpext.mapper;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import io.github.vwenx.mpext.bean.annotation.Note;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author Wen
 * @createDate 2022-11-30
 */
public interface BatchMapper<T> {

    @Note("覆写此方法来自定义单批次大小")
    default int batchInsertLimit(){
        return 1000;
    }

    default int batchInsert(List<T> list) {
        return batchInsertExclusions(list);
    }


    default int batchInsertExclusions(List<T> list, SFunction<T, ?>... exclusions) {
        if (list == null || list.isEmpty()) {
            return 0;
        }

        // 解析手动排除的属性
        Set<String> fieldBlacklist = Arrays.stream(exclusions).map(Tool::parseGetterFieldName).collect(Collectors.toSet());


        TableInfo tableInfo = TableInfoHelper.getTableInfo(list.get(0).getClass());
        String tableName = tableInfo.getTableName();

        List<TableFieldInfo> fieldInfoList = tableInfo.getFieldList().stream()
                .filter(tableFieldInfo -> !fieldBlacklist.contains(tableFieldInfo.getProperty())).collect(Collectors.toList());

        // tableInfo.getFieldList()不包含主键 特殊处理
        boolean appendPK = false;
        if (tableInfo.havePK() && tableInfo.getIdType() != IdType.AUTO){
            // 尝试填充主键
            Tool.fillPK(list);
            appendPK = true;
        }

        // 拼接不优雅，待后期优化
        String columnNames = "`" + fieldInfoList.stream()
                .map(TableFieldInfo::getColumn)
                .collect(Collectors.joining("`, `")) + "`";
        if (appendPK){
            columnNames += ", `" + tableInfo.getKeyColumn() + "`";
        }

        StringBuilder insertValues = new StringBuilder();
        for (TableFieldInfo field : fieldInfoList) {
            insertValues.append(", ");
            insertValues.append("#{model.");
            insertValues.append(field.getProperty());
            insertValues.append("}");
        }
        if (appendPK){
            insertValues.append(", #{model.").append(tableInfo.getKeyProperty()).append("}");
        }
        insertValues.delete(0, 2);

        int insertCount = 0;
        for (List<T> batch : Tool.partition(list, batchInsertLimit())) {
            int insert = this.execBatchInsert(tableName, columnNames, insertValues.toString(), batch);
            insertCount += insert;
        }
        return insertCount;
    }

//    default int batchInsert(List<?> list, String tableName, List<String> colNameList, List<String> fieldNameList){
//        execBatchInsert(
//                tableName,
//                colNameList.stream().map(col -> )
//        );
//    }

    // TODO:后续考虑将SQL处理抽到工具类
    @Insert("<script> INSERT INTO ${tableName} (${colNames}) VALUES <foreach collection='list' item='model' separator=','> ( ${valuesSql} ) </foreach> </script>")
    int execBatchInsert(@Param("tableName") String tableName, @Param("colNames") String colNames, @Param("valuesSql") String valuesSql, @Param("list") List list);

}
