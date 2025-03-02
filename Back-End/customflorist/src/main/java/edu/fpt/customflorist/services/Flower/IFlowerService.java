package edu.fpt.customflorist.services.Flower;

import edu.fpt.customflorist.dtos.Flower.FlowerDTO;
import edu.fpt.customflorist.models.Flower;

import java.util.List;

public interface IFlowerService {
    Flower createFlower(FlowerDTO flowerDTO);
    Flower getFlowerById(Long id);
    Flower updateFlower(Long id, FlowerDTO flowerDTO);
    void deleteFlower(Long id);
    List<FlowerDTO> getAllFlowers();
}
