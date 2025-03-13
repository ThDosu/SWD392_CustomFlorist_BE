package edu.fpt.customflorist.services.Bouquet;


import edu.fpt.customflorist.dtos.Bouquet.BouquetCompositionDTO;
import edu.fpt.customflorist.dtos.Bouquet.BouquetDTO;
import edu.fpt.customflorist.exceptions.DataNotFoundException;
import edu.fpt.customflorist.models.Bouquet;
import edu.fpt.customflorist.models.BouquetComposition;
import edu.fpt.customflorist.models.Flower;
import edu.fpt.customflorist.repositories.BouquetCompositionRepository;
import edu.fpt.customflorist.repositories.BouquetRepository;
import edu.fpt.customflorist.repositories.FlowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import edu.fpt.customflorist.models.BouquetComposition;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BouquetService implements IBouquetService {
    private final BouquetRepository bouquetRepository;
    private final BouquetCompositionRepository compositionRepository;
    private final FlowerRepository flowerRepository;


    public List<BouquetDTO> getAllBouquets() {
        return bouquetRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BouquetDTO getBouquetById(Long id) throws DataNotFoundException {
        Bouquet bouquet = bouquetRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Bouquet not found with id: " + id));
        return convertToDTO(bouquet);
    }

    @Transactional
    public BouquetDTO createBouquet(BouquetDTO bouquetDTO) throws DataNotFoundException {
        Bouquet bouquet = new Bouquet();
        bouquet.setName(bouquetDTO.getName());
        bouquet.setDescription(bouquetDTO.getDescription());
        bouquet.setBasePrice(bouquetDTO.getPrice());
        bouquet.setImage(bouquetDTO.getImageUrl());

        Bouquet savedBouquet = bouquetRepository.save(bouquet);

        // Xử lý thành phần của bó hoa
        if (bouquetDTO.getCompositions() != null) {
            for (BouquetCompositionDTO compositionDTO : bouquetDTO.getCompositions()) {
                Flower flower = flowerRepository.findById(compositionDTO.getFlowerId())
                        .orElseThrow(() -> new DataNotFoundException("Flower not found with id: " + compositionDTO.getFlowerId()));

                BouquetComposition composition = new BouquetComposition();
                composition.setBouquet(savedBouquet);
                composition.setFlower(flower);
                composition.setMaxQuantity(compositionDTO.getQuantity());

                compositionRepository.save(composition);
            }
        }

        return convertToDTO(bouquetRepository.findById(savedBouquet.getBouquetId()).orElseThrow());
    }

    @Transactional
    public BouquetDTO updateBouquet(Long id, BouquetDTO bouquetDTO) throws DataNotFoundException {
        Bouquet bouquet = bouquetRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Bouquet not found with id: " + id));

        bouquet.setName(bouquetDTO.getName());
        bouquet.setDescription(bouquetDTO.getDescription());
        bouquet.setBasePrice(bouquetDTO.getPrice());
        bouquet.setImage(bouquetDTO.getImageUrl());

        // Xóa các thành phần cũ
        compositionRepository.deleteAll(bouquet.getCompositions());
        bouquet.getCompositions().clear();

        // Thêm các thành phần mới
        if (bouquetDTO.getCompositions() != null) {
            for (BouquetCompositionDTO compositionDTO : bouquetDTO.getCompositions()) {
                Flower flower = flowerRepository.findById(compositionDTO.getFlowerId())
                        .orElseThrow(() -> new DataNotFoundException("Flower not found with id: " + compositionDTO.getFlowerId()));

                BouquetComposition composition = new BouquetComposition();
                composition.setBouquet(bouquet);
                composition.setFlower(flower);
                composition.setMaxQuantity(compositionDTO.getQuantity());

                bouquet.getCompositions().add(composition);
            }
        }

        Bouquet updatedBouquet = bouquetRepository.save(bouquet);
        return convertToDTO(updatedBouquet);
    }

    @Transactional
    public void deleteBouquet(Long id) throws DataNotFoundException {
        Bouquet bouquet = bouquetRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Bouquet not found with id: " + id));

        // Xóa các thành phần trước khi xóa bó hoa
        compositionRepository.deleteAll(bouquet.getCompositions());
        bouquetRepository.delete(bouquet);
    }

    private BouquetDTO convertToDTO(Bouquet bouquet) {
        BouquetDTO dto = new BouquetDTO();
        dto.setId(bouquet.getBouquetId());
        dto.setName(bouquet.getName());
        dto.setDescription(bouquet.getDescription());
        dto.setPrice(bouquet.getBasePrice());
        dto.setImageUrl(bouquet.getImage());

        // Chuyển đổi danh sách thành phần
        List<BouquetCompositionDTO> compositionDTOs = bouquet.getCompositions().stream()
                .map(composition -> {
                    BouquetCompositionDTO compDTO = new BouquetCompositionDTO();
                    compDTO.setId(composition.getBouquetCompositionId());
                    compDTO.setFlowerId(composition.getFlower().getFlowerId());
                    compDTO.setFlowerName(composition.getFlower().getName());
                    compDTO.setQuantity(composition.getMaxQuantity());
                    return compDTO;
                })
                .collect(Collectors.toList());

        dto.setCompositions(compositionDTOs);
        return dto;
    }
}