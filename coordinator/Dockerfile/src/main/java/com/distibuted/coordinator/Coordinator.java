package com.distributed.coordinator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@SpringBootApplication
@RestController
public class Coordinator {
    public static void main(String[] args) {
        SpringApplication.run(Coordinator.class, args);
    }

    private final TreeMap<Integer, String> hashRing = new TreeMap<>();
    private final int VIRTUAL_NODES = 3;

    @PostMapping("/register")
    public synchronized String registerNode(@RequestParam String address) {
        for (int i = 0; i < VIRTUAL_NODES; i++) {
            int hash = (address + "#" + i).hashCode();
            hashRing.put(hash, address);
        }
        return "Registered " + address;
    }

    @GetMapping("/getNode")
    public synchronized String getNode(@RequestParam String fileName) {
        int hash = fileName.hashCode();
        SortedMap<Integer, String> tailMap = hashRing.tailMap(hash);
        return tailMap.isEmpty() ? hashRing.firstEntry().getValue() : tailMap.get(tailMap.firstKey());
    }
}
