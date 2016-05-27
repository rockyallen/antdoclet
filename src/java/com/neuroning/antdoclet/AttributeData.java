package com.neuroning.antdoclet;

/**
 *
 * @author rocky
 */
class AttributeData {

    private String name;
    private String description;
    private String defaultValue;
    private String type;
    private String comment;
    //private boolean required;
    private String requiredComment;
    private String notRequiredComment;

    /**
     * @return the description
     */
    public String getShortDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setShortDescription(String description) {
        this.description = description;
    }

    /**
     * @return the defaultValue
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * @param defaultValue the defaultValue to set
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the requiredComment
     */
    public String getRequiredComment() {
        return requiredComment;
    }

    /**
     * @param requiredComment the requiredComment to set
     */
    public void setRequiredComment(String requiredComment) {
        this.requiredComment = requiredComment;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "attribute={" + name + "," + type + "," + description + "}";
    }

    /**
     * @return the notRequiredComment
     */
    public String getNotRequiredComment() {
        return notRequiredComment;
    }

    /**
     * @param notRequiredComment the notRequiredComment to set
     */
    public void setNotRequiredComment(String notRequiredComment) {
        this.notRequiredComment = notRequiredComment;
    }
}
