package com.orik.adminapi.service.interfaces;

import com.orik.adminapi.entity.Role;
import com.orik.adminapi.exception.RoleNotFoundException;

public interface RoleService {
    Role getAdminRole() throws RoleNotFoundException;

}
