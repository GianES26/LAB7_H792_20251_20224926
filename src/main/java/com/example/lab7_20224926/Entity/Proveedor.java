package com.example.lab7_20224926.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "proveedor")
@Data
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String razonSocial;

    @Column(length = 100)
    private String nombreComercial;

    @Column(nullable = false, length = 11, unique = true)
    private String ruc;

    @Column(length = 15)
    private String telefono;

    @Column(length = 100)
    private String correoElectronico;

    @Column(length = 100)
    private String sitioWeb;

    @Column(length = 150)
    private String direccionFisica;

    @Column(nullable = false, length = 50)
    private String pais;

    @Column(nullable = false, length = 100)
    private String representanteLegal;

    @Column(nullable = false, length = 8)
    private String dniRepresentanteLegal;

    @Column(nullable = false, length = 20)
    private String tipoProveedor;

    @Column(nullable = false, length = 20)
    private String categoria;

    @Column
    private Double facturacionAnualDolares;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    @Column
    private LocalDateTime ultimaActualizacion;

    @Column(nullable = false)
    private boolean estado;
}