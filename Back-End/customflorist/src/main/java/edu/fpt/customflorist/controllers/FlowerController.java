package edu.fpt.customflorist.controllers;

import edu.fpt.customflorist.dtos.Flower.FlowerDTO;
import edu.fpt.customflorist.models.Flower;
import edu.fpt.customflorist.responses.ResponseObject;
import edu.fpt.customflorist.services.Flower.IFlowerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/api/v1/flowers")
public class FlowerController {
    private final IFlowerService flowerService;

    @PostMapping
    public ResponseEntity<?> createFlower(
            @Valid @RequestBody FlowerDTO flowerDTO,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        Flower flower = flowerService.createFlower(flowerDTO);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .message("Flower created successfully")
                        .data(flower)
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFlowerById(@PathVariable Long id) {
        try {
            Flower flower = flowerService.getFlowerById(id);
            return ResponseEntity.ok(ResponseObject.builder()
                    .message("Fetch flower successfully")
                    .data(flower)
                    .status(HttpStatus.OK)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFlower(@PathVariable Long id, @Valid @RequestBody FlowerDTO flowerDTO) {
        try {
            Flower updatedFlower = flowerService.updateFlower(id, flowerDTO);
            return ResponseEntity.ok(ResponseObject.builder()
                    .message("Flower updated successfully")
                    .data(updatedFlower)
                    .status(HttpStatus.OK)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFlower(@PathVariable Long id) {
        try {
            flowerService.deleteFlower(id);
            return ResponseEntity.ok(ResponseObject.builder()
                    .message("Flower deleted successfully")
                    .status(HttpStatus.OK)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllFlowers() {
        List<FlowerDTO> flowers = flowerService.getAllFlowers();
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Fetch all flowers successfully")
                .data(flowers)
                .status(HttpStatus.OK)
                .build());
    }
}