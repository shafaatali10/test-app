package com.shafaat.test.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Order.
 */
@Entity
@Table(name = "jhi_order")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "request_id")
    private Long requestId;

    @Column(name = "description")
    private String description;

    @Column(name = "is_approved")
    private Boolean isApproved;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Order id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderType() {
        return this.orderType;
    }

    public Order orderType(String orderType) {
        this.setOrderType(orderType);
        return this;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Long getRequestId() {
        return this.requestId;
    }

    public Order requestId(Long requestId) {
        this.setRequestId(requestId);
        return this;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getDescription() {
        return this.description;
    }

    public Order description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsApproved() {
        return this.isApproved;
    }

    public Order isApproved(Boolean isApproved) {
        this.setIsApproved(isApproved);
        return this;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return id != null && id.equals(((Order) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", orderType='" + getOrderType() + "'" +
            ", requestId=" + getRequestId() +
            ", description='" + getDescription() + "'" +
            ", isApproved='" + getIsApproved() + "'" +
            "}";
    }
}
