package com.maven.tutorial.mavem.tutorial.service;

import com.maven.tutorial.mavem.tutorial.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AddressServiceTest {

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

    @Test void validatePin_InvalidCase() {
        assertEquals(false, service.validatePin(""));
        assertEquals(false, service.validatePin("1"));
        assertEquals(false, service.validatePin("123"));
        assertEquals(false, service.validatePin("123A"));
        assertEquals(false, service.validatePin("A123"));
        assertEquals(false, service.validatePin("12345"));
        assertEquals(false, service.validatePin("vfgbh"));
        assertEquals(false, service.validatePin("vkdo[]"));

    }

    @Test void validatePin_ValidCase() {
        assertEquals(true, service.validatePin("1234"));
        assertEquals(true, service.validatePin("654321"));

    }

    @Test void maskify() {
        assertEquals("1", service.maskify("1"));
        assertEquals("123", service.maskify("123"));
        assertEquals("#1457", service.maskify("11457"));
        assertEquals("###########0000", service.maskify("000000000000000"));
    }
    @Test
    public void testGame() {
        assertEquals(5, service.countBits(1234));
        assertEquals(1, service.countBits(4));
        assertEquals(3, service.countBits(7));
        assertEquals(2, service.countBits(9));
        assertEquals(2, service.countBits(10));
    }
}
