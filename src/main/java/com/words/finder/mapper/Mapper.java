package com.words.finder.mapper;

public interface Mapper<T, S> {
    T convert(S s);
}
