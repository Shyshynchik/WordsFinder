package com.words.finder.api.model;

import lombok.Data;

import java.util.List;

@Data
public class FinderResponse {
    private List<String> result;
}
