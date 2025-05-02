package com.distributed.client;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

public class ClientApp {
    private static final String COORDINATOR_URL = "http://localhost:8080";

    public static void uploadFile(String filePath) throws Exception {
        String fileName = new File(filePath).getName();
        String nodeAddress = getNode(fileName);

        URL url = new URL(nodeAddress + "/upload");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=---ContentBoundary");

        OutputStream out = conn.getOutputStream();
        File file = new File(filePath);
        byte[] fileBytes = Files.readAllBytes(file.toPath());

        out.write(("-----ContentBoundary\r\nContent-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"\r\n\r\n").getBytes());
        out.write(fileBytes);
        out.write("\r\n-----ContentBoundary--\r\n".getBytes());
        out.flush();

        System.out.println("Response: " + conn.getResponseCode());
    }

    private static String getNode(String fileName) throws Exception {
        URL url = new URL(COORDINATOR_URL + "/getNode?fileName=" + fileName);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        return in.readLine();
    }

    public static void main(String[] args) throws Exception {
        uploadFile("sample.txt");
    }
}
