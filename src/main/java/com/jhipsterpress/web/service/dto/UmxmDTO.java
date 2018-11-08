package com.jhipsterpress.web.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Umxm entity.
 */
public class UmxmDTO implements Serializable {

    private Long id;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UmxmDTO umxmDTO = (UmxmDTO) o;
        if (umxmDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), umxmDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UmxmDTO{" +
            "id=" + getId() +
            ", user=" + getUserId() +
            "}";
    }
}
