package com.jhipsterpress.web.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.jhipsterpress.web.domain.enumeration.Gender;

import com.jhipsterpress.web.domain.enumeration.CivilStatus;

import com.jhipsterpress.web.domain.enumeration.Purpose;

import com.jhipsterpress.web.domain.enumeration.Physical;

import com.jhipsterpress.web.domain.enumeration.Religion;

import com.jhipsterpress.web.domain.enumeration.EthnicGroup;

import com.jhipsterpress.web.domain.enumeration.Studies;

import com.jhipsterpress.web.domain.enumeration.Eyes;

import com.jhipsterpress.web.domain.enumeration.Smoker;

import com.jhipsterpress.web.domain.enumeration.Children;

import com.jhipsterpress.web.domain.enumeration.FutureChildren;

/**
 * A Uprofile.
 */
@Entity
@Table(name = "uprofile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "uprofile")
public class Uprofile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @NotNull
    @Size(max = 20)
    @Column(name = "phone", length = 20, nullable = false)
    private String phone;

    @Size(max = 7500)
    @Column(name = "bio", length = 7500)
    private String bio;

    @Column(name = "birthdate")
    private Instant birthdate;

    @Enumerated(EnumType.STRING)
    @Column(name = "civil_status")
    private CivilStatus civilStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "looking_for")
    private Gender lookingFor;

    @Enumerated(EnumType.STRING)
    @Column(name = "purpose")
    private Purpose purpose;

    @Enumerated(EnumType.STRING)
    @Column(name = "physical")
    private Physical physical;

    @Enumerated(EnumType.STRING)
    @Column(name = "religion")
    private Religion religion;

    @Enumerated(EnumType.STRING)
    @Column(name = "ethnic_group")
    private EthnicGroup ethnicGroup;

    @Enumerated(EnumType.STRING)
    @Column(name = "studies")
    private Studies studies;

    @Min(value = -1)
    @Max(value = 20)
    @Column(name = "sibblings")
    private Integer sibblings;

    @Enumerated(EnumType.STRING)
    @Column(name = "eyes")
    private Eyes eyes;

    @Enumerated(EnumType.STRING)
    @Column(name = "smoker")
    private Smoker smoker;

    @Enumerated(EnumType.STRING)
    @Column(name = "children")
    private Children children;

    @Enumerated(EnumType.STRING)
    @Column(name = "future_children")
    private FutureChildren futureChildren;

    @Column(name = "pet")
    private Boolean pet;

    @OneToOne(optional = false)    @NotNull
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public Uprofile creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public byte[] getImage() {
        return image;
    }

    public Uprofile image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Uprofile imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Gender getGender() {
        return gender;
    }

    public Uprofile gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public Uprofile phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBio() {
        return bio;
    }

    public Uprofile bio(String bio) {
        this.bio = bio;
        return this;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Instant getBirthdate() {
        return birthdate;
    }

    public Uprofile birthdate(Instant birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public void setBirthdate(Instant birthdate) {
        this.birthdate = birthdate;
    }

    public CivilStatus getCivilStatus() {
        return civilStatus;
    }

    public Uprofile civilStatus(CivilStatus civilStatus) {
        this.civilStatus = civilStatus;
        return this;
    }

    public void setCivilStatus(CivilStatus civilStatus) {
        this.civilStatus = civilStatus;
    }

    public Gender getLookingFor() {
        return lookingFor;
    }

    public Uprofile lookingFor(Gender lookingFor) {
        this.lookingFor = lookingFor;
        return this;
    }

    public void setLookingFor(Gender lookingFor) {
        this.lookingFor = lookingFor;
    }

    public Purpose getPurpose() {
        return purpose;
    }

    public Uprofile purpose(Purpose purpose) {
        this.purpose = purpose;
        return this;
    }

    public void setPurpose(Purpose purpose) {
        this.purpose = purpose;
    }

    public Physical getPhysical() {
        return physical;
    }

    public Uprofile physical(Physical physical) {
        this.physical = physical;
        return this;
    }

    public void setPhysical(Physical physical) {
        this.physical = physical;
    }

    public Religion getReligion() {
        return religion;
    }

    public Uprofile religion(Religion religion) {
        this.religion = religion;
        return this;
    }

    public void setReligion(Religion religion) {
        this.religion = religion;
    }

    public EthnicGroup getEthnicGroup() {
        return ethnicGroup;
    }

    public Uprofile ethnicGroup(EthnicGroup ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
        return this;
    }

    public void setEthnicGroup(EthnicGroup ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
    }

    public Studies getStudies() {
        return studies;
    }

    public Uprofile studies(Studies studies) {
        this.studies = studies;
        return this;
    }

    public void setStudies(Studies studies) {
        this.studies = studies;
    }

    public Integer getSibblings() {
        return sibblings;
    }

    public Uprofile sibblings(Integer sibblings) {
        this.sibblings = sibblings;
        return this;
    }

    public void setSibblings(Integer sibblings) {
        this.sibblings = sibblings;
    }

    public Eyes getEyes() {
        return eyes;
    }

    public Uprofile eyes(Eyes eyes) {
        this.eyes = eyes;
        return this;
    }

    public void setEyes(Eyes eyes) {
        this.eyes = eyes;
    }

    public Smoker getSmoker() {
        return smoker;
    }

    public Uprofile smoker(Smoker smoker) {
        this.smoker = smoker;
        return this;
    }

    public void setSmoker(Smoker smoker) {
        this.smoker = smoker;
    }

    public Children getChildren() {
        return children;
    }

    public Uprofile children(Children children) {
        this.children = children;
        return this;
    }

    public void setChildren(Children children) {
        this.children = children;
    }

    public FutureChildren getFutureChildren() {
        return futureChildren;
    }

    public Uprofile futureChildren(FutureChildren futureChildren) {
        this.futureChildren = futureChildren;
        return this;
    }

    public void setFutureChildren(FutureChildren futureChildren) {
        this.futureChildren = futureChildren;
    }

    public Boolean isPet() {
        return pet;
    }

    public Uprofile pet(Boolean pet) {
        this.pet = pet;
        return this;
    }

    public void setPet(Boolean pet) {
        this.pet = pet;
    }

    public User getUser() {
        return user;
    }

    public Uprofile user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Uprofile uprofile = (Uprofile) o;
        if (uprofile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), uprofile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Uprofile{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", gender='" + getGender() + "'" +
            ", phone='" + getPhone() + "'" +
            ", bio='" + getBio() + "'" +
            ", birthdate='" + getBirthdate() + "'" +
            ", civilStatus='" + getCivilStatus() + "'" +
            ", lookingFor='" + getLookingFor() + "'" +
            ", purpose='" + getPurpose() + "'" +
            ", physical='" + getPhysical() + "'" +
            ", religion='" + getReligion() + "'" +
            ", ethnicGroup='" + getEthnicGroup() + "'" +
            ", studies='" + getStudies() + "'" +
            ", sibblings=" + getSibblings() +
            ", eyes='" + getEyes() + "'" +
            ", smoker='" + getSmoker() + "'" +
            ", children='" + getChildren() + "'" +
            ", futureChildren='" + getFutureChildren() + "'" +
            ", pet='" + isPet() + "'" +
            "}";
    }
}
