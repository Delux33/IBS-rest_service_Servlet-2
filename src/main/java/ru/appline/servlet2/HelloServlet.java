package ru.appline.servlet2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/calculator")
public class HelloServlet extends HttpServlet {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuffer sb = new StringBuffer();
        String line;

        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        JsonObject jsonObject = gson.fromJson(String.valueOf(sb), JsonObject.class);
        String operation = jsonObject.get("math").getAsString();

        response.setContentType("application/json;charset=utf-8");
        PrintWriter pw = response.getWriter();

        switch (operation) {
            case "+":
                double result1 = jsonObject.get("a").getAsDouble() + jsonObject.get("b").getAsDouble();
                pw.print(gson.fromJson("{\n\"result\" : \"" + result1 + "\"\n}", JsonObject.class));
                break;
            case "-":
                double result2 = jsonObject.get("a").getAsDouble() - jsonObject.get("b").getAsDouble();
                pw.print(gson.fromJson("{\n\"result\" : \"" + result2 + "\"\n}", JsonObject.class));
                break;
            case "*":
                double result3 = jsonObject.get("a").getAsDouble() * jsonObject.get("b").getAsDouble();
                pw.print(gson.fromJson("{\n\"result\" : \"" + result3 + "\"\n}", JsonObject.class));
                break;
            case "/":
                double result4 = jsonObject.get("a").getAsDouble() / jsonObject.get("b").getAsDouble();
                pw.print(gson.fromJson("{\n\"result\" : \"" + result4 + "\"\n}", JsonObject.class));
                break;
            default: pw.print(gson.fromJson("{\n\"Неизвестная операция, существующие операции\" : \"+, -, *, /\"\n}", JsonObject.class));
        }
    }
}