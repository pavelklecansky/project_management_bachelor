package cz.klecansky.projectmanagement.phase.converters;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.phase.service.PhaseService;
import cz.klecansky.projectmanagement.phase.shared.PhaseCommand;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Component
public class UUIDToPhaseCommandConverter implements Converter<UUID, PhaseCommand> {

    @NonNull PhaseService phaseService;

    @Override
    public PhaseCommand convert(UUID source) {
        return phaseService.get(source).orElseThrow(() -> new NoSuchElementFoundException("Phase was not found."));
    }
}
