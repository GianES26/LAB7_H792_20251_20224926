package com.example.lab7_20224926.Controller;

import com.example.lab7_20224926.Service.ProveedorService;
import com.example.lab7_20224926.Entity.Proveedor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllProveedores() {
        Map<String, Object> response = new HashMap<>();
        try {
            return ResponseEntity.ok(proveedorService.getAllProveedores());
        } catch (Exception e) {
            response.put("error", "Error al obtener la lista de proveedores: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProveedorById(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Long proveedorId = Long.parseLong(id);
            return ResponseEntity.ok(proveedorService.getProveedorById(proveedorId));
        } catch (NumberFormatException e) {
            response.put("error", "El ID debe ser un número válido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("error", "Error interno al buscar el proveedor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createProveedor(@RequestBody Proveedor input) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        if (input.getRazonSocial() == null || input.getRazonSocial().isBlank() || input.getRazonSocial().length() > 100) {
            errors.put("razonSocial", "La razón social es obligatoria y no debe exceder 100 caracteres");
        } else if (!input.getRazonSocial().matches("^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ ]+$")) {
            errors.put("razonSocial", "La razón social debe ser alfanumérica");
        }

        if (input.getNombreComercial() != null && input.getNombreComercial().length() > 100) {
            errors.put("nombreComercial", "El nombre comercial no debe exceder 100 caracteres");
        } else if (input.getNombreComercial() != null && !input.getNombreComercial().matches("^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ ]*$")) {
            errors.put("nombreComercial", "El nombre comercial debe ser alfanumérico");
        }

        if (input.getRuc() == null || input.getRuc().isBlank()) {
            errors.put("ruc", "El RUC es obligatorio");
        } else if (!input.getRuc().matches("^[0-9]{11}$")) {
            errors.put("ruc", "El RUC debe tener exactamente 11 dígitos numéricos");
        } else if (proveedorService.proveedorRepository.existsByRuc(input.getRuc())) {
            errors.put("ruc", "El RUC ya está registrado");
        }

        if (input.getTelefono() != null && !input.getTelefono().matches("^[0-9]{9,15}$")) {
            errors.put("telefono", "El teléfono debe ser numérico y entre 9 y 15 dígitos");
        }

        if (input.getCorreoElectronico() != null && !input.getCorreoElectronico().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            errors.put("correoElectronico", "El correo electrónico debe tener un formato válido");
        }

        if (input.getSitioWeb() != null && !input.getSitioWeb().matches("^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$")) {
            errors.put("sitioWeb", "El sitio web debe tener un formato de URL válido");
        }

        if (input.getDireccionFisica() != null && input.getDireccionFisica().length() > 150) {
            errors.put("direccionFisica", "La dirección física no debe exceder 150 caracteres");
        }

        if (input.getPais() == null || input.getPais().isBlank()) {
            errors.put("pais", "El país es obligatorio");
        } else if (!input.getPais().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            errors.put("pais", "El país debe ser alfabético");
        }

        if (input.getRepresentanteLegal() == null || input.getRepresentanteLegal().isBlank()) {
            errors.put("representanteLegal", "El representante legal es obligatorio");
        } else if (!input.getRepresentanteLegal().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            errors.put("representanteLegal", "El representante legal debe ser alfabético");
        }

        if (input.getDniRepresentanteLegal() == null || input.getDniRepresentanteLegal().isBlank()) {
            errors.put("dniRepresentanteLegal", "El DNI del representante legal es obligatorio");
        } else if (!input.getDniRepresentanteLegal().matches("^[0-9]{8}$")) {
            errors.put("dniRepresentanteLegal", "El DNI del representante legal debe tener 8 dígitos numéricos");
        }

        if (input.getTipoProveedor() == null || input.getTipoProveedor().isBlank()) {
            errors.put("tipoProveedor", "El tipo de proveedor es obligatorio");
        } else if (!input.getTipoProveedor().matches("^(Nacional|Internacional)$")) {
            errors.put("tipoProveedor", "El tipo de proveedor debe ser 'Nacional' o 'Internacional'");
        }

        if (input.getCategoria() == null || input.getCategoria().isBlank()) {
            errors.put("categoria", "La categoría es obligatoria");
        } else if (!input.getCategoria().matches("^(Servicios|Productos|Tecnología|Otros)$")) {
            errors.put("categoria", "La categoría debe ser 'Servicios', 'Productos', 'Tecnología' o 'Otros'");
        }

        if (input.getFacturacionAnualDolares() != null && input.getFacturacionAnualDolares() < 0) {
            errors.put("facturacionAnualDolares", "La facturación anual no puede ser negativa");
        }

        if (!errors.isEmpty()) {
            response.put("error", errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(proveedorService.createProveedor(input));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateProveedor(@PathVariable String id, @RequestBody Map<String, Object> updateRequest) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        try {
            Long proveedorId = Long.parseLong(id);
            Proveedor existing = proveedorService.proveedorRepository.findById(proveedorId).orElse(null);
            if (existing == null) {
                response.put("error", "Proveedor con ID " + proveedorId + " no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            // Define valid fields that can be updated
            Set<String> validFields = Set.of("razonSocial", "nombreComercial", "telefono", "correoElectronico",
                    "sitioWeb", "direccionFisica", "pais", "representanteLegal",
                    "dniRepresentanteLegal", "tipoProveedor", "categoria", "facturacionAnualDolares");

            // Check for unrecognized fields
            for (String field : updateRequest.keySet()) {
                if (!validFields.contains(field)) {
                    errors.put(field, "Campo no reconocido o no permitido para actualización");
                }
            }

            if (!errors.isEmpty()) {
                response.put("error", errors);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Convert Map to Proveedor object for validation and update
            Proveedor updateProveedor = new Proveedor();
            if (updateRequest.containsKey("razonSocial")) updateProveedor.setRazonSocial((String) updateRequest.get("razonSocial"));
            if (updateRequest.containsKey("nombreComercial")) updateProveedor.setNombreComercial((String) updateRequest.get("nombreComercial"));
            if (updateRequest.containsKey("telefono")) updateProveedor.setTelefono((String) updateRequest.get("telefono"));
            if (updateRequest.containsKey("correoElectronico")) updateProveedor.setCorreoElectronico((String) updateRequest.get("correoElectronico"));
            if (updateRequest.containsKey("sitioWeb")) updateProveedor.setSitioWeb((String) updateRequest.get("sitioWeb"));
            if (updateRequest.containsKey("direccionFisica")) updateProveedor.setDireccionFisica((String) updateRequest.get("direccionFisica"));
            if (updateRequest.containsKey("pais")) updateProveedor.setPais((String) updateRequest.get("pais"));
            if (updateRequest.containsKey("representanteLegal")) updateProveedor.setRepresentanteLegal((String) updateRequest.get("representanteLegal"));
            if (updateRequest.containsKey("dniRepresentanteLegal")) updateProveedor.setDniRepresentanteLegal((String) updateRequest.get("dniRepresentanteLegal"));
            if (updateRequest.containsKey("tipoProveedor")) updateProveedor.setTipoProveedor((String) updateRequest.get("tipoProveedor"));
            if (updateRequest.containsKey("categoria")) updateProveedor.setCategoria((String) updateRequest.get("categoria"));
            if (updateRequest.containsKey("facturacionAnualDolares")) updateProveedor.setFacturacionAnualDolares((Double) updateRequest.get("facturacionAnualDolares"));

            // Apply validations
            if (updateProveedor.getRazonSocial() != null) {
                if (updateProveedor.getRazonSocial().isBlank() || updateProveedor.getRazonSocial().length() > 100) {
                    errors.put("razonSocial", "La razón social no debe exceder 100 caracteres");
                } else if (!updateProveedor.getRazonSocial().matches("^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ ]+$")) {
                    errors.put("razonSocial", "La razón social debe ser alfanumérica");
                }
            }

            if (updateProveedor.getNombreComercial() != null) {
                if (updateProveedor.getNombreComercial().length() > 100) {
                    errors.put("nombreComercial", "El nombre comercial no debe exceder 100 caracteres");
                } else if (!updateProveedor.getNombreComercial().matches("^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ ]*$")) {
                    errors.put("nombreComercial", "El nombre comercial debe ser alfanumérico");
                }
            }

            if (updateProveedor.getTelefono() != null && !updateProveedor.getTelefono().matches("^[0-9]{9,15}$")) {
                errors.put("telefono", "El teléfono debe ser numérico y entre 9 y 15 dígitos");
            }

            if (updateProveedor.getCorreoElectronico() != null && !updateProveedor.getCorreoElectronico().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                errors.put("correoElectronico", "El correo electrónico debe tener un formato válido");
            }

            if (updateProveedor.getSitioWeb() != null && !updateProveedor.getSitioWeb().matches("^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$")) {
                errors.put("sitioWeb", "El sitio web debe tener un formato de URL válido");
            }

            if (updateProveedor.getDireccionFisica() != null && updateProveedor.getDireccionFisica().length() > 150) {
                errors.put("direccionFisica", "La dirección física no debe exceder 150 caracteres");
            }

            if (updateProveedor.getPais() != null) {
                if (updateProveedor.getPais().isBlank()) {
                    errors.put("pais", "El país es obligatorio");
                } else if (!updateProveedor.getPais().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
                    errors.put("pais", "El país debe ser alfabético");
                }
            }

            if (updateProveedor.getRepresentanteLegal() != null) {
                if (updateProveedor.getRepresentanteLegal().isBlank()) {
                    errors.put("representanteLegal", "El representante legal es obligatorio");
                } else if (!updateProveedor.getRepresentanteLegal().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
                    errors.put("representanteLegal", "El representante legal debe ser alfabético");
                }
            }

            if (updateProveedor.getDniRepresentanteLegal() != null) {
                if (updateProveedor.getDniRepresentanteLegal().isBlank()) {
                    errors.put("dniRepresentanteLegal", "El DNI del representante legal es obligatorio");
                } else if (!updateProveedor.getDniRepresentanteLegal().matches("^[0-9]{8}$")) {
                    errors.put("dniRepresentanteLegal", "El DNI del representante legal debe tener 8 dígitos numéricos");
                }
            }

            if (updateProveedor.getTipoProveedor() != null) {
                if (updateProveedor.getTipoProveedor().isBlank()) {
                    errors.put("tipoProveedor", "El tipo de proveedor es obligatorio");
                } else if (!updateProveedor.getTipoProveedor().matches("^(Nacional|Internacional)$")) {
                    errors.put("tipoProveedor", "El tipo de proveedor debe ser 'Nacional' o 'Internacional'");
                }
            }

            if (updateProveedor.getCategoria() != null) {
                if (updateProveedor.getCategoria().isBlank()) {
                    errors.put("categoria", "La categoría es obligatoria");
                } else if (!updateProveedor.getCategoria().matches("^(Servicios|Productos|Tecnología|Otros)$")) {
                    errors.put("categoria", "La categoría debe ser 'Servicios', 'Productos', 'Tecnología' o 'Otros'");
                }
            }

            if (updateProveedor.getFacturacionAnualDolares() != null && updateProveedor.getFacturacionAnualDolares() < 0) {
                errors.put("facturacionAnualDolares", "La facturación anual no puede ser negativa");
            }

            if (!errors.isEmpty()) {
                response.put("error", errors);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            return ResponseEntity.ok(proveedorService.updateProveedor(proveedorId, updateProveedor));
        } catch (NumberFormatException e) {
            response.put("error", "El ID debe ser un número válido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("error", "Error interno al actualizar el proveedor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteProveedor(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Long proveedorId = Long.parseLong(id);
            Proveedor existing = proveedorService.proveedorRepository.findById(proveedorId).orElse(null);
            if (existing == null) {
                response.put("error", "Proveedor con ID " + proveedorId + " no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            return ResponseEntity.ok(proveedorService.deleteProveedor(proveedorId));
        } catch (NumberFormatException e) {
            response.put("error", "El ID debe ser un número válido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("error", "Error al eliminar el proveedor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}