package com.jhipsterpress.web.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.jhipsterpress.web.domain.enumeration.Gender;
import com.jhipsterpress.web.domain.enumeration.CivilStatus;
import com.jhipsterpress.web.domain.enumeration.Gender;
import com.jhipsterpress.web.domain.enumeration.Purpose;
import com.jhipsterpress.web.domain.enumeration.Physical;
import com.jhipsterpress.web.domain.enumeration.Religion;
import com.jhipsterpress.web.domain.enumeration.EthnicGroup;
import com.jhipsterpress.web.domain.enumeration.Studies;
import com.jhipsterpress.web.domain.enumeration.Eyes;
import com.jhipsterpress.web.domain.enumeration.Smoker;
import com.jhipsterpress.web.domain.enumeration.Children;
import com.jhipsterpress.web.domain.enumeration.FutureChildren;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the Uprofile entity. This class is used in UprofileResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /uprofiles?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UprofileCriteria implements Serializable {
    /**
     * Class for filtering Gender
     */
    public static class GenderFilter extends Filter<Gender> {
    }
    /**
     * Class for filtering CivilStatus
     */
    public static class CivilStatusFilter extends Filter<CivilStatus> {
    }
    /**
     * Class for filtering Purpose
     */
    public static class PurposeFilter extends Filter<Purpose> {
    }
    /**
     * Class for filtering Physical
     */
    public static class PhysicalFilter extends Filter<Physical> {
    }
    /**
     * Class for filtering Religion
     */
    public static class ReligionFilter extends Filter<Religion> {
    }
    /**
     * Class for filtering EthnicGroup
     */
    public static class EthnicGroupFilter extends Filter<EthnicGroup> {
    }
    /**
     * Class for filtering Studies
     */
    public static class StudiesFilter extends Filter<Studies> {
    }
    /**
     * Class for filtering Eyes
     */
    public static class EyesFilter extends Filter<Eyes> {
    }
    /**
     * Class for filtering Smoker
     */
    public static class SmokerFilter extends Filter<Smoker> {
    }
    /**
     * Class for filtering Children
     */
    public static class ChildrenFilter extends Filter<Children> {
    }
    /**
     * Class for filtering FutureChildren
     */
    public static class FutureChildrenFilter extends Filter<FutureChildren> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter creationDate;

    private GenderFilter gender;

    private StringFilter phone;

    private StringFilter bio;

    private InstantFilter birthdate;

    private CivilStatusFilter civilStatus;

    private GenderFilter lookingFor;

    private PurposeFilter purpose;

    private PhysicalFilter physical;

    private ReligionFilter religion;

    private EthnicGroupFilter ethnicGroup;

    private StudiesFilter studies;

    private IntegerFilter sibblings;

    private EyesFilter eyes;

    private SmokerFilter smoker;

    private ChildrenFilter children;

    private FutureChildrenFilter futureChildren;

    private BooleanFilter pet;

    private LongFilter userId;

    public UprofileCriteria() {
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

    public GenderFilter getGender() {
        return gender;
    }

    public void setGender(GenderFilter gender) {
        this.gender = gender;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getBio() {
        return bio;
    }

    public void setBio(StringFilter bio) {
        this.bio = bio;
    }

    public InstantFilter getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(InstantFilter birthdate) {
        this.birthdate = birthdate;
    }

    public CivilStatusFilter getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(CivilStatusFilter civilStatus) {
        this.civilStatus = civilStatus;
    }

    public GenderFilter getLookingFor() {
        return lookingFor;
    }

    public void setLookingFor(GenderFilter lookingFor) {
        this.lookingFor = lookingFor;
    }

    public PurposeFilter getPurpose() {
        return purpose;
    }

    public void setPurpose(PurposeFilter purpose) {
        this.purpose = purpose;
    }

    public PhysicalFilter getPhysical() {
        return physical;
    }

    public void setPhysical(PhysicalFilter physical) {
        this.physical = physical;
    }

    public ReligionFilter getReligion() {
        return religion;
    }

    public void setReligion(ReligionFilter religion) {
        this.religion = religion;
    }

    public EthnicGroupFilter getEthnicGroup() {
        return ethnicGroup;
    }

    public void setEthnicGroup(EthnicGroupFilter ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
    }

    public StudiesFilter getStudies() {
        return studies;
    }

    public void setStudies(StudiesFilter studies) {
        this.studies = studies;
    }

    public IntegerFilter getSibblings() {
        return sibblings;
    }

    public void setSibblings(IntegerFilter sibblings) {
        this.sibblings = sibblings;
    }

    public EyesFilter getEyes() {
        return eyes;
    }

    public void setEyes(EyesFilter eyes) {
        this.eyes = eyes;
    }

    public SmokerFilter getSmoker() {
        return smoker;
    }

    public void setSmoker(SmokerFilter smoker) {
        this.smoker = smoker;
    }

    public ChildrenFilter getChildren() {
        return children;
    }

    public void setChildren(ChildrenFilter children) {
        this.children = children;
    }

    public FutureChildrenFilter getFutureChildren() {
        return futureChildren;
    }

    public void setFutureChildren(FutureChildrenFilter futureChildren) {
        this.futureChildren = futureChildren;
    }

    public BooleanFilter getPet() {
        return pet;
    }

    public void setPet(BooleanFilter pet) {
        this.pet = pet;
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
        final UprofileCriteria that = (UprofileCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(bio, that.bio) &&
            Objects.equals(birthdate, that.birthdate) &&
            Objects.equals(civilStatus, that.civilStatus) &&
            Objects.equals(lookingFor, that.lookingFor) &&
            Objects.equals(purpose, that.purpose) &&
            Objects.equals(physical, that.physical) &&
            Objects.equals(religion, that.religion) &&
            Objects.equals(ethnicGroup, that.ethnicGroup) &&
            Objects.equals(studies, that.studies) &&
            Objects.equals(sibblings, that.sibblings) &&
            Objects.equals(eyes, that.eyes) &&
            Objects.equals(smoker, that.smoker) &&
            Objects.equals(children, that.children) &&
            Objects.equals(futureChildren, that.futureChildren) &&
            Objects.equals(pet, that.pet) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        creationDate,
        gender,
        phone,
        bio,
        birthdate,
        civilStatus,
        lookingFor,
        purpose,
        physical,
        religion,
        ethnicGroup,
        studies,
        sibblings,
        eyes,
        smoker,
        children,
        futureChildren,
        pet,
        userId
        );
    }

    @Override
    public String toString() {
        return "UprofileCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (creationDate != null ? "creationDate=" + creationDate + ", " : "") +
                (gender != null ? "gender=" + gender + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (bio != null ? "bio=" + bio + ", " : "") +
                (birthdate != null ? "birthdate=" + birthdate + ", " : "") +
                (civilStatus != null ? "civilStatus=" + civilStatus + ", " : "") +
                (lookingFor != null ? "lookingFor=" + lookingFor + ", " : "") +
                (purpose != null ? "purpose=" + purpose + ", " : "") +
                (physical != null ? "physical=" + physical + ", " : "") +
                (religion != null ? "religion=" + religion + ", " : "") +
                (ethnicGroup != null ? "ethnicGroup=" + ethnicGroup + ", " : "") +
                (studies != null ? "studies=" + studies + ", " : "") +
                (sibblings != null ? "sibblings=" + sibblings + ", " : "") +
                (eyes != null ? "eyes=" + eyes + ", " : "") +
                (smoker != null ? "smoker=" + smoker + ", " : "") +
                (children != null ? "children=" + children + ", " : "") +
                (futureChildren != null ? "futureChildren=" + futureChildren + ", " : "") +
                (pet != null ? "pet=" + pet + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
