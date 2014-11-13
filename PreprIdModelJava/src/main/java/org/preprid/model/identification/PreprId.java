package org.preprid.model.identification;

import lombok.*;
import lombok.experimental.Accessors;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * The PreprId is a URI that can also serve as a URL,
 * it has the format:
 * http://[api-end-point]/[last-name]/[first-name]/[disambiguation]/
 */
@ToString(callSuper = true)
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true, fluent = true)
@RequiredArgsConstructor
public class PreprId {

    public static final String HTTP_PROTOCOL() { return "http"; }

    @SneakyThrows(MalformedURLException.class)
    public static final URL DEFAULT_PREPRID_ENDPOINT() { return new URL("http://api.preprid.org/"); }

    public static PreprId fromURI(final URI uri) {

        final URL creationAPIEndPoint;
        try {
            creationAPIEndPoint = new URL(HTTP_PROTOCOL() + "://" + uri.getAuthority());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Bad URI authority: " + uri.getAuthority(), e);
        }

        final String path = uri.getPath();

        return fromURIComponents(creationAPIEndPoint, path);
    }

    /**
     * Creates a PreprId based off the {@link org.preprid.model.identification.PreprId#DEFAULT_PREPRID_ENDPOINT()}.
     *
     * @param path String: /lastname/firstname/disambiguation
     * @return PreprId created by a call to {@link org.preprid.model.identification.PreprId#fromURIComponents(java.net.URL, String)}.
     */
    public static final PreprId fromPath(@NonNull final String path) {
        return fromURIComponents(DEFAULT_PREPRID_ENDPOINT(), path);
    }

    public static final PreprId fromURIComponents(@NonNull final URL creationAPIEndPoint,
                                                  @NonNull final String path) {

        assert (path.charAt(0) == '/');

        final String[] names = path.split("/");

        if (names.length < 4) { // empty string, last name, first name, disambiguation, (empty string)?
            throw new IllegalArgumentException("Expected URI path to be '/lastname/firstname/disambiguation' but found: "
                                               + path);
        }

        final PreprId retval = new PreprId(creationAPIEndPoint, names[1], names[2], names[3]);

        return retval;
    }

    @NonNull private final URL creationAPIEndPoint;

    @NonNull private final String lastName;

    @NonNull private final String firstName;

    @NonNull private final String disambiguation;

    public URL asURL() {
        final String spec = String.format("/%s/%s/%s/", lastName(), firstName(), disambiguation());

        try {
            return new URL(creationAPIEndPoint(), spec);
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
