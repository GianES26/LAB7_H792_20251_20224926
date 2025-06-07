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

    @Column(name = "razon_social", nullable = false, length = 100)
    private String razonSocial;

    @Column(name = "nombre_comercial", length = 100)
    private String nombreComercial;

    @Column(name = "ruc", nullable = false, length = 11, unique = true)
    private String ruc;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name = "correo_electronico", length = 100)
    private String correoElectronico;

    @Column(name = "sitio_web", length = 100)
    private String sitioWeb;

    @Column(name = "direccion_fisica", length = 150)
    private String direccionFisica;

    @Column(name = "pais", nullable = false, length = 50)
    private String pais;

    @Column(name = "representante_legal", nullable = false, length = 100)
    private String representanteLegal;

    @Column(name = "dni_representante_legal", nullable = false, length = 8)
    private String dniRepresentanteLegal;

    @Column(name = "tipo_proveedor", nullable = false, length = 20)
    private String tipoProveedor;

    @Column(name = "categoria", nullable = false, length = 20)
    private String categoria;

    @Column(name = "facturacion_anual_dolares")
    private Double facturacionAnualDolares;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "ultima_actualizacion")
    private LocalDateTime ultimaActualizacion;

    @Column(name = "estado", nullable = false)
    private boolean estado;
}