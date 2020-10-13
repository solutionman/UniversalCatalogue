package com.universal.catalog.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String tablename;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "catalogid")
    private List<CatalogField> fields;
}
