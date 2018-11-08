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
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the Vtopic entity. This class is used in VtopicResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /vtopics?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VtopicCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter creationDate;

    private StringFilter vtopictitle;

    private StringFilter vtopicdesc;

    private LongFilter vquestionId;

    private LongFilter userId;

    public VtopicCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(InstantFilter creationDate) {
        this.creationDate = creationDate;
    }

    public StringFilter getVtopictitle() {
        return vtopictitle;
    }

    public void setVtopictitle(StringFilter vtopictitle) {
        this.vtopictitle = vtopictitle;
    }

    public StringFilter getVtopicdesc() {
        return vtopicdesc;
    }

    public void setVtopicdesc(StringFilter vtopicdesc) {
        this.vtopicdesc = vtopicdesc;
    }

    public LongFilter getVquestionId() {
        return vquestionId;
    }

    public void setVquestionId(LongFilter vquestionId) {
        this.vquestionId = vquestionId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
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
        final VtopicCriteria that = (VtopicCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(vtopictitle, that.vtopictitle) &&
            Objects.equals(vtopicdesc, that.vtopicdesc) &&
            Objects.equals(vquestionId, that.vquestionId) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        creationDate,
        vtopictitle,
        vtopicdesc,
        vquestionId,
        userId
        );
    }

    @Override
    public String toString() {
        return "VtopicCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (creationDate != null ? "creationDate=" + creationDate + ", " : "") +
                (vtopictitle != null ? "vtopictitle=" + vtopictitle + ", " : "") +
                (vtopicdesc != null ? "vtopicdesc=" + vtopicdesc + ", " : "") +
                (vquestionId != null ? "vquestionId=" + vquestionId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
