package ru.appline;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ru.appline.logic.Model;
import ru.appline.logic.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.appline.utils.Utils.readInputJson;

@WebServlet(urlPatterns = "/addUser")
public class ServletAdd extends HttpServlet {

    private AtomicInteger counter = new AtomicInteger(4);
    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter pw = response.getWriter();

        StringBuffer sb = new StringBuffer();

        readInputJson(sb, request);

        JsonObject jobj = gson.fromJson(String.valueOf(sb), JsonObject.class);

        String name = jobj.get("name").getAsString();
        String surname = jobj.get("surname").getAsString();
        double salary = jobj.get("salary").getAsDouble();

        User user = new User(name, surname, salary);
        model.add(user, counter.getAndIncrement());


        pw.print(gson.toJson(model.getFromList()));

    }
}
