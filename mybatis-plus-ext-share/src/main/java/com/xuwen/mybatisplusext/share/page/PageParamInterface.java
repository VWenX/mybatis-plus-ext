package com.xuwen.mybatisplusext.share.page;

public interface PageParamInterface {

    default Integer getPageSize() {
        return null;
    }

    default Integer getPageNo() {
        return null;
    }

    default boolean isCount(){
        return true;
    }

    default boolean validPageParam() {
        Integer pageNo = getPageNo();
        Integer pageSize = getPageSize();
        return pageNo != null && pageSize != null && pageNo > 0 && pageSize > 0;
    }

}
