package io.github.vwenx.mpext.mapper;

import io.github.vwenx.mpext.bean.annotation.Note;

@Note("集合本包的所有Mapper扩展")
public interface BaseMapperExt<T> extends BatchMapper<T>, SoftDeleteMapper<T> {


}
