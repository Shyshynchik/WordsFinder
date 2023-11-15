package com.words.finder.controller;

import com.words.finder.mapper.Mapper;
import com.words.finder.model.FindDto;
import com.words.finder.model.UserInputFindDto;
import com.words.finder.service.WordFilterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@Tag(name = "Word finder", description = "Контроллер для поска слов")
@RestController
@RequiredArgsConstructor
public class WordFinderController {
    private final WordFilterService wordFilterService;
    private final Mapper<FindDto, UserInputFindDto> mapper;

    @Operation(description = "Ищет слова по маске, а также по списку включенных, исключенных слов и исключающих масок")
    @PostMapping("/find")
    public ResponseEntity<Mono<List<String>>> findWord(
            @RequestBody UserInputFindDto findRequest
    ) {
        var findDto = mapper.convert(findRequest);
        return ResponseEntity.ok(wordFilterService.findWords(findDto));
    }

}
