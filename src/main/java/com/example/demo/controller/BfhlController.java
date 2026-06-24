package com.example.demo.controller;

import com.example.demo.dto.RequestDto;
import com.example.demo.dto.ResponseDto;
import com.example.demo.service.BfhlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bfhl")
public class BfhlController {

    @Autowired
    private BfhlService bfhlService;

    @PostMapping
    public ResponseDto process(@RequestBody RequestDto request) {
        return bfhlService.process(request);
    }

    @GetMapping
    public java.util.Map<String, Integer> getOperationCode() {
        return java.util.Map.of("operation_code", 1);
    }
}