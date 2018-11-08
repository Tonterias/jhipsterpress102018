package com.jhipsterpress.web.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Celeb entity.
 */
public class CelebDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2, max = 40)
    private String celebName;

    private Set<CommunityDTO> communities = new HashSet<>();

    private Set<UmxmDTO> umxms = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCelebName() {
        return celebName;
    }

    public void setCelebName(String celebName) {
        this.celebName = celebName;
    }

    public Set<CommunityDTO> getCommunities() {
        return communities;
    }

    public void setCommunities(Set<CommunityDTO> communities) {
        this.communities = communities;
    }

    public Set<UmxmDTO> getUmxms() {
        return umxms;
    }

    public void setUmxms(Set<UmxmDTO> umxms) {
        this.umxms = umxms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CelebDTO celebDTO = (CelebDTO) o;
        if (celebDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), celebDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CelebDTO{" +
            "id=" + getId() +
            ", celebName='" + getCelebName() + "'" +
            "}";
    }
}
