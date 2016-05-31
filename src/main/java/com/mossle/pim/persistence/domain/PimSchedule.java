package com.mossle.pim.persistence.domain;

// Generated by Hibernate Tools
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PimSchedule .
 * 
 * @author Lingo
 */
@Entity
@Table(name = "PIM_SCHEDULE")
public class PimSchedule implements java.io.Serializable {
    private static final long serialVersionUID = 0L;

    /** null. */
    private Long id;

    /** null. */
    private String name;

    /** null. */
    private String location;

    /** null. */
    private String content;

    /** null. */
    private Integer type;

    /** null. */
    private Date startTime;

    /** null. */
    private Date endTime;

    /** null. */
    private String alertTime;

    /** null. */
    private String userId;

    /** null. */
    private Integer status;

    /** null. */
    private String ref;

    /** null. */
    private String tenantId;

    /** . */
    private Set<PimScheduleParticipant> pimScheduleParticipants = new HashSet<PimScheduleParticipant>(
            0);

    public PimSchedule() {
    }

    public PimSchedule(String name, String location, String content,
            Integer type, Date startTime, Date endTime, String alertTime,
            String userId, Integer status, String ref, String tenantId,
            Set<PimScheduleParticipant> pimScheduleParticipants) {
        this.name = name;
        this.location = location;
        this.content = content;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.alertTime = alertTime;
        this.userId = userId;
        this.status = status;
        this.ref = ref;
        this.tenantId = tenantId;
        this.pimScheduleParticipants = pimScheduleParticipants;
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
    @Column(name = "LOCATION", length = 200)
    public String getLocation() {
        return this.location;
    }

    /**
     * @param location
     *            null.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /** @return null. */
    @Column(name = "CONTENT", length = 200)
    public String getContent() {
        return this.content;
    }

    /**
     * @param content
     *            null.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /** @return null. */
    @Column(name = "TYPE")
    public Integer getType() {
        return this.type;
    }

    /**
     * @param type
     *            null.
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /** @return null. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_TIME", length = 26)
    public Date getStartTime() {
        return this.startTime;
    }

    /**
     * @param startTime
     *            null.
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /** @return null. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_TIME", length = 26)
    public Date getEndTime() {
        return this.endTime;
    }

    /**
     * @param endTime
     *            null.
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /** @return null. */
    @Column(name = "ALERT_TIME", length = 50)
    public String getAlertTime() {
        return this.alertTime;
    }

    /**
     * @param alertTime
     *            null.
     */
    public void setAlertTime(String alertTime) {
        this.alertTime = alertTime;
    }

    /** @return null. */
    @Column(name = "USER_ID", length = 64)
    public String getUserId() {
        return this.userId;
    }

    /**
     * @param userId
     *            null.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /** @return null. */
    @Column(name = "STATUS")
    public Integer getStatus() {
        return this.status;
    }

    /**
     * @param status
     *            null.
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /** @return null. */
    @Column(name = "REF", length = 100)
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

    /** @return . */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pimSchedule")
    public Set<PimScheduleParticipant> getPimScheduleParticipants() {
        return this.pimScheduleParticipants;
    }

    /**
     * @param pimScheduleParticipants
     *            .
     */
    public void setPimScheduleParticipants(
            Set<PimScheduleParticipant> pimScheduleParticipants) {
        this.pimScheduleParticipants = pimScheduleParticipants;
    }
}
