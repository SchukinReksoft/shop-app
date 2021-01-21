package com.example.demo.api.rest;

import com.example.demo.core.models.CatalogEntity;
import com.example.demo.core.services.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/catalog")
public class CatalogController {

    private final CatalogService catalogService;

    @Autowired
    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping() // получаем весь каталог
    public ResponseEntity<List<CatalogEntity>> getFullCatalog() {
        List<CatalogEntity> catalogEntityList = catalogService.listAll();
        return ResponseEntity.ok(catalogEntityList);
    }

    @GetMapping("/{id}/") // получаем конкретный товар по идентификатору
    public ResponseEntity<CatalogEntity> getCatalogItem(@PathVariable(name = "id") UUID id) {
        Optional<CatalogEntity> result = catalogService.getById(id);
        return result
                .map(entity -> ResponseEntity.ok(entity))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping() // создаем каталог
    public ResponseEntity<CatalogEntity> createCatalog(@RequestBody CatalogEntity catalogEntity) {
       // проверить валидность catalogEntity
        boolean result = catalogService.validateEntity(catalogEntity);
        // если фальш выдаем ошибку bedRequest
        // если правда - вызываем метод save
        if (result) {
           CatalogEntity resSave = catalogService.save(catalogEntity);
           return ResponseEntity.ok(resSave);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/{id}/") // изменяем конкретный товар по идентификатору
    public ResponseEntity<CatalogEntity> editCatalogItem(@PathVariable(name = "id")UUID id, @RequestBody CatalogEntity catalogEntity) {
        boolean result = catalogService.validateEntity(catalogEntity);
        if (result) {
            Optional<CatalogEntity> resEdit = catalogService.editCatalog(id, catalogEntity);
            return resEdit
                    .map(entity -> ResponseEntity.ok(entity))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}/") // удаляем конкретный товар по идентификатору
    public ResponseEntity<?> deleteCatalogItem(@PathVariable(name = "id") UUID id) {
        Optional<Boolean> result = catalogService.deleteById(id);
        return result
                .map(e -> ResponseEntity.noContent().build())
                .orElseGet(() -> ResponseEntity.notFound().build());

    }




}
