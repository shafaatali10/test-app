package com.shafaat.test.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A GeneralData.
 */
@Entity
@Table(name = "general_data")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GeneralData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "table_usage")
    private String tableUsage;

    @Column(name = "db_selection")
    private String dbSelection;

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "has_data_more_than_5_million")
    private Boolean hasDataMoreThan5Million;

    @Column(name = "is_parallelization_reqd")
    private Boolean isParallelizationReqd;

    @Column(name = "recovery_class")
    private String recoveryClass;

    @Column(name = "order_id")
    private Long orderId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GeneralData id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableUsage() {
        return this.tableUsage;
    }

    public GeneralData tableUsage(String tableUsage) {
        this.setTableUsage(tableUsage);
        return this;
    }

    public void setTableUsage(String tableUsage) {
        this.tableUsage = tableUsage;
    }

    public String getDbSelection() {
        return this.dbSelection;
    }

    public GeneralData dbSelection(String dbSelection) {
        this.setDbSelection(dbSelection);
        return this;
    }

    public void setDbSelection(String dbSelection) {
        this.dbSelection = dbSelection;
    }

    public String getTableName() {
        return this.tableName;
    }

    public GeneralData tableName(String tableName) {
        this.setTableName(tableName);
        return this;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Boolean getHasDataMoreThan5Million() {
        return this.hasDataMoreThan5Million;
    }

    public GeneralData hasDataMoreThan5Million(Boolean hasDataMoreThan5Million) {
        this.setHasDataMoreThan5Million(hasDataMoreThan5Million);
        return this;
    }

    public void setHasDataMoreThan5Million(Boolean hasDataMoreThan5Million) {
        this.hasDataMoreThan5Million = hasDataMoreThan5Million;
    }

    public Boolean getIsParallelizationReqd() {
        return this.isParallelizationReqd;
    }

    public GeneralData isParallelizationReqd(Boolean isParallelizationReqd) {
        this.setIsParallelizationReqd(isParallelizationReqd);
        return this;
    }

    public void setIsParallelizationReqd(Boolean isParallelizationReqd) {
        this.isParallelizationReqd = isParallelizationReqd;
    }

    public String getRecoveryClass() {
        return this.recoveryClass;
    }

    public GeneralData recoveryClass(String recoveryClass) {
        this.setRecoveryClass(recoveryClass);
        return this;
    }

    public void setRecoveryClass(String recoveryClass) {
        this.recoveryClass = recoveryClass;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public GeneralData orderId(Long orderId) {
        this.setOrderId(orderId);
        return this;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GeneralData)) {
            return false;
        }
        return id != null && id.equals(((GeneralData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GeneralData{" +
            "id=" + getId() +
            ", tableUsage='" + getTableUsage() + "'" +
            ", dbSelection='" + getDbSelection() + "'" +
            ", tableName='" + getTableName() + "'" +
            ", hasDataMoreThan5Million='" + getHasDataMoreThan5Million() + "'" +
            ", isParallelizationReqd='" + getIsParallelizationReqd() + "'" +
            ", recoveryClass='" + getRecoveryClass() + "'" +
            ", orderId=" + getOrderId() +
            "}";
    }
}
