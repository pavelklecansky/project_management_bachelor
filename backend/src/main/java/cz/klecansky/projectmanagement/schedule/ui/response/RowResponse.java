package cz.klecansky.projectmanagement.schedule.ui.response;

import lombok.Data;

import java.util.UUID;

@Data
public class RowResponse {

    private UUID realId;

    private int id;

    private String label;
}
