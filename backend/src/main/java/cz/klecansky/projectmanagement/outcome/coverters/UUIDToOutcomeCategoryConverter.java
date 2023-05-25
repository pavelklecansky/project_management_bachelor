package cz.klecansky.projectmanagement.outcome.coverters;

import cz.klecansky.projectmanagement.core.exception.NoSuchElementFoundException;
import cz.klecansky.projectmanagement.outcome.service.OutcomeCategoryService;
import cz.klecansky.projectmanagement.outcome.shared.OutcomeCategoryCommand;
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
public class UUIDToOutcomeCategoryConverter implements Converter<UUID, OutcomeCategoryCommand> {

    @NonNull OutcomeCategoryService outcomeCategoryService;

    @Override
    public OutcomeCategoryCommand convert(UUID source) {
        return outcomeCategoryService.get(source).orElseThrow(() -> new NoSuchElementFoundException("Project was not found."));
    }
}
