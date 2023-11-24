package com.orik.adminapi.controller;

import com.orik.adminapi.entity.User;
import com.orik.adminapi.service.interfaces.RoleService;
import com.orik.adminapi.service.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@Controller
@RequestMapping("/system")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public String getUsers(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "4") int size,
                           @RequestParam(name = "sortField", defaultValue = "userId") String sortField,
                           @RequestParam(name = "sortOrder", defaultValue = "desc") String sortOrder) {
        model.addAttribute("usersPage", userService.getAllUsersSorted(page, size, sortField, sortOrder, roleService.getUserRole().getRoleId()));
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortOrder", sortOrder);
        return "admin/admin-page";
    }
    @GetMapping("/search-users")
    public String searchDriversByLastName(@RequestParam(name = "searchField") String searchField,
                                          @RequestParam(name = "searchValue") String searchValue,
                                          @RequestParam(name = "page", defaultValue = "0") int page,
                                          @RequestParam(name = "size", defaultValue = "4") int size,
                                          Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> usersPage = userService.findByFieldContainingIgnoreCase(searchField,searchValue, pageable);
        model.addAttribute("searchField", searchField);
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("usersPage", usersPage);
        return "admin/admin-page";
    }
}
