package com.example.demo.core.services;

import com.example.demo.core.repos.CatalogRepository;
import com.example.demo.core.models.CatalogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@Service
public class CatalogService {


    private final CatalogRepository catalogRepository;

    @Autowired
    public CatalogService(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    public Page<CatalogEntity> listAll(String search, Pageable pageable) {
        // SELECT product_name
        // FROM catalog_entity
        // WHERE product_name = 'search'
        // если был передан search - необходимо сделать запрос и найти все сущности в бд, которые содержат в названии параметр
        if (search != null && !search.isBlank()) {
            var result = catalogRepository.findByProductNameLike("%" +search + "%", pageable);
            return result;
        } else {
            return catalogRepository.findAll(pageable);
        }

        // иначе отобразить весь каталог
    }


    public Optional<CatalogEntity> getById(UUID id) {
        return catalogRepository.findById(id);
    }

    public boolean validateEntity(CatalogEntity catalogEntity) {
        boolean result = true; // реализация метода
        return result;
    }

    public CatalogEntity save(CatalogEntity catalogEntity) {
        // проверка на существование объекта?
        // находим его в бд
        // если существует - ничего здесь не делаем, через контроллер говорим bad request
        // если не существует - код ниже
        CatalogEntity newEntity = new CatalogEntity();
        newEntity.setId(UUID.randomUUID());
        newEntity.setProductDescription(catalogEntity.getProductDescription());
        newEntity.setProductKol(catalogEntity.getProductKol());
        newEntity.setProductName(catalogEntity.getProductName());
        newEntity.setProductPhoto(catalogEntity.getProductPhoto());
        newEntity.setProductPrice(catalogEntity.getProductPrice());
        return catalogRepository.save(newEntity);
        // и возвращаем контроллеру созданный объект
    }

    public Optional<CatalogEntity> editCatalog(UUID uuid, CatalogEntity catalogEntity) {
        Optional<CatalogEntity> result = catalogRepository.findById(uuid);
        return result
                .map(entity -> {
                    entity.setProductDescription(catalogEntity.getProductDescription());
                    entity.setProductKol(catalogEntity.getProductKol());
                    entity.setProductPhoto(catalogEntity.getProductPhoto());
                    entity.setProductPrice(catalogEntity.getProductPrice());
                    entity.setProductName(catalogEntity.getProductName());
                    entity.setProductDescription(catalogEntity.getProductDescription());
                    return catalogRepository.save(entity);
                });

    }

    public Optional<Boolean> deleteById(UUID id) {
        Optional<CatalogEntity> result = catalogRepository.findById(id);
        return result
                .map(catalogEntity -> {
                    catalogRepository.deleteById(id);
                    return true;
                });
    }
}
