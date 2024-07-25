package com.mides.core.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo")
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
    @JsonManagedReference
    private List<Email> emails;
    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Dirreccion dirreccion;
    @JsonManagedReference
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Telefono> telefonos;


    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", emails=" + emails +
                ", dirreccion=" + dirreccion +
                ", telefonos=" + telefonos +
                '}';
    }
}
