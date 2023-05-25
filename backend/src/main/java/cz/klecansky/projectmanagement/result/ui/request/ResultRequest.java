package cz.klecansky.projectmanagement.result.ui.request;

import lombok.Data;

import java.util.UUID;

@Data
public class ResultRequest {

    private String name;

    private String description;

    private UUID outcome;
}
