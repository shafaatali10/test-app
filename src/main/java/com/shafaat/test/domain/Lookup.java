package com.shafaat.test.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Lookup.
 */
@Entity
@Table(name = "lookup")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Lookup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "lookup_code")
    private String lookupCode;

    @Column(name = "lookup_value")
    private String lookupValue;

    @Column(name = "description")
    private String description;

    @Column(name = "display_sequence")
    private Integer displaySequence;

    @Column(name = "view_name")
    private String viewName;

    @Column(name = "category")
    private String category;

    @Column(name = "dependent_code")
    private String dependentCode;

    @Column(name = "is_active")
    private Boolean isActive;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Lookup id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLookupCode() {
        return this.lookupCode;
    }

    public Lookup lookupCode(String lookupCode) {
        this.setLookupCode(lookupCode);
        return this;
    }

    public void setLookupCode(String lookupCode) {
        this.lookupCode = lookupCode;
    }

    public String getLookupValue() {
        return this.lookupValue;
    }

    public Lookup lookupValue(String lookupValue) {
        this.setLookupValue(lookupValue);
        return this;
    }

    public void setLookupValue(String lookupValue) {
        this.lookupValue = lookupValue;
    }

    public String getDescription() {
        return this.description;
    }

    public Lookup description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDisplaySequence() {
        return this.displaySequence;
    }

    public Lookup displaySequence(Integer displaySequence) {
        this.setDisplaySequence(displaySequence);
        return this;
    }

    public void setDisplaySequence(Integer displaySequence) {
        this.displaySequence = displaySequence;
    }

    public String getViewName() {
        return this.viewName;
    }

    public Lookup viewName(String viewName) {
        this.setViewName(viewName);
        return this;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getCategory() {
        return this.category;
    }

    public Lookup category(String category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDependentCode() {
        return this.dependentCode;
    }

    public Lookup dependentCode(String dependentCode) {
        this.setDependentCode(dependentCode);
        return this;
    }

    public void setDependentCode(String dependentCode) {
        this.dependentCode = dependentCode;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Lookup isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lookup)) {
            return false;
        }
        return id != null && id.equals(((Lookup) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Lookup{" +
            "id=" + getId() +
            ", lookupCode='" + getLookupCode() + "'" +
            ", lookupValue='" + getLookupValue() + "'" +
            ", description='" + getDescription() + "'" +
            ", displaySequence=" + getDisplaySequence() +
            ", viewName='" + getViewName() + "'" +
            ", category='" + getCategory() + "'" +
            ", dependentCode='" + getDependentCode() + "'" +
            ", isActive='" + getIsActive() + "'" +
            "}";
    }
}
