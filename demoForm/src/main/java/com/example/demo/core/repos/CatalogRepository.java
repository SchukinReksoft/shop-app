package com.example.demo.core.repos;

import com.example.demo.core.models.CatalogEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CatalogRepository extends CrudRepository<CatalogEntity, UUID> {
    List<CatalogEntity> findAll();
}
