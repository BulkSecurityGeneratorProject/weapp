package com.hntyhb.detection.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Point.
 */
@Entity
@Table(name = "point")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Point implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 测点编号
     */
    @NotNull
    @ApiModelProperty(value = "测点编号", required = true)
    @Column(name = "code", nullable = false)
    private String code;

    /**
     * 测点名称
     */
    @NotNull
    @ApiModelProperty(value = "测点名称", required = true)
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 进度
     */
    @ApiModelProperty(value = "进度")
    @Column(name = "lng")
    private Double lng;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    @Column(name = "lat")
    private Double lat;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    @Column(name = "address")
    private String address;

    @ManyToOne
    @JsonIgnoreProperties("points")
    private Task task;

    @OneToMany(mappedBy = "point")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Sample> samples = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Point code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Point name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLng() {
        return lng;
    }

    public Point lng(Double lng) {
        this.lng = lng;
        return this;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public Point lat(Double lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getAddress() {
        return address;
    }

    public Point address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Task getTask() {
        return task;
    }

    public Point task(Task task) {
        this.task = task;
        return this;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Set<Sample> getSamples() {
        return samples;
    }

    public Point samples(Set<Sample> samples) {
        this.samples = samples;
        return this;
    }

    public Point addSample(Sample sample) {
        this.samples.add(sample);
        sample.setPoint(this);
        return this;
    }

    public Point removeSample(Sample sample) {
        this.samples.remove(sample);
        sample.setPoint(null);
        return this;
    }

    public void setSamples(Set<Sample> samples) {
        this.samples = samples;
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
        Point point = (Point) o;
        if (point.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), point.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Point{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", lng=" + getLng() +
            ", lat=" + getLat() +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
