package vn.com.assistant.fqcbackend.service.imps;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import vn.com.assistant.fqcbackend.dto.CriterionRequestDTO;
import vn.com.assistant.fqcbackend.dto.CriterionResponseDTO;
import vn.com.assistant.fqcbackend.dto.GradeRequestDTO;
import vn.com.assistant.fqcbackend.entity.Criterion;
import vn.com.assistant.fqcbackend.entity.enums.Unit;
import vn.com.assistant.fqcbackend.exception.ConflictException;
import vn.com.assistant.fqcbackend.exception.InvalidException;
import vn.com.assistant.fqcbackend.repository.CriterionRepository;
import vn.com.assistant.fqcbackend.service.CriterionService;
import vn.com.assistant.fqcbackend.mapper.CriterionMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class CriterionServiceImp implements CriterionService {
    private final Environment env;
    private final CriterionRepository criterionRepository;
    @Override
    public List<CriterionResponseDTO> fetch() {
        List<Criterion> criterionList = criterionRepository.findAll();
        return CriterionMapper.INSTANCE.listCriterionToListCriterionResponseDTO(criterionList);
    }

    @Override
    public void create(CriterionRequestDTO criterionRequestDTO) {
        boolean validGradeList = checkGradeListValid(criterionRequestDTO.getGrades());
        if(!validGradeList) throw new InvalidException(env.getProperty("criterion.create.invalidGradeList"));

        boolean validUnit = EnumUtils.isValidEnum(Unit.class, criterionRequestDTO.getUnit());

        if(!validUnit) throw new InvalidException(env.getProperty("criterion.create.invalidUnit"));
        Criterion criterion = CriterionMapper.INSTANCE.criterionRequestDTOtoCriterion(criterionRequestDTO);
        criterionRepository.save(criterion);
    }

    @Override
    public void delete(String criteriaId) {
        Criterion criterion = criterionRepository.findById(criteriaId).orElseThrow(() -> new InvalidException(env.getProperty("criterion.notExisted")));
        if (!(criterion.getProductList().isEmpty()))
            throw new ConflictException(env.getProperty("criterion.used"));
        criterionRepository.deleteById(criteriaId);
    }

    private boolean checkGradeListValid(List<GradeRequestDTO> gradeRequestDTOList){
        if(gradeRequestDTOList.isEmpty()){
            return false;
        }
        for (int i = 1; i < gradeRequestDTOList.size(); i++) {
            for (int j = 0; j < i; j++) {
                if(gradeRequestDTOList.get(i).getAllowedPoint().compareTo(gradeRequestDTOList.get(j).getAllowedPoint()) < 0){
                    return false;
                }
            }
        }
        return true;
    }
}
