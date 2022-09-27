package vn.com.assistant.fqcbackend.mapper;

import org.mapstruct.Mapper;
import org.springframework.util.StringUtils;

@Mapper(componentModel = "spring")

public interface MapStructMapper {
    default String emptyStringToNull(String string) {
        return string == null || StringUtils.trimAllWhitespace(string).isEmpty() ? null : string;
    }

}
