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
 * Criteria class for the Celeb entity. This class is used in CelebResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /celebs?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CelebCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter celebName;

    private LongFilter communityId;

    private LongFilter umxmId;

    public CelebCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCelebName() {
        return celebName;
    }

    public void setCelebName(StringFilter celebName) {
        this.celebName = celebName;
    }

    public LongFilter getCommunityId() {
        return communityId;
    }

    public void setCommunityId(LongFilter communityId) {
        this.communityId = communityId;
    }

    public LongFilter getUmxmId() {
        return umxmId;
    }

    public void setUmxmId(LongFilter umxmId) {
        this.umxmId = umxmId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CelebCriteria that = (CelebCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(celebName, that.celebName) &&
            Objects.equals(communityId, that.communityId) &&
            Objects.equals(umxmId, that.umxmId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        celebName,
        communityId,
        umxmId
        );
    }

    @Override
    public String toString() {
        return "CelebCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (celebName != null ? "celebName=" + celebName + ", " : "") +
                (communityId != null ? "communityId=" + communityId + ", " : "") +
                (umxmId != null ? "umxmId=" + umxmId + ", " : "") +
            "}";
    }

}
