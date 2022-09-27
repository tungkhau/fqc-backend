package vn.com.assistant.fqcbackend.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import vn.com.assistant.fqcbackend.dto.FabricRequestDTO;
import vn.com.assistant.fqcbackend.dto.FabricResponseDTO;
import vn.com.assistant.fqcbackend.entity.Customer;
import vn.com.assistant.fqcbackend.entity.Fabric;
import vn.com.assistant.fqcbackend.exception.ConflictException;
import vn.com.assistant.fqcbackend.exception.InvalidException;
import vn.com.assistant.fqcbackend.repository.CustomerRepository;
import vn.com.assistant.fqcbackend.repository.FabricRepository;
import vn.com.assistant.fqcbackend.service.FabricService;
import vn.com.assistant.fqcbackend.mapper.FabricMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class FabricServiceImp implements FabricService {

    private final FabricRepository fabricRepository;

    private final CustomerRepository customerRepository;

    private final Environment env;

    @Override
    public List<FabricResponseDTO> fetch() {
        List<Fabric> fabricList = fabricRepository.findAllByOrderByCreatedTimeDesc();
        return FabricMapper.INSTANCE.listFabricToListFabricResponseDTO(fabricList);
    }

    @Override
    public void create(FabricRequestDTO fabricRequestDTO) {
        Fabric fabric = FabricMapper.INSTANCE.fabricRequestDTOtoFabric(fabricRequestDTO);
        Customer customer = customerRepository.findById(fabricRequestDTO.getCustomerId()).orElseThrow(() -> new InvalidException(env.getProperty("customer.notExisted")));
        fabric.setCustomer(customer);
        fabricRepository.save(fabric);
    }

    @Override
    public void delete(String fabricId) {
        Fabric fabric = fabricRepository.findById(fabricId).orElseThrow(() -> new InvalidException(env.getProperty("fabric.notExisted")));
        if (!(fabric.getProductList().isEmpty())) throw new ConflictException(env.getProperty("fabric.used"));
        fabricRepository.deleteById(fabricId);
    }
}
