package com.shafaat.test.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * A HddlFile.
 */
@Entity
@Table(name = "hddl_file")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HddlFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "swid")
    private String swid;

    @Column(name = "db_name")
    private String dbName;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "status")
    private String status;

    @Lob
    @Column(name = "hddl")
    private String hddl;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public HddlFile id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSwid() {
        return this.swid;
    }

    public HddlFile swid(String swid) {
        this.setSwid(swid);
        return this;
    }

    public void setSwid(String swid) {
        this.swid = swid;
    }

    public String getDbName() {
        return this.dbName;
    }

    public HddlFile dbName(String dbName) {
        this.setDbName(dbName);
        return this;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public LocalDate getExpiryDate() {
        return this.expiryDate;
    }

    public HddlFile expiryDate(LocalDate expiryDate) {
        this.setExpiryDate(expiryDate);
        return this;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStatus() {
        return this.status;
    }

    public HddlFile status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHddl() {
        return this.hddl;
    }

    public HddlFile hddl(String hddl) {
        this.setHddl(hddl);
        return this;
    }

    public void setHddl(String hddl) {
        this.hddl = hddl;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HddlFile)) {
            return false;
        }
        return id != null && id.equals(((HddlFile) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HddlFile{" +
            "id=" + getId() +
            ", swid='" + getSwid() + "'" +
            ", dbName='" + getDbName() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", hddl='" + getHddl() + "'" +
            "}";
    }
}
