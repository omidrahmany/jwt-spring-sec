package ro.omid.jwtsample.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
public class PublicRestController {

    @GetMapping
    public Map<String, HttpStatus> getResult() {
        Map<String, HttpStatus> map = new HashMap<>();
        map.put("result", HttpStatus.I_AM_A_TEAPOT);
        return map;
    }
}
