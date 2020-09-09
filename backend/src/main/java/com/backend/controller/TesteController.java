package com.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 *
 * @author hudson.magalhaes
 */
@RestController
@RequestMapping("/api/teste")
public class TesteController {

    public static final Logger logger = LoggerFactory.getLogger(TesteController.class);

    @GetMapping(value = "/{id}")
    public String getId(@PathVariable("id") Long id) {
        return "GetId=" + id;
    }

    @PostMapping
    public String postId() {
        return "PostId=";
    }

    @PutMapping()
    public String putId() {
        return "PutId=";
    }

    @DeleteMapping(value = "/{id}")
    public String deleteId(@PathVariable("id") Long id) {
        return "DeleteId=" + id;
    }

}
