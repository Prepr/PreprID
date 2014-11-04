package org.preprid.model.metadata;

import lombok.*;
import lombok.experimental.Accessors;
import org.preprid.model.identification.Identifiable;

import java.time.Instant;

/**
 * Created by abouelna on 03/11/2014.
 */
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true, fluent = true)
@RequiredArgsConstructor
public class AccountMetaData extends Identifiable {
    @NonNull private final Instant utcTimeOfCreation;
}
