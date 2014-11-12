package org.preprid.model.information;

import lombok.*;
import lombok.experimental.Accessors;
import org.preprid.model.identification.Identifiable;

import java.time.Instant;

/**
 * Base class for all modifiable information, which is basically everything
 * except the lastname and firstname at the time of registeration.
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
public abstract class VersionedInformation extends Identifiable {
    @NonNull protected Instant utcTimeOfLastUpdate;
}
