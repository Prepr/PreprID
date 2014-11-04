package org.preprid.model.identification;

import lombok.*;
import lombok.experimental.Accessors;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by abouelna on 02/11/2014.
 */
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true, fluent = true)
@RequiredArgsConstructor
public class PreprId {

    public static final String HTTP_PROTOCOL = "http://";

    public static PreprId fromURI(final URI uri) {

        final URL creationAPIEndPoint;
        try {
            creationAPIEndPoint = new URL(HTTP_PROTOCOL + uri.getAuthority());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Bad URI authority: " + uri.getAuthority(), e);
        }

        final String[] names = uri.getPath().split("/");

        if (names.length != 3) { // empty string, last name, first name
            throw new IllegalArgumentException("Expected URI path to be 'lastname/firstname' but found: " + uri.getPath());
        }

        final String disambiguation = uri.getFragment();

        final PreprId retval = new PreprId(creationAPIEndPoint, names[1], names[2], disambiguation);

        return retval;
    }

    @NonNull private final URL creationAPIEndPoint;

    @NonNull private final String lastName;

    @NonNull private final String firstName;

    @NonNull private final String disambiguation;

    public URL asURL() {
        final StringBuilder spec = new StringBuilder('/').append(lastName())
                                                         .append(firstName());

        if (!disambiguation.isEmpty()) {
            spec.append('#').append(disambiguation());
        }

        try {
            return new URL(creationAPIEndPoint(), spec.toString());
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Cannot create a URL out of PreprId " + toString());
        }
    }

    public URI asURI() {
        try {
            return asURL().toURI();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Cannot create a URI out of PreprId " + toString());
        }
    }

}
