package vn.com.assistant.fqcbackend.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import vn.com.assistant.fqcbackend.dto.CriterialRequestDTO;
import vn.com.assistant.fqcbackend.dto.CriterialResponseDTO;
import vn.com.assistant.fqcbackend.dto.GradeRequestDTO;
import vn.com.assistant.fqcbackend.entity.Criterial;
import vn.com.assistant.fqcbackend.entity.enums.Unit;
import vn.com.assistant.fqcbackend.exception.ConflictException;
import vn.com.assistant.fqcbackend.exception.InvalidException;
import vn.com.assistant.fqcbackend.repository.CriterialRepository;
import vn.com.assistant.fqcbackend.utility.CriterialMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class CriterialServiceImp implements CriterialService {
    private final Environment env;
    private final CriterialRepository _repository;
    @Override
    public List<CriterialResponseDTO> fetch() {
        List<Criterial> criterialList = _repository.findAll();
        return CriterialMapper.INSTANCE.listCriterialToListCriterialResponseDTO(criterialList);
    }

    @Override
    public void create(CriterialRequestDTO criterialRequestDTO) {
        boolean checkGrade = checkGradeListValid(criterialRequestDTO.getGrades());
        if(!checkGrade) throw new InvalidException(env.getProperty("criterial.grade.inValid"));
        if(!EnumUtils.isValidEnum(Unit.class, criterialRequestDTO.getUnit())) throw new InvalidException(env.getProperty("criterial.unit.inValid"));
        Criterial criterial = CriterialMapper.INSTANCE.criterialRequestDTOtoCriterial(criterialRequestDTO);
        _repository.save(criterial);
    }

    @Override
    public void delete(String criterialId) {
        Criterial criterial = _repository.findById(criterialId).orElseThrow(() -> new InvalidException(env.getProperty("criterial.notExisted")));
        if (!(criterial.getGrades().isEmpty()))
            throw new ConflictException(env.getProperty("criterial.used"));
        _repository.deleteById(criterialId);
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
