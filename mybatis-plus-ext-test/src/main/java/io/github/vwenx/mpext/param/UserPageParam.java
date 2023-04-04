package io.github.vwenx.mpext.param;

import io.github.vwenx.mpext.bean.page.PageParamInterface;
import lombok.Data;

@Data
public class UserPageParam implements PageParamInterface {

    private Integer minAge;

    private Integer pageSize;

    private Integer pageNo;

}
