package org.preprid.model.identification;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * Base class for all classes in the PreprId model package, except the PreprId class.
 */
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true, fluent = true)
@RequiredArgsConstructor
public abstract class Identifiable {
    @NonNull protected final PreprId preprId;
}
