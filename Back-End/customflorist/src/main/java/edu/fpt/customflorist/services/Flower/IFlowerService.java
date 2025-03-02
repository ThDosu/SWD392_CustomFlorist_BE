package edu.fpt.customflorist.services.Flower;

import edu.fpt.customflorist.dtos.Flower.FlowerDTO;
import edu.fpt.customflorist.exceptions.DataNotFoundException;
import edu.fpt.customflorist.models.Flower;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFlowerService {
    Flower createFlower(FlowerDTO flowerDTO);
    Flower updateFlower(Long flowerId, FlowerDTO flowerDTO) throws DataNotFoundException;
    void deleteFlower(Long flowerId) throws DataNotFoundException;
    Flower getFlowerById(Long flowerId) throws DataNotFoundException;
    Page<Flower> getAllFlowers(Pageable pageable);
}
