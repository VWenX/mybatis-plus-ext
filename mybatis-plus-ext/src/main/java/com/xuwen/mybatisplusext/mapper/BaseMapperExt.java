package com.xuwen.mybatisplusext.mapper;

import com.xuwen.mybatisplusext.share.annotation.Note;

@Note("集合本包的所有Mapper扩展")
public interface BaseMapperExt<T> extends SimpleMapper<T>, BatchMapper<T> {


}
