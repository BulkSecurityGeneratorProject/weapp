package com.hntyhb.detection.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A SampleUser.
 */
@Entity
@Table(name = "sample_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SampleUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "user1")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Sample> sample1S = new HashSet<>();

    @OneToMany(mappedBy = "user2")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Sample> sample2S = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public SampleUser name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Sample> getSample1S() {
        return sample1S;
    }

    public SampleUser sample1S(Set<Sample> samples) {
        this.sample1S = samples;
        return this;
    }

    public SampleUser addSample1(Sample sample) {
        this.sample1S.add(sample);
        sample.setUser1(this);
        return this;
    }

    public SampleUser removeSample1(Sample sample) {
        this.sample1S.remove(sample);
        sample.setUser1(null);
        return this;
    }

    public void setSample1S(Set<Sample> samples) {
        this.sample1S = samples;
    }

    public Set<Sample> getSample2S() {
        return sample2S;
    }

    public SampleUser sample2S(Set<Sample> samples) {
        this.sample2S = samples;
        return this;
    }

    public SampleUser addSample2(Sample sample) {
        this.sample2S.add(sample);
        sample.setUser2(this);
        return this;
    }

    public SampleUser removeSample2(Sample sample) {
        this.sample2S.remove(sample);
        sample.setUser2(null);
        return this;
    }

    public void setSample2S(Set<Sample> samples) {
        this.sample2S = samples;
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
        SampleUser sampleUser = (SampleUser) o;
        if (sampleUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sampleUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SampleUser{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
