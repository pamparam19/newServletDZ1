package ru.appline;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ru.appline.logic.Model;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static ru.appline.utils.Utils.readInputJson;

@WebServlet(urlPatterns = "/get")
public class ServletList extends HttpServlet {

    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter pw = response.getWriter();

        int id = Integer.parseInt(request.getParameter("id"));

        if (id == 0) {
            pw.print("<html>" +
                    "<h3>Доступные пользователи:</h3><br/>" +
                    "ID пользователя: " +
                    "<ul>");

            model.getFromList().forEach((key, value) ->
                    pw.print("<li>" + key + "</li>" +
                            "<ul>" +
                            "<li>Имя: " + value.getName() + "</li>" +
                            "<li>Фамилия: " + value.getSurname() + "</li>" +
                            "<li>Зарплата: " + value.getSalary() + "</li>" +
                            "</ul>"));

            pw.print("</ul>" +
                    "<a href=\"index.jsp\">Домой<a/>" +
                    "</html>");
        } else if (id > 0) {
            if (id > model.getFromList().size()) {
                pw.print("<html>" +
                        "<h3>Такого пользователя нет</h3>" +
                        "<a href=\"index.jsp\">Домой<a/>" +
                        "</html>");
            } else {
                pw.print("<html>" +
                        "<h3>Запрошенный пользователь: </h3>" +
                        "</br>" +
                        "Имя: " + model.getFromList().get(id).getName() + "<br/>" +
                        "Фамилия: " + model.getFromList().get(id).getSurname() + "<br/>" +
                        "Зарплата: " + model.getFromList().get(id).getSalary() + "<br/>" +
                        "<a href=\"index.jsp\">Домой<a/>" +
                        "</html>");
            }
        } else {
            pw.print("<html>" +
                    "<h3>ID должен быть больше 0</h3>" +
                    "<a href=\"index.jsp\">Домой<a/>" +
                    "</html>");
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter pw = response.getWriter();
        StringBuffer sb = new StringBuffer();

        readInputJson(sb, request);

        JsonObject jobj = gson.fromJson(String.valueOf(sb), JsonObject.class);

        request.setCharacterEncoding("UTF-8");
        int id = jobj.get("id").getAsInt();

        response.setContentType("application/json; charset=utf-8");


        if (id == 0) {
            pw.print(gson.toJson(model.getFromList()));
        } else if (id > 0) {
            pw.print(gson.toJson((model.getFromList().get(id))));
        } else {
            pw.print(gson.toJson("id должен быть больше 0"));
        }
    }


}
