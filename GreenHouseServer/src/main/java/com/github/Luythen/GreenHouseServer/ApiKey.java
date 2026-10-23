package com.github.Luythen.GreenHouseServer;

import java.util.Collection;

import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class ApiKey extends AbstractAuthenticationToken {

    private final String greenHouseId;

    public ApiKey (String greenHouseId, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.greenHouseId = greenHouseId;
        setAuthenticated(true);
    }

    @Override
    public @Nullable Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return greenHouseId;
    }

}
