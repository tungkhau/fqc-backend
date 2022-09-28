package vn.com.assistant.fqcbackend.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import vn.com.assistant.fqcbackend.dto.ColorRequestDTO;
import vn.com.assistant.fqcbackend.dto.ColorResponseDTO;
import vn.com.assistant.fqcbackend.entity.Color;
import vn.com.assistant.fqcbackend.entity.Customer;
import vn.com.assistant.fqcbackend.exception.ConflictException;
import vn.com.assistant.fqcbackend.exception.InvalidException;
import vn.com.assistant.fqcbackend.repository.ColorRepository;
import vn.com.assistant.fqcbackend.repository.CustomerRepository;
import vn.com.assistant.fqcbackend.service.ColorService;
import vn.com.assistant.fqcbackend.mapper.ColorMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class ColorServiceImp implements ColorService {

    private final ColorRepository colorRepository;

    private final CustomerRepository customerRepository;


    private final Environment env;

    @Override
    public List<ColorResponseDTO> fetch() {
        List<Color> colorList = colorRepository.findAllByOrderByCreatedTimeDesc();
        return ColorMapper.INSTANCE.listColorToListColorResponseDTO(colorList);
    }

    @Override
    public void create(ColorRequestDTO colorRequestDTO) {
        Color color = ColorMapper.INSTANCE.colorRequestDTOtoColor(colorRequestDTO);
        Customer customer = customerRepository.findById(colorRequestDTO.getCustomerId()).orElseThrow(() -> new InvalidException(env.getProperty("customer.notFound")));
        color.setCustomer(customer);
        colorRepository.save(color);
    }

    @Override
    public void delete(String colorId) {
        Color color = colorRepository.findById(colorId).orElseThrow(() -> new InvalidException(env.getProperty("color.notFound")));
        if (!(color.getProductList().isEmpty())) throw new ConflictException(env.getProperty("color.used"));
        colorRepository.deleteById(colorId);
    }
}
