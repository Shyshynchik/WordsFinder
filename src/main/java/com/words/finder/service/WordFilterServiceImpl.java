package com.words.finder.service;

import com.words.finder.api.service.FinderApi;
import com.words.finder.model.FindDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class WordFilterServiceImpl implements WordFilterService {
    private final FinderApi finderApi;

    @Override
    public Mono<List<String>> findWords(FindDto findDto) {
        return finderApi.findByMask(findDto.getMask())
                .map(response -> mapWordsByLetters(response.getResult()))
                .map(words -> filterWords(words, findDto));
    }

    private Map<Character, Set<String>> mapWordsByLetters(List<String> words) {
        Map<Character, Set<String>> wordsByLetters = new HashMap<>();

        for (var word : words) {
            addWordIndexedByLetters(wordsByLetters, word);
        }

        return wordsByLetters;
    }

    private void addWordIndexedByLetters(Map<Character, Set<String>> map, String word) {
        List<Character> charactersInWord = word.chars()
                .mapToObj(c -> (char) c)
                .map(Character::toUpperCase)
                .toList();

        charactersInWord.forEach(character -> {
            if (!map.containsKey(character)) {
                map.put(character, new HashSet<>());
            }
            var set = map.get(character);
            set.add(word);
        });
    }

    private List<String> filterWords(Map<Character, Set<String>> words, FindDto findDto) {
        var excludedLetters = setToUpperCase(findDto.getExcludedLetters());
        var includedLetters = setToUpperCase(findDto.getIncludedLetters());

        var excludedWords = getExcludedWords(words, excludedLetters);

        var filteredWords = words.entrySet().stream()
                .filter(word -> !excludedLetters.contains(word.getKey()))
                .peek(s -> s.getValue().removeAll(excludedWords))
                .filter(s -> includedLetters.isEmpty() || includedLetters.contains(s.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (includedLetters.isEmpty()) {
            return new ArrayList<>(sortWords(filteredWords));
        }

        var includedWords = getIntersection(filteredWords);

        return new ArrayList<>(excludeWordsByMask(includedWords, findDto.getExcludedMasks()));
    }

    private static Set<String> getExcludedWords(Map<Character, Set<String>> words, Set<Character> excludedLetters) {
        return words.entrySet().stream()
                .filter(word -> excludedLetters.contains(word.getKey()))
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    private Set<Character> setToUpperCase(Set<Character> characterSet) {
        return characterSet.stream().map(Character::toUpperCase).collect(Collectors.toSet());
    }

    private Set<String> sortWords(Map<Character, Set<String>> mapLettersToWords) {
        return mapLettersToWords.entrySet().stream().flatMap(i -> i.getValue().stream()).collect(Collectors.toCollection(TreeSet::new));
    }

    private Set<String> getIntersection(Map<Character, Set<String>> charSetMap) {
        Set<String> intersection = new TreeSet<>();

        for (Set<String> charSet : charSetMap.values()) {
            if (intersection.isEmpty()) {
                intersection.addAll(charSet);
                continue;
            }
            intersection.retainAll(charSet);
        }

        return intersection;
    }

    private Set<String> excludeWordsByMask(Set<String> words, Set<String> excludedMasks) {
        return words.stream()
                .filter(word -> !containsMask(word, excludedMasks))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    private boolean containsMask(String word, Set<String> excludedMasks) {
        for (var excludedMask: excludedMasks) {
            String regex = excludedMask.toUpperCase().replaceAll("\\*", ".");

            Pattern pattern = Pattern.compile(regex);

            if (pattern.matcher(word).matches()) {
                return true;
            }
        }

        return false;
    }
}
