package com.example.lab7_20224926.Service;

import com.example.lab7_20224926.Entity.Proveedor;
import com.example.lab7_20224926.Repository.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProveedorService {

    public final ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    public Map<String, Object> getAllProveedores() {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> proveedores = proveedorRepository.findAll().stream()
                .map(this::convertToMapList)
                .collect(Collectors.toList());
        response.put("proveedores", proveedores);
        response.put("mensaje", "Lista de proveedores obtenida correctamente");
        return response;
    }

    public Map<String, Object> getProveedorById(Long id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor con ID " + id + " no encontrado"));
        Map<String, Object> response = new HashMap<>();
        response.put("proveedor", convertToMapDetail(proveedor));
        response.put("mensaje", "Proveedor encontrado correctamente");
        return response;
    }

    public Map<String, Object> createProveedor(Proveedor proveedor) {
        proveedor.setFechaRegistro(LocalDateTime.now());
        proveedor.setUltimaActualizacion(null);
        proveedor.setEstado(true);
        proveedor = proveedorRepository.save(proveedor);
        Map<String, Object> response = new HashMap<>();
        response.put("proveedor", convertToMapDetail(proveedor));
        response.put("mensaje", "Proveedor creado correctamente");
        return response;
    }

    public Map<String, Object> updateProveedor(Long id, Proveedor updates) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor con ID " + id + " no encontrado"));

        if (updates.getRazonSocial() != null) proveedor.setRazonSocial(updates.getRazonSocial());
        if (updates.getNombreComercial() != null) proveedor.setNombreComercial(updates.getNombreComercial());
        if (updates.getTelefono() != null) proveedor.setTelefono(updates.getTelefono());
        if (updates.getCorreoElectronico() != null) proveedor.setCorreoElectronico(updates.getCorreoElectronico());
        if (updates.getSitioWeb() != null) proveedor.setSitioWeb(updates.getSitioWeb());
        if (updates.getDireccionFisica() != null) proveedor.setDireccionFisica(updates.getDireccionFisica());
        if (updates.getPais() != null) proveedor.setPais(updates.getPais());
        if (updates.getRepresentanteLegal() != null) proveedor.setRepresentanteLegal(updates.getRepresentanteLegal());
        if (updates.getDniRepresentanteLegal() != null) proveedor.setDniRepresentanteLegal(updates.getDniRepresentanteLegal());
        if (updates.getTipoProveedor() != null) proveedor.setTipoProveedor(updates.getTipoProveedor());
        if (updates.getCategoria() != null) proveedor.setCategoria(updates.getCategoria());
        if (updates.getFacturacionAnualDolares() != null) proveedor.setFacturacionAnualDolares(updates.getFacturacionAnualDolares());

        proveedor.setUltimaActualizacion(LocalDateTime.now());
        proveedor = proveedorRepository.save(proveedor);
        Map<String, Object> response = new HashMap<>();
        response.put("proveedor", convertToMapDetail(proveedor));
        response.put("mensaje", "Proveedor actualizado correctamente");
        return response;
    }

    public Map<String, Object> deleteProveedor(Long id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor con ID " + id + " no encontrado"));
        proveedor.setEstado(false);
        proveedor.setUltimaActualizacion(LocalDateTime.now());
        proveedorRepository.save(proveedor);
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Proveedor eliminado lógicamente");
        return response;
    }

    private Map<String, Object> convertToMapList(Proveedor proveedor) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", proveedor.getId());
        map.put("razonSocial", proveedor.getRazonSocial());
        map.put("nombreComercial", proveedor.getNombreComercial());
        map.put("ruc", proveedor.getRuc());
        map.put("telefono", proveedor.getTelefono());
        map.put("correoElectronico", proveedor.getCorreoElectronico());
        map.put("pais", proveedor.getPais());
        map.put("representanteLegal", proveedor.getRepresentanteLegal());
        map.put("dniRepresentanteLegal", proveedor.getDniRepresentanteLegal());
        map.put("tipoProveedor", proveedor.getTipoProveedor());
        map.put("categoria", proveedor.getCategoria());
        map.put("estado", proveedor.isEstado() ? "Activo" : "Inactivo");
        return map;
    }

    private Map<String, Object> convertToMapDetail(Proveedor proveedor) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", proveedor.getId());
        map.put("razonSocial", proveedor.getRazonSocial());
        map.put("nombreComercial", proveedor.getNombreComercial());
        map.put("ruc", proveedor.getRuc());
        map.put("telefono", proveedor.getTelefono());
        map.put("correoElectronico", proveedor.getCorreoElectronico());
        map.put("sitioWeb", proveedor.getSitioWeb());
        map.put("direccionFisica", proveedor.getDireccionFisica());
        map.put("pais", proveedor.getPais());
        map.put("representanteLegal", proveedor.getRepresentanteLegal());
        map.put("dniRepresentanteLegal", proveedor.getDniRepresentanteLegal());
        map.put("tipoProveedor", proveedor.getTipoProveedor());
        map.put("categoria", proveedor.getCategoria());
        map.put("facturacionAnualDolares", proveedor.getFacturacionAnualDolares());
        map.put("fechaRegistro", proveedor.getFechaRegistro().toString());
        map.put("ultimaActualizacion", proveedor.getUltimaActualizacion() != null ? proveedor.getUltimaActualizacion().toString() : null);
        map.put("estado", proveedor.isEstado() ? "Activo" : "Inactivo");
        return map;
    }
}