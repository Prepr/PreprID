package org.preprid.model.information;

import lombok.*;
import lombok.experimental.Accessors;
import org.preprid.model.identification.Identifiable;

import java.time.Instant;

/**
 * Base class for all modifiable information, which is basically everything
 * except the lastname and firstname at the time of registeration.
 */
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true, fluent = true)
@RequiredArgsConstructor
public abstract class VersionedInformation extends Identifiable {
    @NonNull protected final Instant utcTimeOfLastUpdate;
}
