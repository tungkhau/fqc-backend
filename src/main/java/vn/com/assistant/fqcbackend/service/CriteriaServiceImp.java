package vn.com.assistant.fqcbackend.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import vn.com.assistant.fqcbackend.dto.CriteriaRequestDTO;
import vn.com.assistant.fqcbackend.dto.CriteriaResponseDTO;
import vn.com.assistant.fqcbackend.dto.GradeRequestDTO;
import vn.com.assistant.fqcbackend.entity.Criteria;
import vn.com.assistant.fqcbackend.entity.enums.Unit;
import vn.com.assistant.fqcbackend.exception.ConflictException;
import vn.com.assistant.fqcbackend.exception.InvalidException;
import vn.com.assistant.fqcbackend.repository.CriteriaRepository;
import vn.com.assistant.fqcbackend.utility.CriteriaMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class CriteriaServiceImp implements CriteriaService {
    private final Environment env;
    private final CriteriaRepository _repository;
    @Override
    public List<CriteriaResponseDTO> fetch() {
        List<Criteria> criteriaList = _repository.findAll();
        return CriteriaMapper.INSTANCE.listCriteriaToListCriteriaResponseDTO(criteriaList);
    }

    @Override
    public void create(CriteriaRequestDTO criteriaRequestDTO) {
        boolean checkGrade = checkGradeListValid(criteriaRequestDTO.getGrades());
        if(!checkGrade) throw new InvalidException(env.getProperty("criteria.grade.inValid"));
        if(!EnumUtils.isValidEnum(Unit.class, criteriaRequestDTO.getUnit())) throw new InvalidException(env.getProperty("criteria.unit.inValid"));
        Criteria criteria = CriteriaMapper.INSTANCE.criteriaRequestDTOtoCriteria(criteriaRequestDTO);
        _repository.save(criteria);
    }

    @Override
    public void delete(String criteriaId) {
        Criteria criteria = _repository.findById(criteriaId).orElseThrow(() -> new InvalidException(env.getProperty("criteria.notExisted")));
        if (!(criteria.getProducts().isEmpty()))
            throw new ConflictException(env.getProperty("criteria.used"));
        _repository.deleteById(criteriaId);
    }

    public boolean checkGradeListValid(List<GradeRequestDTO> gradeRequestDTOList){
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
