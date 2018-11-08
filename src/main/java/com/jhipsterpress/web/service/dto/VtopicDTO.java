package com.jhipsterpress.web.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Vtopic entity.
 */
public class VtopicDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant creationDate;

    @NotNull
    @Size(min = 2, max = 50)
    private String vtopictitle;

    @Size(min = 2, max = 250)
    private String vtopicdesc;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public String getVtopictitle() {
        return vtopictitle;
    }

    public void setVtopictitle(String vtopictitle) {
        this.vtopictitle = vtopictitle;
    }

    public String getVtopicdesc() {
        return vtopicdesc;
    }

    public void setVtopicdesc(String vtopicdesc) {
        this.vtopicdesc = vtopicdesc;
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

        VtopicDTO vtopicDTO = (VtopicDTO) o;
        if (vtopicDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vtopicDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VtopicDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", vtopictitle='" + getVtopictitle() + "'" +
            ", vtopicdesc='" + getVtopicdesc() + "'" +
            ", user=" + getUserId() +
            "}";
    }
}
