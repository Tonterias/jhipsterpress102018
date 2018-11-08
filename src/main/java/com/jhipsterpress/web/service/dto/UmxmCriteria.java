package com.jhipsterpress.web.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Umxm entity. This class is used in UmxmResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /umxms?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UmxmCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter userId;

    private LongFilter interestId;

    private LongFilter activityId;

    private LongFilter celebId;

    public UmxmCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getInterestId() {
        return interestId;
    }

    public void setInterestId(LongFilter interestId) {
        this.interestId = interestId;
    }

    public LongFilter getActivityId() {
        return activityId;
    }

    public void setActivityId(LongFilter activityId) {
        this.activityId = activityId;
    }

    public LongFilter getCelebId() {
        return celebId;
    }

    public void setCelebId(LongFilter celebId) {
        this.celebId = celebId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UmxmCriteria that = (UmxmCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(interestId, that.interestId) &&
            Objects.equals(activityId, that.activityId) &&
            Objects.equals(celebId, that.celebId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        userId,
        interestId,
        activityId,
        celebId
        );
    }

    @Override
    public String toString() {
        return "UmxmCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (interestId != null ? "interestId=" + interestId + ", " : "") +
                (activityId != null ? "activityId=" + activityId + ", " : "") +
                (celebId != null ? "celebId=" + celebId + ", " : "") +
            "}";
    }

}
