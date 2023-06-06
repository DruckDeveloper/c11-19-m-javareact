package com.upCycle.controller;

import com.upCycle.dto.request.DtoProducto;
import com.upCycle.dto.response.DtoEcoproveedorResponse;
import com.upCycle.dto.response.DtoProductoResponse;
import com.upCycle.entity.Producto;
import com.upCycle.entity.Usuario;
import com.upCycle.exception.UserNotExistException;
import com.upCycle.exception.UserUnauthorizedException;
import com.upCycle.service.EcoproveedorService;
import com.upCycle.service.ProductoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/api/product")
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoService service;
    private final EcoproveedorService ecoproveedorService;

    @Autowired
    public ProductoController(ProductoService service, EcoproveedorService ecoproveedorService) {
        this.service = service;
        this.ecoproveedorService = ecoproveedorService;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<DtoProductoResponse> crearProducto(@RequestBody DtoProducto dtoProducto) throws UserNotExistException, UserUnauthorizedException {

            //Usuario logueado = (Usuario) session.getAttribute("usuarioLogueado");
            DtoProductoResponse productoResponse = service.crearProducto(dtoProducto);
            return ResponseEntity.status(HttpStatus.CREATED).body(productoResponse);

    }

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<DtoProductoResponse>> listarProductos(){

        try{
            List<DtoProductoResponse> dtoProductoResponse = service.listarProductos();
            if(!dtoProductoResponse.isEmpty()){
                return ResponseEntity.ok().body(dtoProductoResponse);
            }else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DtoEcoproveedorResponse> buscarEcoproveedorPorIdProdcuto(@PathVariable Long id){

        try {
            DtoEcoproveedorResponse dtoEcoproveedorResponse = service.buscarEcoproveedorPorIdProdcuto(id);
            if(dtoEcoproveedorResponse != null){
                return ResponseEntity.ok().body(dtoEcoproveedorResponse);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(path = "/filterTag/{material}")
    public ResponseEntity<List<DtoProductoResponse>> filtrarPorMaterial(@PathVariable String material){
        try {
            List<DtoProductoResponse> dtoProductoResponse = service.listarPorMaterial(material);
            return !dtoProductoResponse.isEmpty() ? ResponseEntity.ok(dtoProductoResponse) : ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<>());
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(path = "/getPoints/{idEcoproveedor}")
    public ResponseEntity<Integer> getEcopuntos(@PathVariable Long idEcoproveedor){
        try {
            return ResponseEntity.ok(ecoproveedorService.getEcopuntos(idEcoproveedor));
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(0);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {

        try {
            service.eliminarProducto(id);
            return ResponseEntity.noContent().build();

        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
