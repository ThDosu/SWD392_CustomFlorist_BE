package edu.fpt.customflorist.controllers;

import edu.fpt.customflorist.dtos.Promotion.PromotionDTO;
import edu.fpt.customflorist.exceptions.DataNotFoundException;
import edu.fpt.customflorist.services.Promotion.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/api/v1/promotions")
@RequiredArgsConstructor
@CrossOrigin(origins = {"*", "http://localhost:3000", "https://yourflorist.vercel.app"})
public class PromotionController {
    private final PromotionService promotionService;

    @GetMapping
    public ResponseEntity<List<PromotionDTO>> getAllPromotions() {
        return ResponseEntity.ok(promotionService.getAllPromotions());
    }

    @GetMapping("/active")
    public ResponseEntity<List<PromotionDTO>> getActivePromotions() {
        return ResponseEntity.ok(promotionService.getActivePromotions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromotionDTO> getPromotionById(@PathVariable Long id) throws DataNotFoundException {
        return ResponseEntity.ok(promotionService.getPromotionById(id));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<PromotionDTO> getPromotionByCode(@PathVariable String code) throws DataNotFoundException {
        return ResponseEntity.ok(promotionService.findPromotionByCode(code));
    }

    @PostMapping
    public ResponseEntity<PromotionDTO> createPromotion(@RequestBody PromotionDTO promotionDTO) {
        return new ResponseEntity<>(promotionService.createPromotion(promotionDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromotionDTO> updatePromotion(@PathVariable Long id, @RequestBody PromotionDTO promotionDTO) throws DataNotFoundException {
        return ResponseEntity.ok(promotionService.updatePromotion(id, promotionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromotion(@PathVariable Long id) throws DataNotFoundException {
        promotionService.deletePromotion(id);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping("/apply")
//    public ResponseEntity<Map<String, Boolean>> applyPromotion(@RequestBody Map<String, String> request) {
//        String code = request.get("code");
//        boolean applied = promotionService.applyPromotion(code);
//        return ResponseEntity.ok(Map.of("applied", applied));
//    }
}
