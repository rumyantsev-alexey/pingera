package ru.job4j.pingera.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.pingera.clasez.ToolHandlers;
import ru.job4j.pingera.clasez.Tools;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
public class UtilityController {

    @GetMapping(value = "/gettools")
    public List<String> getTools() {
        List<String> result = new ArrayList<>();
        Tools[] r = Tools.values();
        for(int i=0; i < r.length; i++) {
            result.add(r[i].name());
        }
        return result;
    }

    @GetMapping(value = "/gettoolhandlers")
    public List<String> getToolHandlers() {
        List<String> result = new ArrayList<>();
        ToolHandlers[] r = ToolHandlers.values();
        for(int i=0; i < r.length; i++) {
            result.add(r[i].name());
        }
        return result;
    }

}
