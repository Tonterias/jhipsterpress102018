package com.jhipsterpress.web.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Vthumb entity.
 */
public class VthumbDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant creationDate;

    private Boolean vthumbup;

    private Boolean vthumbdown;

    private Long userId;

    private Long vquestionId;

    private Long vanswerId;

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

    public Boolean isVthumbup() {
        return vthumbup;
    }

    public void setVthumbup(Boolean vthumbup) {
        this.vthumbup = vthumbup;
    }

    public Boolean isVthumbdown() {
        return vthumbdown;
    }

    public void setVthumbdown(Boolean vthumbdown) {
        this.vthumbdown = vthumbdown;
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

    public Long getVanswerId() {
        return vanswerId;
    }

    public void setVanswerId(Long vanswerId) {
        this.vanswerId = vanswerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VthumbDTO vthumbDTO = (VthumbDTO) o;
        if (vthumbDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vthumbDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VthumbDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", vthumbup='" + isVthumbup() + "'" +
            ", vthumbdown='" + isVthumbdown() + "'" +
            ", user=" + getUserId() +
            ", vquestion=" + getVquestionId() +
            ", vanswer=" + getVanswerId() +
            "}";
    }
}
