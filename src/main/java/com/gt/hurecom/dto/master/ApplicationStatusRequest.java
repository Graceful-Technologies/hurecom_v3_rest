package com.gt.hurecom.dto.master;

public class ApplicationStatusRequest {

    private Long stageId;

    private String code;

    private String name;

    private Integer sequence;

    private boolean terminal;

    private boolean followupRequired;

    private boolean active;

    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public boolean isTerminal() {
        return terminal;
    }

    public void setTerminal(boolean terminal) {
        this.terminal = terminal;
    }

    public boolean isFollowupRequired() {
        return followupRequired;
    }

    public void setFollowupRequired(boolean followupRequired) {
        this.followupRequired = followupRequired;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
