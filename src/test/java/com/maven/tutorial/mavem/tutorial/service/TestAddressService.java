package com.maven.tutorial.mavem.tutorial.service;

import com.maven.tutorial.mavem.tutorial.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestAddressService {

    @Mock
    private AddressRepository repository;

    @InjectMocks
    private AddressService service;
    @Test
    public void abcdeReturnsZero() {
        assertEquals(0, service.duplicateCount("abcde"));
    }

    @Test
    public void abcdeaReturnsOne() {
        assertEquals(1, service.duplicateCount("abcdea"));
    }

    @Test
    public void indivisibilityReturnsOne() {
        assertEquals(26, service.duplicateCount("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZReturnsTwentySix"));
    }

    @Test
    public void reallyLongStringContainingDuplicatesReturnsThree() {
        String testThousandA = new String(new char[1000]).replace('\0', 'a');
        String testHundredB = new String(new char[100]).replace('\0', 'b');
        String testTenC = new String(new char[10]).replace('\0', 'c');
        String test1CapitalA = new String(new char[1]).replace('\0', 'A');
        String test1d = new String(new char[1]).replace('\0', 'd');
        String test = test1d + test1CapitalA + testTenC +
                testHundredB + testThousandA;


        assertEquals(3, service.duplicateCount(test));
    }

    @Test
    public void fixedTests() {
        assertEquals(7, service.oddCount(15));
        assertEquals(7511, service.oddCount(15023));
    }
}
