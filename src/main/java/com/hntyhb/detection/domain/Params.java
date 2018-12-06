package com.hntyhb.detection.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Params.
 */
@Entity
@Table(name = "params")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Params implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 测量类型
     */
    @NotNull
    @ApiModelProperty(value = "测量类型", required = true)
    @Column(name = "jhi_type", nullable = false)
    private String type;

    /**
     * 测量参数
     */
    
    @ApiModelProperty(value = "测量参数", required = true)
    @Lob
    @Column(name = "json", nullable = false)
    private String json;

    @ManyToOne
    @JsonIgnoreProperties("params")
    private Sample sample;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public Params type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJson() {
        return json;
    }

    public Params json(String json) {
        this.json = json;
        return this;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Sample getSample() {
        return sample;
    }

    public Params sample(Sample sample) {
        this.sample = sample;
        return this;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
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
        Params params = (Params) o;
        if (params.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), params.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Params{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", json='" + getJson() + "'" +
            "}";
    }
}
