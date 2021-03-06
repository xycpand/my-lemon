package com.mossle.model.persistence.domain;

// Generated by Hibernate Tools
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ModelField .
 * 
 * @author Lingo
 */
@Entity
@Table(name = "MODEL_FIELD")
public class ModelField implements java.io.Serializable {
    private static final long serialVersionUID = 0L;

    /** null. */
    private Long id;

    /** null. */
    private ModelInfo modelInfo;

    /** null. */
    private String code;

    /** null. */
    private String name;

    /** null. */
    private String type;

    /** null. */
    private Integer priority;

    /** null. */
    private String searchable;

    /** null. */
    private String displayable;

    /** null. */
    private String viewList;

    /** null. */
    private String viewForm;

    /** null. */
    private String tenantId;

    public ModelField() {
    }

    public ModelField(ModelInfo modelInfo, String code, String name,
            String type, Integer priority, String searchable,
            String displayable, String viewList, String viewForm,
            String tenantId) {
        this.modelInfo = modelInfo;
        this.code = code;
        this.name = name;
        this.type = type;
        this.priority = priority;
        this.searchable = searchable;
        this.displayable = displayable;
        this.viewList = viewList;
        this.viewForm = viewForm;
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
    @JoinColumn(name = "INFO_ID")
    public ModelInfo getModelInfo() {
        return this.modelInfo;
    }

    /**
     * @param modelInfo
     *            null.
     */
    public void setModelInfo(ModelInfo modelInfo) {
        this.modelInfo = modelInfo;
    }

    /** @return null. */
    @Column(name = "CODE", length = 200)
    public String getCode() {
        return this.code;
    }

    /**
     * @param code
     *            null.
     */
    public void setCode(String code) {
        this.code = code;
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
    @Column(name = "TYPE", length = 200)
    public String getType() {
        return this.type;
    }

    /**
     * @param type
     *            null.
     */
    public void setType(String type) {
        this.type = type;
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
    @Column(name = "SEARCHABLE", length = 10)
    public String getSearchable() {
        return this.searchable;
    }

    /**
     * @param searchable
     *            null.
     */
    public void setSearchable(String searchable) {
        this.searchable = searchable;
    }

    /** @return null. */
    @Column(name = "DISPLAYABLE", length = 10)
    public String getDisplayable() {
        return this.displayable;
    }

    /**
     * @param displayable
     *            null.
     */
    public void setDisplayable(String displayable) {
        this.displayable = displayable;
    }

    /** @return null. */
    @Column(name = "VIEW_LIST", length = 10)
    public String getViewList() {
        return this.viewList;
    }

    /**
     * @param viewList
     *            null.
     */
    public void setViewList(String viewList) {
        this.viewList = viewList;
    }

    /** @return null. */
    @Column(name = "VIEW_FORM", length = 10)
    public String getViewForm() {
        return this.viewForm;
    }

    /**
     * @param viewForm
     *            null.
     */
    public void setViewForm(String viewForm) {
        this.viewForm = viewForm;
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
