package com.svop.service.secutity;

import com.svop.tables.Users.Role;
import com.svop.tables.Users.RolePermissions;
import com.svop.tables.Users.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public final class JwtUserFactory {
    public JwtUserFactory(){}
    public static SvopUserDetails create(User user)
    {
        List<GrantedAuthority> grantedAuthorities=new ArrayList<>();
        if (user.getRoles()!=null)
        {
            grantedAuthorities=grantedAuthorities(getAuthorities(user.getRoles()));
        }
    return new SvopUserDetails(user.getId(),user.getUsername(),user.getPassword(),user.getLocale(),grantedAuthorities);
    }
    public static List<GrantedAuthority> grantedAuthorities(List<String> list)
    {
        List<GrantedAuthority> grantedAuthorities=new ArrayList<>(list.size());
        for(String auth:list)
        {
            grantedAuthorities.add(new SimpleGrantedAuthority(auth));
        }
        return grantedAuthorities;
    }
    public static List<String> getAuthorities(Collection<Role> roles)
    {
        List<String> list=new ArrayList<>(roles.size());
        for(Role role:roles)
        {
            list.add(role.getName());
            Set<RolePermissions> rolePermissions=role.getPermissions();
            if (rolePermissions!=null)
            {
                for (RolePermissions permissions:rolePermissions)
                {
                    list.add(permissions.getPermission().getName());
                }
            }
        }
        return list;
    }
}
