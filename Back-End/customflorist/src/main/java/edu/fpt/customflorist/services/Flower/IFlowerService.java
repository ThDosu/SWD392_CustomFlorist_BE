package edu.fpt.customflorist.services.Flower;

import edu.fpt.customflorist.models.Flower;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFlowerService {
    Flower createFlower() throws Exception;
    void updateFlower() throws Exception;
    void deleteFlower() throws Exception;
    Flower getFlowerById() throws Exception;
    Page<Flower> findAll(String keyword, Pageable pageable) throws Exception;
}
