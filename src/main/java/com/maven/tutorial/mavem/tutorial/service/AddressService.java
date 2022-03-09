package com.maven.tutorial.mavem.tutorial.service;

import com.maven.tutorial.mavem.tutorial.model.entity.Address;
import com.maven.tutorial.mavem.tutorial.model.entity.User;
import com.maven.tutorial.mavem.tutorial.model.request.UserRequest;
import com.maven.tutorial.mavem.tutorial.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AddressService {
    private final AddressRepository repository;

    public void create(UserRequest userRequest, User user) {
        Address address = new Address();
        address.setUser(user);
        address.setLine1(userRequest.getLine1());
        address.setLine2(userRequest.getLine2());
        address.setZipcode(userRequest.getZipcode());
        repository.save(address);
    }

    public String maskify(String str) {
        if (str.length() > 4) {
            int amountOfChap = str.length() - 4;
            return "#".repeat(amountOfChap) + str.substring(amountOfChap);
        }
        return str;
    }

    public int duplicateCount(String text) {
        List<String> strings = Arrays.stream(text.split("")).collect(Collectors.toList());
        Map<String, Integer> repeatationMap = new HashMap<String, Integer>();

        for (String str : strings) {
            str = str.toLowerCase();
            if (repeatationMap.containsKey(str)) {
                repeatationMap.put(str, repeatationMap.get(str) + 1);
            } else {
                repeatationMap.put(str, 1);
            }
        }
        int count = 0;
        for (int repatCount : repeatationMap.values()) {
            if (repatCount > 1) {
                count++;
            }
        }
        return count;
    }

    public boolean validatePin(String pin) {
        Pattern p = Pattern.compile("(^\\d{4}$)|(^\\d{6}$)");
        Matcher m = p.matcher(pin);
        return m.matches();
    }

    public int oddCount(int n) {
        int count = 0;
        do {
            n = n - 2;
            count++;

        } while (n > 1);
        return count;
    }
}

