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
import java.io.IOException;
import java.io.PrintWriter;

import static ru.appline.utils.Utils.readInputJson;

@WebServlet(urlPatterns = "/updateUser")
public class ServletPut extends HttpServlet {

    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter pw = response.getWriter();
        StringBuffer sb = new StringBuffer();

        readInputJson(sb, request);

        JsonObject jobj = gson.fromJson(String.valueOf(sb), JsonObject.class);

        int id = jobj.get("id").getAsInt();
        String name = jobj.get("name").getAsString();
        String surname = jobj.get("surname").getAsString();
        String salary = jobj.get("salary").getAsString();

        User user = new User(name, surname, Double.parseDouble(salary));
        response.setContentType("application/json; charset=utf-8");


        if (model.getFromList().containsKey(id)) {
            if (user.equals(model.getFromList().get(id))) {
                pw.print(gson.toJson("Изменения не произведены, данные идентичны"));
            } else {
                model.getFromList().put(id, user);
                pw.print(gson.toJson(model.getFromList()));
            }
        } else {
            pw.print(gson.toJson("Пользователя с id " + id + " не существует"));
        }

    }
}
