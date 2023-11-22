package com.orik.botapi.service.interfaces;

import com.orik.botapi.entity.Role;
import com.orik.botapi.exception.RoleNotFoundException;

public interface RoleService {
    Role getUserRole();
}
