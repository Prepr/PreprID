package org.preprid.model.metadata;

import lombok.*;
import lombok.experimental.Accessors;
import org.preprid.model.identification.Identifiable;

import java.time.Instant;

/**
 * Information pertaining to the account, not the account holder.
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Accessors(chain = true, fluent = true)
@RequiredArgsConstructor
public class AccountMetaData extends Identifiable {
    @NonNull private final Instant utcTimeOfCreation;
}
