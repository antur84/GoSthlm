package com.filreas.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DeparturesDto implements Serializable {
    private final List<MetroDto> metros = new ArrayList<>();

    public List<MetroDto> getMetros() {
        return metros;
    }
}
