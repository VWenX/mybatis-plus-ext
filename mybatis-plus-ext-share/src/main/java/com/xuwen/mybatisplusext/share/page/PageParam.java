package com.xuwen.mybatisplusext.share.page;

public final class PageParam implements PageParamInterface {

    private Integer pageSize;
    private Integer pageNo;
    private boolean count;

    /**
     * 从参数构建PageParam对象
     *
     * @param pageNo   页码
     * @param pageSize 页大小
     * @param useCount 是否进行count 仅在分页场景下有效
     */
    public static PageParam build(Integer pageNo, Integer pageSize, boolean useCount) {
        PageParam param = new PageParam();
        param.pageNo = pageNo;
        param.pageSize = pageSize;
        param.count = useCount;
        return param;
    }


    @Override
    public Integer getPageSize() {
        return pageSize;
    }

    @Override
    public Integer getPageNo() {
        return pageNo;
    }

    @Override
    public boolean isCount() {
        return count;
    }
}
