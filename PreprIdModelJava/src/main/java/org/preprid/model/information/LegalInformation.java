package org.preprid.model.information;

import lombok.*;
import lombok.experimental.Accessors;
import org.preprid.model.information.VersionedInformation;

import java.net.URI;
import java.net.URL;
import java.time.Instant;

/**
 * Bits and pieces of information to be pulled into different entities as needed.
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Accessors(chain = true, fluent = true)
@RequiredArgsConstructor
public class LegalInformation extends VersionedInformation {

    /**
     * Records the URI identifier of the agreement, the time it was accepted,
     * and the URL of the page through which the agreement was accepted.
     * This information should be enough to pull out the logs of the agreement.
     */
    @ToString(callSuper = true)
    @EqualsAndHashCode
    @Getter
    @Setter
    @Accessors(chain = true, fluent = true)
    @RequiredArgsConstructor
    public static class AgreementAcceptance {
        @NonNull private final URI agreementUri;

        @NonNull private final Instant utcTimeOfAcceptance;

        @NonNull private final URL agreementAcceptancePage;
    }

    @NonNull private final Iterable<AgreementAcceptance> acceptedAgreements;
}
