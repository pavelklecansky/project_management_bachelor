package cz.klecansky.projectmanagement.outcome.coverters;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.outcome.service.OutcomeService;
import cz.klecansky.projectmanagement.outcome.shared.OutcomeCommand;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Component
public class UUIDToOutcomeConverter implements Converter<UUID, OutcomeCommand> {

    @NonNull
    OutcomeService outcomeService;

    @Override
    public OutcomeCommand convert(UUID source) {
        return outcomeService.get(source).orElseThrow(() -> new NoSuchElementFoundException("Outcome was not found."));
    }
}
