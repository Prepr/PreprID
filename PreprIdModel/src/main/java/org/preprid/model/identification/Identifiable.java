package org.preprid.model.identification;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * Created by abouelna on 02/11/2014.
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
