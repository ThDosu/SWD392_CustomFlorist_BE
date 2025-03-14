package edu.fpt.customflorist.controllers;

import edu.fpt.customflorist.dtos.Bouquet.BouquetDTO;
import edu.fpt.customflorist.exceptions.DataNotFoundException;
import edu.fpt.customflorist.services.Bouquet.BouquetService;
import edu.fpt.customflorist.services.Bouquet.IBouquetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/api/v1/bouquet")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class BouquetController {
    private final IBouquetService bouquetService;

    @GetMapping
    public ResponseEntity<List<BouquetDTO>> getAllBouquets() {
        return ResponseEntity.ok(bouquetService.getAllBouquets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BouquetDTO> getBouquetById(@PathVariable Long id) throws DataNotFoundException {
        return ResponseEntity.ok(bouquetService.getBouquetById(id));
    }

    @PostMapping
    public ResponseEntity<BouquetDTO> createBouquet(@RequestBody BouquetDTO bouquetDTO) throws DataNotFoundException {
        return new ResponseEntity<>(bouquetService.createBouquet(bouquetDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BouquetDTO> updateBouquet(@PathVariable Long id, @RequestBody BouquetDTO bouquetDTO) throws DataNotFoundException{
        return ResponseEntity.ok(bouquetService.updateBouquet(id, bouquetDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBouquet(@PathVariable Long id) throws DataNotFoundException {
        bouquetService.deleteBouquet(id);
        return ResponseEntity.noContent().build();
    }
}