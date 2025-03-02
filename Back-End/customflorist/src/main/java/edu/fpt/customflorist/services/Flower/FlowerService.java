package edu.fpt.customflorist.services.Flower;

import edu.fpt.customflorist.dtos.Flower.FlowerDTO;
import edu.fpt.customflorist.models.Flower;
import edu.fpt.customflorist.repositories.FlowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
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

    public Flower getFlowerById(Long id) {
        Optional<Flower> flower = flowerRepository.findById(id);
        if (flower.isPresent()) {
            return flower.get();
        } else {
            throw new RuntimeException("Flower not found");
        }
    }

    public Flower updateFlower(Long id, FlowerDTO flowerDTO) {
        Optional<Flower> existingFlower = flowerRepository.findById(id);
        if (existingFlower.isPresent()) {
            Flower flower = existingFlower.get();
            flower.setName(flowerDTO.getName());
            flower.setFlowerType(flowerDTO.getFlowerType());
            flower.setColor(flowerDTO.getColor());
            flower.setPrice(flowerDTO.getPrice());
            flower.setImage(flowerDTO.getImage());
            flower.setIsActive(flowerDTO.getIsActive());
            return flowerRepository.save(flower);
        } else {
            throw new RuntimeException("Flower not found");
        }
    }

    public void deleteFlower(Long id) {
        if (flowerRepository.existsById(id)) {
            flowerRepository.deleteById(id);
        } else {
            throw new RuntimeException("Flower not found");
        }
    }

    public List<FlowerDTO> getAllFlowers() {
        return flowerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private FlowerDTO convertToDTO(Flower flower) {
        return FlowerDTO.builder()
                .name(flower.getName())
                .flowerType(flower.getFlowerType())
                .color(flower.getColor())
                .price(flower.getPrice())
                .image(flower.getImage())
                .isActive(flower.getIsActive())
                .build();
    }
}
