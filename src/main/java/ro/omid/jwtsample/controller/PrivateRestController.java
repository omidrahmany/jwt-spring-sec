package ro.omid.jwtsample.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/private")
public class PrivateRestController {

    @GetMapping
    public Map<String, HttpStatus> getResult() {
        Map<String, HttpStatus> map = new HashMap<>();
        map.put("result", HttpStatus.ACCEPTED);
        return map;
    }
}
