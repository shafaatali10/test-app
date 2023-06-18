package com.shafaat.test.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A MetaData.
 */
@Entity
@Table(name = "meta_data")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MetaData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "stid_class")
    private String stidClass;

    @Column(name = "stid_column_name")
    private String stidColumnName;

    @Column(name = "data_level")
    private String dataLevel;

    @Column(name = "initial_load_type")
    private String initialLoadType;

    @Column(name = "partition_schema")
    private String partitionSchema;

    @Column(name = "order_id")
    private Long orderId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MetaData id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStidClass() {
        return this.stidClass;
    }

    public MetaData stidClass(String stidClass) {
        this.setStidClass(stidClass);
        return this;
    }

    public void setStidClass(String stidClass) {
        this.stidClass = stidClass;
    }

    public String getStidColumnName() {
        return this.stidColumnName;
    }

    public MetaData stidColumnName(String stidColumnName) {
        this.setStidColumnName(stidColumnName);
        return this;
    }

    public void setStidColumnName(String stidColumnName) {
        this.stidColumnName = stidColumnName;
    }

    public String getDataLevel() {
        return this.dataLevel;
    }

    public MetaData dataLevel(String dataLevel) {
        this.setDataLevel(dataLevel);
        return this;
    }

    public void setDataLevel(String dataLevel) {
        this.dataLevel = dataLevel;
    }

    public String getInitialLoadType() {
        return this.initialLoadType;
    }

    public MetaData initialLoadType(String initialLoadType) {
        this.setInitialLoadType(initialLoadType);
        return this;
    }

    public void setInitialLoadType(String initialLoadType) {
        this.initialLoadType = initialLoadType;
    }

    public String getPartitionSchema() {
        return this.partitionSchema;
    }

    public MetaData partitionSchema(String partitionSchema) {
        this.setPartitionSchema(partitionSchema);
        return this;
    }

    public void setPartitionSchema(String partitionSchema) {
        this.partitionSchema = partitionSchema;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public MetaData orderId(Long orderId) {
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
        if (!(o instanceof MetaData)) {
            return false;
        }
        return id != null && id.equals(((MetaData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MetaData{" +
            "id=" + getId() +
            ", stidClass='" + getStidClass() + "'" +
            ", stidColumnName='" + getStidColumnName() + "'" +
            ", dataLevel='" + getDataLevel() + "'" +
            ", initialLoadType='" + getInitialLoadType() + "'" +
            ", partitionSchema='" + getPartitionSchema() + "'" +
            ", orderId=" + getOrderId() +
            "}";
    }
}
