package org.preprid.model.information;

import lombok.*;
import lombok.experimental.Accessors;
import org.preprid.model.identification.Identifiable;

import java.time.Instant;

/**
 * Created by abouelna on 02/11/2014.
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
