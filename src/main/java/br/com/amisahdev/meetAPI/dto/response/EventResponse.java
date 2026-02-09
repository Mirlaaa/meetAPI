package br.com.amisahdev.meetAPI.dto.response;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record EventResponse(
    UUID id,
    String title,
    String description,
    Boolean active,
    Date createdAt,
    Date updatedAt,
//    List<OrganizerResponse> organizers,
//    List<ActivityResponse> activities,

    long totalSubscriptions

) { }
