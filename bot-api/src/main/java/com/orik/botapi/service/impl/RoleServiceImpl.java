package com.orik.botapi.service.impl;

import com.orik.botapi.DAO.RoleRepository;
import com.orik.botapi.entity.Role;
import com.orik.botapi.exception.RoleNotFoundException;
import com.orik.botapi.service.interfaces.RoleService;
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
    public Role getUserRole() {
        return roleRepository.findById(3L)
                .orElseThrow(() -> new RoleNotFoundException("Role with id: 3 not found!"));
    }
}
