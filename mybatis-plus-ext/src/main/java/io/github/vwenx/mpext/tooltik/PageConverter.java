package io.github.vwenx.mpext.tooltik;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.github.pagehelper.Page;

import java.util.List;

public class PageConverter {


    public static <E> PageDTO<E> toMpPage(List<E> sourcePage){
        return toMpPage(sourcePage, new PageDTO<>());
    }

    /*
     * 将 list/pageHelper的Page 转换为 MybatisPlus的Page
     */
    public static <E, P extends IPage<E>> P toMpPage(List<E> sourcePage, P targetPage){
        targetPage.setRecords(sourcePage);
        return copyPageInfo(sourcePage, targetPage);
    }

    /*
     * 将 list/pageHelper的Page 转换为 MybatisPlus的Page
     */
    public static <T> IPage<T> toMpPage(List<?> sourcePage, List<T> listData){
        PageDTO<T> targetPage = new PageDTO<>();
        targetPage.setRecords(listData);
        return copyPageInfo(sourcePage, targetPage);
    }

    /*
     * 将 list/pageHelper的Page 转换为 MybatisPlus的Page
     */
    public static <T, P extends IPage<T>> P toMpPage(List<?> sourcePage, List<T> listData, P targetPage){
        targetPage.setRecords(listData);
        return copyPageInfo(sourcePage, targetPage);
    }

    /*
     * copy来源分页信息(如存在) 到 目标IPage对象
     */
    private static <P extends IPage<?>> P copyPageInfo(List<?> sourcePage, P targetPage){
        if (sourcePage instanceof Page){
            Page<?> hPage = (Page<?>) sourcePage;
            targetPage.setTotal(hPage.getTotal());
            targetPage.setCurrent(hPage.getPageNum());
            targetPage.setSize(hPage.getPageSize());
        }
        return targetPage;
    }

}
