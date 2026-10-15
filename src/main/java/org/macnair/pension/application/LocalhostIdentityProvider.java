package org.macnair.pension.application;

import io.quarkus.security.identity.IdentityProvider;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.request.TrustedAuthenticationRequest;
import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.smallrye.mutiny.Uni;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LocalhostIdentityProvider implements IdentityProvider<TrustedAuthenticationRequest> {

    @Override
    public Class<TrustedAuthenticationRequest> getRequestType() {
        return TrustedAuthenticationRequest.class;
    }

    @Override
    public Uni<SecurityIdentity> authenticate(TrustedAuthenticationRequest request,
                                              AuthenticationRequestContext context) {

        // Only auto-login for real localhost
        if ("127.0.0.1".equals(request.getPrincipal())) {
            QuarkusSecurityIdentity identity = QuarkusSecurityIdentity.builder()
                    .setPrincipal(() -> "localhost-user")
                    .addRole("admin")
                    .build();

            return Uni.createFrom().item(identity);
        }

        return Uni.createFrom().nullItem();
    }
}
