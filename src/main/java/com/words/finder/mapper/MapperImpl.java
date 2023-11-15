package com.words.finder.mapper;

import com.words.finder.model.FindDto;
import com.words.finder.model.UserInputFindDto;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MapperImpl implements Mapper<FindDto, UserInputFindDto> {

    @Override
    public FindDto convert(UserInputFindDto userInputFindDto) {
        return FindDto.builder()
                .mask(userInputFindDto.getMask())
                .excludedMasks(userInputFindDto.getExcludedMasks())
                .excludedLetters(stringToSet(userInputFindDto.getExcludedLetters()))
                .includedLetters(stringToSet(userInputFindDto.getIncludedLetters()))
                .build();
    }

    private Set<Character> stringToSet(String letters) {
        return letters.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toSet());
    }
}
