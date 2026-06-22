package com.techdispatch.my_springboot_platform.DTO;

import com.techdispatch.my_springboot_platform.Models.Availability;

public class AvailabilityDto {

    private Long id;
    private String dayOfWeek;
    private String startTime;
    private String endTime;
    private Long technicianId;
    private String technicianName;

    public AvailabilityDto() {}

    public static AvailabilityDto from(Availability availability) {
        AvailabilityDto dto = new AvailabilityDto();
        dto.id = availability.getId();
        dto.dayOfWeek = availability.getDayOfWeek();
        dto.startTime = availability.getStartTime();
        dto.endTime = availability.getEndTime();
        if (availability.getTechnician() != null) {
            dto.technicianId = availability.getTechnician().getId();
            dto.technicianName = availability.getTechnician().getFirstName() + " "
                    + availability.getTechnician().getLastName();
        }
        return dto;
    }

    public Long getId() {
        return id;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public Long getTechnicianId() {
        return technicianId;
    }

    public String getTechnicianName() {
        return technicianName;
    }

}
