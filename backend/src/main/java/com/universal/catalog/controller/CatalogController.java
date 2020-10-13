package com.universal.catalog.controller;

import com.universal.catalog.entity.Catalog;
import com.universal.catalog.repository.CatalogRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CatalogController {
    private final CatalogRepository catalogRepository;

    public CatalogController( CatalogRepository catalogRepository ) {
        this.catalogRepository = catalogRepository;
    }

    @GetMapping(value = "catalogs")
    Map<String, Object> getCatalogs(){
        List<Catalog> catalogList = catalogRepository.findAll();
        Map<String, Object> result = new HashMap<>();
        result.put("catalogs", catalogList);
        return result;
    }
}
