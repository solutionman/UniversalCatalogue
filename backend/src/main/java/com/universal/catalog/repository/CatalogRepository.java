package com.universal.catalog.repository;

import com.universal.catalog.entity.Catalog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface CatalogRepository extends JpaRepository<Catalog, Integer> {
    Page<Catalog> findAll(Pageable pageable);
}
