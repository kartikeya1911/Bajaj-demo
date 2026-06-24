package com.example.demo;

import com.example.demo.dto.RequestDto;
import com.example.demo.dto.ResponseDto;
import com.example.demo.service.BfhlServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BfhlServiceTest {

    @Test
    void testProcess() {

        BfhlServiceImpl service = new BfhlServiceImpl();

        RequestDto request = new RequestDto();

        request.setData(
                List.of("a", "1", "334", "4", "R", "$")
        );

        ResponseDto response = service.process(request);

        assertEquals("339", response.getSum());

        assertEquals(1,
                response.getOddNumbers().size());

        assertEquals(2,
                response.getEvenNumbers().size());

        assertEquals(2,
                response.getAlphabets().size());

        assertEquals(1,
                response.getSpecialCharacters().size());

        assertEquals("Ra",
                response.getConcatString());
    }
}