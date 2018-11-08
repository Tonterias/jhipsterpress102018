package com.jhipsterpress.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Umxm.
 */
@Entity
@Table(name = "umxm")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "umxm")
public class Umxm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne(optional = false)    @NotNull
    @JoinColumn(unique = true)
    private User user;

    @ManyToMany(mappedBy = "umxms", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Interest> interests = new HashSet<>();

    @ManyToMany(mappedBy = "umxms", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Activity> activities = new HashSet<>();

    @ManyToMany(mappedBy = "umxms", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Celeb> celebs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public Umxm user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Interest> getInterests() {
        return interests;
    }

    public Umxm interests(Set<Interest> interests) {
        this.interests = interests;
        return this;
    }

    public Umxm addInterest(Interest interest) {
        this.interests.add(interest);
        interest.getUmxms().add(this);
        return this;
    }

    public Umxm removeInterest(Interest interest) {
        this.interests.remove(interest);
        interest.getUmxms().remove(this);
        return this;
    }

    public void setInterests(Set<Interest> interests) {
        this.interests = interests;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public Umxm activities(Set<Activity> activities) {
        this.activities = activities;
        return this;
    }

    public Umxm addActivity(Activity activity) {
        this.activities.add(activity);
        activity.getUmxms().add(this);
        return this;
    }

    public Umxm removeActivity(Activity activity) {
        this.activities.remove(activity);
        activity.getUmxms().remove(this);
        return this;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }

    public Set<Celeb> getCelebs() {
        return celebs;
    }

    public Umxm celebs(Set<Celeb> celebs) {
        this.celebs = celebs;
        return this;
    }

    public Umxm addCeleb(Celeb celeb) {
        this.celebs.add(celeb);
        celeb.getUmxms().add(this);
        return this;
    }

    public Umxm removeCeleb(Celeb celeb) {
        this.celebs.remove(celeb);
        celeb.getUmxms().remove(this);
        return this;
    }

    public void setCelebs(Set<Celeb> celebs) {
        this.celebs = celebs;
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
        Umxm umxm = (Umxm) o;
        if (umxm.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), umxm.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Umxm{" +
            "id=" + getId() +
            "}";
    }
}
