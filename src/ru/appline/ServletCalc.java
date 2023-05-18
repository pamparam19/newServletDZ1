package ru.appline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static ru.appline.utils.Utils.readInputJson;

@WebServlet(urlPatterns = "/calculate")
public class ServletCalc extends HttpServlet {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();

        StringBuffer sb = new StringBuffer();
        readInputJson(sb,request);
        JsonObject jobj = gson.fromJson(String.valueOf(sb), JsonObject.class);

        double a = jobj.get("a").getAsDouble();
        double b = jobj.get("b").getAsDouble();
        String math = jobj.get("math").getAsString();

        double result = 0;
        response.setContentType("application/json; charset=utf-8");
        switch (math){
            case ("+"):
                result = a + b;
                break;
            case ("-"):
                result = a - b;
                break;
            case ("*"):
                result = a * b;
                break;
            case ("/"):
                result = a / b;
                break;
            case ("%"):
                result = a % b;
                break;
            default:
                pw.print(gson.toJson("Передано некорректное значение оператора: " + math));
        }

        String json = "{\"result\": " + result +" }";

       pw.print(gson.fromJson(json, JsonObject.class));

    }
}
