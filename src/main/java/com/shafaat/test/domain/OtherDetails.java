package com.shafaat.test.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A OtherDetails.
 */
@Entity
@Table(name = "other_details")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OtherDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "mandator_column")
    private String mandatorColumn;

    @Column(name = "is_hub_usage_reqd")
    private Boolean isHubUsageReqd;

    @Column(name = "insert_chars")
    private String insertChars;

    @Column(name = "table_access_method")
    private String tableAccessMethod;

    @Column(name = "one_wmp_view")
    private String oneWmpView;

    @Column(name = "order_id")
    private Long orderId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OtherDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMandatorColumn() {
        return this.mandatorColumn;
    }

    public OtherDetails mandatorColumn(String mandatorColumn) {
        this.setMandatorColumn(mandatorColumn);
        return this;
    }

    public void setMandatorColumn(String mandatorColumn) {
        this.mandatorColumn = mandatorColumn;
    }

    public Boolean getIsHubUsageReqd() {
        return this.isHubUsageReqd;
    }

    public OtherDetails isHubUsageReqd(Boolean isHubUsageReqd) {
        this.setIsHubUsageReqd(isHubUsageReqd);
        return this;
    }

    public void setIsHubUsageReqd(Boolean isHubUsageReqd) {
        this.isHubUsageReqd = isHubUsageReqd;
    }

    public String getInsertChars() {
        return this.insertChars;
    }

    public OtherDetails insertChars(String insertChars) {
        this.setInsertChars(insertChars);
        return this;
    }

    public void setInsertChars(String insertChars) {
        this.insertChars = insertChars;
    }

    public String getTableAccessMethod() {
        return this.tableAccessMethod;
    }

    public OtherDetails tableAccessMethod(String tableAccessMethod) {
        this.setTableAccessMethod(tableAccessMethod);
        return this;
    }

    public void setTableAccessMethod(String tableAccessMethod) {
        this.tableAccessMethod = tableAccessMethod;
    }

    public String getOneWmpView() {
        return this.oneWmpView;
    }

    public OtherDetails oneWmpView(String oneWmpView) {
        this.setOneWmpView(oneWmpView);
        return this;
    }

    public void setOneWmpView(String oneWmpView) {
        this.oneWmpView = oneWmpView;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public OtherDetails orderId(Long orderId) {
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
        if (!(o instanceof OtherDetails)) {
            return false;
        }
        return id != null && id.equals(((OtherDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OtherDetails{" +
            "id=" + getId() +
            ", mandatorColumn='" + getMandatorColumn() + "'" +
            ", isHubUsageReqd='" + getIsHubUsageReqd() + "'" +
            ", insertChars='" + getInsertChars() + "'" +
            ", tableAccessMethod='" + getTableAccessMethod() + "'" +
            ", oneWmpView='" + getOneWmpView() + "'" +
            ", orderId=" + getOrderId() +
            "}";
    }
}
