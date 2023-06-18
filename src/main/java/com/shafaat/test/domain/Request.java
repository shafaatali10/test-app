package com.shafaat.test.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Request.
 */
@Entity
@Table(name = "request")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "rfc")
    private String rfc;

    @Column(name = "swid")
    private String swid;

    @Column(name = "status")
    private String status;

    @Column(name = "is_draft")
    private Boolean isDraft;

    @Column(name = "request_link")
    private String requestLink;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Request id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRfc() {
        return this.rfc;
    }

    public Request rfc(String rfc) {
        this.setRfc(rfc);
        return this;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getSwid() {
        return this.swid;
    }

    public Request swid(String swid) {
        this.setSwid(swid);
        return this;
    }

    public void setSwid(String swid) {
        this.swid = swid;
    }

    public String getStatus() {
        return this.status;
    }

    public Request status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getIsDraft() {
        return this.isDraft;
    }

    public Request isDraft(Boolean isDraft) {
        this.setIsDraft(isDraft);
        return this;
    }

    public void setIsDraft(Boolean isDraft) {
        this.isDraft = isDraft;
    }

    public String getRequestLink() {
        return this.requestLink;
    }

    public Request requestLink(String requestLink) {
        this.setRequestLink(requestLink);
        return this;
    }

    public void setRequestLink(String requestLink) {
        this.requestLink = requestLink;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Request)) {
            return false;
        }
        return id != null && id.equals(((Request) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Request{" +
            "id=" + getId() +
            ", rfc='" + getRfc() + "'" +
            ", swid='" + getSwid() + "'" +
            ", status='" + getStatus() + "'" +
            ", isDraft='" + getIsDraft() + "'" +
            ", requestLink='" + getRequestLink() + "'" +
            "}";
    }
}
