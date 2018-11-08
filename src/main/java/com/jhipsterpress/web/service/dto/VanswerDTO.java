package com.jhipsterpress.web.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Vanswer entity.
 */
public class VanswerDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant creationDate;

    @NotNull
    @Size(min = 2, max = 500)
    private String urlvanser;

    private Boolean accepted;

    private Long userId;

    private Long vquestionId;

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

    public String getUrlvanser() {
        return urlvanser;
    }

    public void setUrlvanser(String urlvanser) {
        this.urlvanser = urlvanser;
    }

    public Boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVquestionId() {
        return vquestionId;
    }

    public void setVquestionId(Long vquestionId) {
        this.vquestionId = vquestionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VanswerDTO vanswerDTO = (VanswerDTO) o;
        if (vanswerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vanswerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VanswerDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", urlvanser='" + getUrlvanser() + "'" +
            ", accepted='" + isAccepted() + "'" +
            ", user=" + getUserId() +
            ", vquestion=" + getVquestionId() +
            "}";
    }
}
