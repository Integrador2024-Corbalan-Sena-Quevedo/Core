package com.mides.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "parameters")
public class Parameter {
   @Id
    private String id;
   
    private String value;
}
