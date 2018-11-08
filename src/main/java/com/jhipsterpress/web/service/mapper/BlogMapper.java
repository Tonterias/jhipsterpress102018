package com.jhipsterpress.web.service.mapper;

import com.jhipsterpress.web.domain.*;
import com.jhipsterpress.web.service.dto.BlogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Blog and its DTO BlogDTO.
 */
@Mapper(componentModel = "spring", uses = {CommunityMapper.class, UserMapper.class})
public interface BlogMapper extends EntityMapper<BlogDTO, Blog> {

    @Mapping(source = "community.id", target = "communityId")
    @Mapping(source = "community.user.id", target = "userId")
    BlogDTO toDto(Blog blog);

    @Mapping(target = "posts", ignore = true)
    @Mapping(source = "communityId", target = "community")
    @Mapping(source = "userId", target = "community.user.id")
    Blog toEntity(BlogDTO blogDTO);

    default Blog fromId(Long id) {
        if (id == null) {
            return null;
        }
        Blog blog = new Blog();
        blog.setId(id);
        return blog;
    }
}
