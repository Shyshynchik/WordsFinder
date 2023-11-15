package com.words.finder.service;

import com.words.finder.model.FindDto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface WordFilterService {
    Mono<List<String>> findWords(FindDto findDto);
}
