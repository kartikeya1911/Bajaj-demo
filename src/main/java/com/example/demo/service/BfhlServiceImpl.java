package com.example.demo.service;

import com.example.demo.dto.RequestDto;
import com.example.demo.dto.ResponseDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BfhlServiceImpl implements BfhlService {

    // TODO: Replace with your actual details
    private static final String USER_ID = "jain_kartikeya";
    private static final String EMAIL = "jainkartikeya9@gmail.com";
    private static final String ROLL_NUMBER = "2310991865";

    @Override
    public ResponseDto process(RequestDto requestDto) {
        List<String> data = requestDto.getData();

        List<String> oddNumbers = new ArrayList<>();
        List<String> evenNumbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();
        int sum = 0;

        for (String item : data) {
            if (isNumber(item)) {
                int num = Integer.parseInt(item);
                sum += num;
                if (num % 2 == 0) {
                    evenNumbers.add(item);
                } else {
                    oddNumbers.add(item);
                }
            } else if (isAlphabet(item)) {
                alphabets.add(item.toUpperCase());
            } else {
                // Special character (anything that is not purely numeric or purely alphabetic)
                specialCharacters.add(item);
            }
        }

        // Build concat_string:
        // 1. Collect all individual alphabetical characters from the input (preserving order)
        // 2. Reverse the collected characters
        // 3. Apply alternating caps: index 0 = uppercase, index 1 = lowercase, index 2 = uppercase, ...
        String concatString = buildConcatString(data);

        return new ResponseDto(
                true,
                USER_ID,
                EMAIL,
                ROLL_NUMBER,
                oddNumbers,
                evenNumbers,
                alphabets,
                specialCharacters,
                String.valueOf(sum),
                concatString
        );
    }

    /**
     * Checks if a string is a valid integer number (may have leading minus sign).
     */
    private boolean isNumber(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if a string consists entirely of alphabetical characters (a-z, A-Z).
     */
    private boolean isAlphabet(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        for (char c : s.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Builds the concat_string by:
     * 1. Extracting all individual alphabetical characters from the data array in order
     * 2. Reversing them
     * 3. Applying alternating caps (index 0 → uppercase, index 1 → lowercase, etc.)
     */
    private String buildConcatString(List<String> data) {
        // Step 1: Collect all individual alphabetical characters in order
        StringBuilder allChars = new StringBuilder();
        for (String item : data) {
            for (char c : item.toCharArray()) {
                if (Character.isLetter(c)) {
                    allChars.append(c);
                }
            }
        }

        // Step 2: Reverse
        String reversed = allChars.reverse().toString();

        // Step 3: Alternating caps — even index = uppercase, odd index = lowercase
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < reversed.length(); i++) {
            char c = reversed.charAt(i);
            if (i % 2 == 0) {
                result.append(Character.toUpperCase(c));
            } else {
                result.append(Character.toLowerCase(c));
            }
        }

        return result.toString();
    }
}
