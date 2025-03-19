package edu.fpt.customflorist.services.Promotion;

import edu.fpt.customflorist.dtos.Promotion.PromotionDTO;
import edu.fpt.customflorist.exceptions.DataNotFoundException;

import java.util.List;

public interface IPromotionService {
    List<PromotionDTO> getAllPromotions();
    List<PromotionDTO> getActivePromotions();
    PromotionDTO getPromotionById(Long id) throws DataNotFoundException;
    PromotionDTO findPromotionByCode(String code) throws DataNotFoundException;
    PromotionDTO createPromotion(PromotionDTO promotionDTO);
    PromotionDTO updatePromotion(Long id, PromotionDTO promotionDTO) throws DataNotFoundException;
    void deletePromotion(Long id) throws DataNotFoundException;
}
