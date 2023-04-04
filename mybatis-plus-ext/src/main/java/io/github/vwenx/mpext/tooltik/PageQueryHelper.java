package io.github.vwenx.mpext.tooltik;

import com.github.pagehelper.PageHelper;
import io.github.vwenx.mpext.bean.page.PageParamInterface;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 分页查询助手 基于PageHelper实现分页
 */
public class PageQueryHelper {


    /**
     * listOrPage系列均为可选分页
     * @return List / PageHelper的Page
     */
    public static  <E> List<E> listOrPage(Number pageNo, Number pageSize, boolean count, Supplier<List<E>> query){
        int pSize = pageSize==null ? -1 : pageSize.intValue();
        int pNo = pageNo==null ? -1 : pageNo.intValue();

        // 无效分页参数
        if (pSize < 1 || pNo < 1){
            return query.get();
        }

        // 有效分页
        try {
            PageHelper.startPage(pNo, pSize, count);
            return query.get();
        }finally {
            PageHelper.clearPage();
        }
    }

    public static  <E, P extends PageParamInterface> List<E> listOrPage(P param, Supplier<List<E>> query){
        return listOrPage(param.getPageNo(), param.getPageSize(), param.isCount(), query);
    }

    public static  <E, P extends PageParamInterface> List<E> listOrPage(P param, Function<P, List<E>> query){
        return listOrPage(param.getPageNo(), param.getPageSize(), param.isCount(), () -> query.apply(param));
    }

    public static  <E> List<E> listOrPage(Number pageNo, Number pageSize, Supplier<List<E>> query) {
        return listOrPage(pageNo, pageSize, true, query);
    }


}
