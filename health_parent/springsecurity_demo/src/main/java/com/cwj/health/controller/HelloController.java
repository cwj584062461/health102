package com.cwj.health.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping("/add")
    @PreAuthorize("hasAnyAuthority('add')")
    public String add() {
        System.out.println("add.....");
        return null;
    }

    @RequestMapping("/update")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String update(){
        System.out.println("update...");
        return null;
    }

    @RequestMapping("/delete")
    @PreAuthorize("hasRole('ABC')")
    public String delete(){
        System.out.println("delete....");
        return null;
    }
}
