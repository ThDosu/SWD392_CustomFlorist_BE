package edu.fpt.customflorist.services.Flower;

import edu.fpt.customflorist.dtos.Flower.FlowerDTO;
import edu.fpt.customflorist.exceptions.DataNotFoundException;
import edu.fpt.customflorist.models.Flower;
import edu.fpt.customflorist.repositories.FlowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FlowerService {
    private final FlowerRepository flowerRepository;

    public Flower createFlower(FlowerDTO flowerDTO) {
        Flower flower = new Flower();
        flower.setName(flowerDTO.getName());
        flower.setFlowerType(flowerDTO.getFlowerType());
        flower.setColor(flowerDTO.getColor());
        flower.setPrice(flowerDTO.getPrice());
        flower.setImage(flowerDTO.getImage());
        flower.setIsActive(flowerDTO.getIsActive());
        return flowerRepository.save(flower);
    }

    public Flower updateFlower(Long flowerId, FlowerDTO flowerDTO) throws DataNotFoundException {
        Flower flower = flowerRepository.findById(flowerId)
                .orElseThrow(() -> new DataNotFoundException("Flower not found"));

        flower.setName(flowerDTO.getName());
        flower.setFlowerType(flowerDTO.getFlowerType());
        flower.setColor(flowerDTO.getColor());
        flower.setPrice(flowerDTO.getPrice());
        flower.setImage(flowerDTO.getImage());
        flower.setIsActive(flowerDTO.getIsActive());

        return flowerRepository.save(flower);
    }

    public void deleteFlower(Long flowerId) throws DataNotFoundException {
        Flower flower = flowerRepository.findById(flowerId)
                .orElseThrow(() -> new DataNotFoundException("Flower not found"));

        flowerRepository.delete(flower);
    }

    public Flower getFlowerById(Long flowerId) throws DataNotFoundException {
        return flowerRepository.findById(flowerId)
                .orElseThrow(() -> new DataNotFoundException("Flower not found"));
    }

    public Page<Flower> getAllFlowers(Pageable pageable) {
        return flowerRepository.findAll(pageable);
    }
}
