package com.orik.adminapi.service.impl;

import com.orik.adminapi.DAO.RoleRepository;
import com.orik.adminapi.entity.Role;
import com.orik.adminapi.exception.RoleNotFoundException;
import com.orik.adminapi.service.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getAdminRole() throws RoleNotFoundException{
        return roleRepository.findById(2L)
                .orElseThrow(() -> new RoleNotFoundException("Role with id: 2 not found!"));
    }

    @Override
    public Role getUserRole() throws RoleNotFoundException {
        return roleRepository.findById(3L)
                .orElseThrow(() -> new RoleNotFoundException("Role with id: 3 not found!"));
    }
}
