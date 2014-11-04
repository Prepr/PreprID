package org.preprid.model.information;

import lombok.*;
import lombok.experimental.Accessors;
import org.preprid.model.information.VersionedInformation;

import java.net.URI;
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
public class LegalInformation extends VersionedInformation {

    /**
     * Created by abouelna on 02/11/2014.
     */
    @ToString
    @EqualsAndHashCode
    @Getter
    @Setter
    @Accessors(chain = true, fluent = true)
    @RequiredArgsConstructor
    public static class AgreementAcceptance {
        @NonNull private final URI agreementUri;

        @NonNull private final Instant utcTimeOfAcceptance;
    }

    @NonNull private final Iterable<AgreementAcceptance> acceptedAgreements;
}
