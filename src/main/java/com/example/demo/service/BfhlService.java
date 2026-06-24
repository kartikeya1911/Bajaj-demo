package com.example.demo.service;

import com.example.demo.dto.RequestDto;
import com.example.demo.dto.ResponseDto;

public interface BfhlService {

    ResponseDto processData(RequestDto requestDto);
}
