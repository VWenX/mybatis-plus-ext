package com.xuwen.mybatisplusexttest.param;

import com.xuwen.mybatisplusext.share.page.PageParamInterface;
import lombok.Data;

@Data
public class UserPageParam implements PageParamInterface {

    private Integer minAge;

    private Integer pageSize;

    private Integer pageNo;

}
