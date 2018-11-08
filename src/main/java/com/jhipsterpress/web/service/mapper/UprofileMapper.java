package com.jhipsterpress.web.service.mapper;

import com.jhipsterpress.web.domain.*;
import com.jhipsterpress.web.service.dto.UprofileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Uprofile and its DTO UprofileDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface UprofileMapper extends EntityMapper<UprofileDTO, Uprofile> {

    @Mapping(source = "user.id", target = "userId")
    UprofileDTO toDto(Uprofile uprofile);

    @Mapping(source = "userId", target = "user")
    Uprofile toEntity(UprofileDTO uprofileDTO);

    default Uprofile fromId(Long id) {
        if (id == null) {
            return null;
        }
        Uprofile uprofile = new Uprofile();
        uprofile.setId(id);
        return uprofile;
    }
}
