package com.caremyhome.dto;

import lombok.Data;

@Data
public class UserStatsDTO {
    private long tenants;
    private long agents;
    private long owners;
    private long inquiries;

    public UserStatsDTO() {}

    public UserStatsDTO(long tenants, long agents, long owners, long inquiries) {
        this.tenants = tenants;
        this.agents = agents;
        this.owners = owners;
        this.inquiries = inquiries;
    }
}
