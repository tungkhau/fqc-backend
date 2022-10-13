package vn.com.assistant.fqcbackend.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import vn.com.assistant.fqcbackend.dto.LotRequestDTO;
import vn.com.assistant.fqcbackend.dto.LotResponseDTO;
import vn.com.assistant.fqcbackend.entity.Lot;
import vn.com.assistant.fqcbackend.entity.Product;
import vn.com.assistant.fqcbackend.exception.ConflictException;
import vn.com.assistant.fqcbackend.exception.InvalidException;
import vn.com.assistant.fqcbackend.repository.LotRepository;
import vn.com.assistant.fqcbackend.repository.ProductRepository;
import vn.com.assistant.fqcbackend.service.LotService;
import vn.com.assistant.fqcbackend.mapper.LotMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class LotServiceImp implements LotService {
    private final LotRepository lotRepository;
    private final Environment env;

    private final ProductRepository productRepository;

    @Override
    public List<LotResponseDTO> fetch() {
        List<Lot> lots = lotRepository.findAllByOrderByCreatedTimeDesc();
        return LotMapper.INSTANCE.listLotToLotResponseDTO(lots);
    }

    @Override
    public LotResponseDTO get(String lotCode) {
        Lot lot = lotRepository.findByCode(lotCode).orElseThrow(() -> new InvalidException(env.getProperty("lot.notExisted")));
        return LotMapper.INSTANCE.lotToLotResponseDTO(lot);
    }

    @Override
    public void create(LotRequestDTO lotRequestDTO) {
        Lot lot = LotMapper.INSTANCE.lotRequestDTOtoLot(lotRequestDTO);
        Product product = productRepository.findById(lotRequestDTO.getProductId()).orElseThrow(() -> new InvalidException(env.getProperty("product.notExisted")));

        lot.setProduct(product);
        lotRepository.save(lot);
    }

    @Override
    public void update(LotRequestDTO lotRequestDTO, String lotId) {
        Lot lot = lotRepository.findById(lotId).orElseThrow(() -> new InvalidException(env.getProperty("lot.notExisted")));
        LotMapper.INSTANCE.updateLotFromLotRequestDTO(lotRequestDTO, lot);
        lotRepository.save(lot);
    }

    @Override
    public void delete(String lotId) {
        Lot lot = lotRepository.findById(lotId).orElseThrow(() -> new InvalidException(env.getProperty("lot.notExisted")));
        if (!lot.getInspectingSessionList().isEmpty()) throw new ConflictException(env.getProperty("lot.used"));
        lotRepository.deleteById(lotId);
    }
}
