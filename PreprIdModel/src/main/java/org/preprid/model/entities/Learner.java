package org.preprid.model.entities;

import lombok.*;
import lombok.experimental.Accessors;
import org.preprid.model.identification.Identifiable;

/**
 * Created by abouelna on 02/11/2014.
 */
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true, fluent = true)
@RequiredArgsConstructor
public class Learner extends Identifiable {
}
