package cz.klecansky.projectmanagement.schedule.ui.response;

import java.util.UUID;
import lombok.Data;

@Data
public class RowResponse {

    private UUID realId;

    private int id;

    private String label;
}
