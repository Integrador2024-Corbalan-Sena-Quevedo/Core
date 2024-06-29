package com.mides.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Email> emails;
    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private Dirreccion dirreccion;
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Telefono> telefonos;
}
