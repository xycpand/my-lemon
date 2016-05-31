package com.mossle.bpm.persistence.domain;

// Generated by Hibernate Tools
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * BpmInstance .
 * 
 * @author Lingo
 */
@Entity
@Table(name = "BPM_INSTANCE")
public class BpmInstance implements java.io.Serializable {
    private static final long serialVersionUID = 0L;

    /** null. */
    private Long id;

    /** null. */
    private BpmProcess bpmProcess;

    /** null. */
    private String name;

    /** null. */
    private String businessKey;

    /** null. */
    private String ref;

    /** null. */
    private Date createTime;

    /** null. */
    private String initiator;

    /** null. */
    private Integer priority;

    /** null. */
    private String tenantId;

    public BpmInstance() {
    }

    public BpmInstance(BpmProcess bpmProcess, String name, String businessKey,
            String ref, Date createTime, String initiator, Integer priority,
            String tenantId) {
        this.bpmProcess = bpmProcess;
        this.name = name;
        this.businessKey = businessKey;
        this.ref = ref;
        this.createTime = createTime;
        this.initiator = initiator;
        this.priority = priority;
        this.tenantId = tenantId;
    }

    /** @return null. */
    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    /**
     * @param id
     *            null.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return null. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROCESS_ID")
    public BpmProcess getBpmProcess() {
        return this.bpmProcess;
    }

    /**
     * @param bpmProcess
     *            null.
     */
    public void setBpmProcess(BpmProcess bpmProcess) {
        this.bpmProcess = bpmProcess;
    }

    /** @return null. */
    @Column(name = "NAME", length = 200)
    public String getName() {
        return this.name;
    }

    /**
     * @param name
     *            null.
     */
    public void setName(String name) {
        this.name = name;
    }

    /** @return null. */
    @Column(name = "BUSINESS_KEY", length = 64)
    public String getBusinessKey() {
        return this.businessKey;
    }

    /**
     * @param businessKey
     *            null.
     */
    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    /** @return null. */
    @Column(name = "REF", length = 64)
    public String getRef() {
        return this.ref;
    }

    /**
     * @param ref
     *            null.
     */
    public void setRef(String ref) {
        this.ref = ref;
    }

    /** @return null. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_TIME", length = 26)
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * @param createTime
     *            null.
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** @return null. */
    @Column(name = "INITIATOR", length = 64)
    public String getInitiator() {
        return this.initiator;
    }

    /**
     * @param initiator
     *            null.
     */
    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    /** @return null. */
    @Column(name = "PRIORITY")
    public Integer getPriority() {
        return this.priority;
    }

    /**
     * @param priority
     *            null.
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /** @return null. */
    @Column(name = "TENANT_ID", length = 64)
    public String getTenantId() {
        return this.tenantId;
    }

    /**
     * @param tenantId
     *            null.
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
