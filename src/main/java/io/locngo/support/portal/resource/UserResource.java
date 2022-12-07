package io.locngo.support.portal.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserResource {
    
    @GetMapping
    public String helloWorld() {
        return "Hello World!";
    }
}
