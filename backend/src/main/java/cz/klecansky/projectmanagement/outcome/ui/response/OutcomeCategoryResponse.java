package cz.klecansky.projectmanagement.outcome.ui.response;

import lombok.Data;

import java.util.UUID;

@Data
public class OutcomeCategoryResponse {

    private UUID id;

    private String name;

    private String description;
}
