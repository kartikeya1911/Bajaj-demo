package com.example.demo;

import com.example.demo.dto.RequestDto;
import com.example.demo.dto.ResponseDto;
import com.example.demo.service.BfhlService;
import com.example.demo.service.BfhlServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BfhlServiceTest {

    private BfhlService bfhlService;

    @BeforeEach
    void setUp() {
        bfhlService = new BfhlServiceImpl();
    }

    // ==================== Example A ====================
    @Test
    @DisplayName("Example A: Mixed data with numbers, letters, and special chars")
    void testExampleA() {
        RequestDto request = new RequestDto(Arrays.asList("a", "1", "334", "4", "R", "$"));
        ResponseDto response = bfhlService.processData(request);

        assertTrue(response.isSuccess());
        assertEquals(List.of("1"), response.getOddNumbers());
        assertEquals(List.of("334", "4"), response.getEvenNumbers());
        assertEquals(List.of("A", "R"), response.getAlphabets());
        assertEquals(List.of("$"), response.getSpecialCharacters());
        assertEquals("339", response.getSum());
        assertEquals("Ra", response.getConcatString());
    }

    // ==================== Example B ====================
    @Test
    @DisplayName("Example B: Multiple types with special chars")
    void testExampleB() {
        RequestDto request = new RequestDto(Arrays.asList("2", "a", "y", "4", "&", "-", "*", "5", "92", "b"));
        ResponseDto response = bfhlService.processData(request);

        assertTrue(response.isSuccess());
        assertEquals(List.of("5"), response.getOddNumbers());
        assertEquals(List.of("2", "4", "92"), response.getEvenNumbers());
        assertEquals(List.of("A", "Y", "B"), response.getAlphabets());
        assertEquals(List.of("&", "-", "*"), response.getSpecialCharacters());
        assertEquals("103", response.getSum());
        assertEquals("ByA", response.getConcatString());
    }

    // ==================== Example C ====================
    @Test
    @DisplayName("Example C: Multi-char alphabetical strings only")
    void testExampleC() {
        RequestDto request = new RequestDto(Arrays.asList("A", "ABCD", "DOE"));
        ResponseDto response = bfhlService.processData(request);

        assertTrue(response.isSuccess());
        assertEquals(Collections.emptyList(), response.getOddNumbers());
        assertEquals(Collections.emptyList(), response.getEvenNumbers());
        assertEquals(List.of("A", "ABCD", "DOE"), response.getAlphabets());
        assertEquals(Collections.emptyList(), response.getSpecialCharacters());
        assertEquals("0", response.getSum());
        assertEquals("EoDdCbAa", response.getConcatString());
    }

    // ==================== Edge Cases ====================
    @Test
    @DisplayName("Empty data array should return empty lists and sum 0")
    void testEmptyData() {
        RequestDto request = new RequestDto(Collections.emptyList());
        ResponseDto response = bfhlService.processData(request);

        assertTrue(response.isSuccess());
        assertTrue(response.getOddNumbers().isEmpty());
        assertTrue(response.getEvenNumbers().isEmpty());
        assertTrue(response.getAlphabets().isEmpty());
        assertTrue(response.getSpecialCharacters().isEmpty());
        assertEquals("0", response.getSum());
        assertEquals("", response.getConcatString());
    }

    @Test
    @DisplayName("Only numbers: no alphabets, no special chars")
    void testOnlyNumbers() {
        RequestDto request = new RequestDto(Arrays.asList("1", "2", "3", "4"));
        ResponseDto response = bfhlService.processData(request);

        assertTrue(response.isSuccess());
        assertEquals(List.of("1", "3"), response.getOddNumbers());
        assertEquals(List.of("2", "4"), response.getEvenNumbers());
        assertTrue(response.getAlphabets().isEmpty());
        assertTrue(response.getSpecialCharacters().isEmpty());
        assertEquals("10", response.getSum());
        assertEquals("", response.getConcatString());
    }

    @Test
    @DisplayName("Only special characters")
    void testOnlySpecialCharacters() {
        RequestDto request = new RequestDto(Arrays.asList("@", "#", "!", "%"));
        ResponseDto response = bfhlService.processData(request);

        assertTrue(response.isSuccess());
        assertTrue(response.getOddNumbers().isEmpty());
        assertTrue(response.getEvenNumbers().isEmpty());
        assertTrue(response.getAlphabets().isEmpty());
        assertEquals(List.of("@", "#", "!", "%"), response.getSpecialCharacters());
        assertEquals("0", response.getSum());
        assertEquals("", response.getConcatString());
    }

    @Test
    @DisplayName("Zero is classified as even")
    void testZeroIsEven() {
        RequestDto request = new RequestDto(List.of("0"));
        ResponseDto response = bfhlService.processData(request);

        assertTrue(response.getOddNumbers().isEmpty());
        assertEquals(List.of("0"), response.getEvenNumbers());
        assertEquals("0", response.getSum());
    }

    @Test
    @DisplayName("user_id, email, and roll_number are always present")
    void testUserMetadataPresent() {
        RequestDto request = new RequestDto(List.of("a"));
        ResponseDto response = bfhlService.processData(request);

        assertNotNull(response.getUserId());
        assertFalse(response.getUserId().isEmpty());
        assertNotNull(response.getEmail());
        assertFalse(response.getEmail().isEmpty());
        assertNotNull(response.getRollNumber());
        assertFalse(response.getRollNumber().isEmpty());
    }

    @Test
    @DisplayName("Single character alternating caps")
    void testSingleCharConcatString() {
        RequestDto request = new RequestDto(List.of("z"));
        ResponseDto response = bfhlService.processData(request);

        // Single char reversed is the same char, alternating caps: index 0 = uppercase
        assertEquals("Z", response.getConcatString());
    }
}
