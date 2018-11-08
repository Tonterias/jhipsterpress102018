package com.jhipsterpress.web.service.mapper;

import com.jhipsterpress.web.domain.*;
import com.jhipsterpress.web.service.dto.UmxmDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Umxm and its DTO UmxmDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface UmxmMapper extends EntityMapper<UmxmDTO, Umxm> {

    @Mapping(source = "user.id", target = "userId")
    UmxmDTO toDto(Umxm umxm);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "interests", ignore = true)
    @Mapping(target = "activities", ignore = true)
    @Mapping(target = "celebs", ignore = true)
    Umxm toEntity(UmxmDTO umxmDTO);

    default Umxm fromId(Long id) {
        if (id == null) {
            return null;
        }
        Umxm umxm = new Umxm();
        umxm.setId(id);
        return umxm;
    }
}
