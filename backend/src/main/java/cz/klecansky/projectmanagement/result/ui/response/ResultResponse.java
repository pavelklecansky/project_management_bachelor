package cz.klecansky.projectmanagement.result.ui.response;

import lombok.Data;

import java.util.UUID;

@Data
public class ResultResponse {

    private UUID id;

    private String name;

    private String description;
}
