package com.caremyhome.dto;

@Data
public class TenantDTO {
    private String name;
    private String email;
    private PropertyDTO property;
    private List<MaintenanceRequest> maintenanceRequests;

    @Data
    public static class PropertyDTO {
        private UUID id;
        private String title;
        private String location;
        private String unit;
        private int rent;
        private LocalDate dueDate;

        public PropertyDTO(Property property) {
            this.id = property.getId();
            this.title = property.getTitle();
            this.location = property.getCity() + ", " + property.getState();
            this.unit = property.getUnit();
            this.rent = property.getRent();
            this.dueDate = property.getNextDueDate();
        }
    }
}
