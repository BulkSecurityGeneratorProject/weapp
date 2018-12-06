package com.hntyhb.detection.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.hntyhb.detection.domain.enumeration.Status;

/**
 * A Sample.
 */
@Entity
@Table(name = "sample")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sample implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 采样编号
     */
    @NotNull
    @ApiModelProperty(value = "采样编号", required = true)
    @Column(name = "code", nullable = false)
    private String code;

    /**
     * 样品名称
     */
    @NotNull
    @ApiModelProperty(value = "样品名称", required = true)
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 监测项目
     */
    @NotNull
    @ApiModelProperty(value = "监测项目", required = true)
    @Column(name = "jhi_type", nullable = false)
    private String type;

    /**
     * 状态
     */
    @NotNull
    @ApiModelProperty(value = "状态", required = true)
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    /**
     * 采样时间
     */
    @ApiModelProperty(value = "采样时间")
    @Column(name = "sample_date")
    private LocalDate sampleDate;

    /**
     * 大气压
     */
    @ApiModelProperty(value = "大气压")
    @Column(name = "dqy")
    private Float dqy;

    /**
     * 风向
     */
    @ApiModelProperty(value = "风向")
    @Column(name = "fx")
    private String fx;

    /**
     * 风速
     */
    @ApiModelProperty(value = "风速")
    @Column(name = "fs")
    private Float fs;

    /**
     * 温度
     */
    @ApiModelProperty(value = "温度")
    @Column(name = "wd")
    private Float wd;

    /**
     * 相对湿度
     */
    @ApiModelProperty(value = "相对湿度")
    @Column(name = "xdsd")
    private Float xdsd;

    @ManyToOne
    @JsonIgnoreProperties("sample1S")
    private SampleUser user1;

    @ManyToOne
    @JsonIgnoreProperties("sample2S")
    private SampleUser user2;

    @ManyToOne
    @JsonIgnoreProperties("samples")
    private Point point;

    @OneToMany(mappedBy = "sample")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Params> params = new HashSet<>();

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

    public Sample code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Sample name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public Sample type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public Sample status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getSampleDate() {
        return sampleDate;
    }

    public Sample sampleDate(LocalDate sampleDate) {
        this.sampleDate = sampleDate;
        return this;
    }

    public void setSampleDate(LocalDate sampleDate) {
        this.sampleDate = sampleDate;
    }

    public Float getDqy() {
        return dqy;
    }

    public Sample dqy(Float dqy) {
        this.dqy = dqy;
        return this;
    }

    public void setDqy(Float dqy) {
        this.dqy = dqy;
    }

    public String getFx() {
        return fx;
    }

    public Sample fx(String fx) {
        this.fx = fx;
        return this;
    }

    public void setFx(String fx) {
        this.fx = fx;
    }

    public Float getFs() {
        return fs;
    }

    public Sample fs(Float fs) {
        this.fs = fs;
        return this;
    }

    public void setFs(Float fs) {
        this.fs = fs;
    }

    public Float getWd() {
        return wd;
    }

    public Sample wd(Float wd) {
        this.wd = wd;
        return this;
    }

    public void setWd(Float wd) {
        this.wd = wd;
    }

    public Float getXdsd() {
        return xdsd;
    }

    public Sample xdsd(Float xdsd) {
        this.xdsd = xdsd;
        return this;
    }

    public void setXdsd(Float xdsd) {
        this.xdsd = xdsd;
    }

    public SampleUser getUser1() {
        return user1;
    }

    public Sample user1(SampleUser sampleUser) {
        this.user1 = sampleUser;
        return this;
    }

    public void setUser1(SampleUser sampleUser) {
        this.user1 = sampleUser;
    }

    public SampleUser getUser2() {
        return user2;
    }

    public Sample user2(SampleUser sampleUser) {
        this.user2 = sampleUser;
        return this;
    }

    public void setUser2(SampleUser sampleUser) {
        this.user2 = sampleUser;
    }

    public Point getPoint() {
        return point;
    }

    public Sample point(Point point) {
        this.point = point;
        return this;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Set<Params> getParams() {
        return params;
    }

    public Sample params(Set<Params> params) {
        this.params = params;
        return this;
    }

    public Sample addParam(Params params) {
        this.params.add(params);
        params.setSample(this);
        return this;
    }

    public Sample removeParam(Params params) {
        this.params.remove(params);
        params.setSample(null);
        return this;
    }

    public void setParams(Set<Params> params) {
        this.params = params;
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
        Sample sample = (Sample) o;
        if (sample.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sample.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Sample{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", sampleDate='" + getSampleDate() + "'" +
            ", dqy=" + getDqy() +
            ", fx='" + getFx() + "'" +
            ", fs=" + getFs() +
            ", wd=" + getWd() +
            ", xdsd=" + getXdsd() +
            "}";
    }
}
