package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class RequestDto {

    @NotNull(message = "Data field cannot be null")
    private List<String> data;

    public RequestDto() {
    }

    public RequestDto(List<String> data) {
        this.data = data;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}