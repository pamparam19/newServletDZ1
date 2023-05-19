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


@WebServlet(urlPatterns = "/deleteUser")
public class ServletDelete extends HttpServlet {
    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter pw = response.getWriter();

        StringBuffer sb = new StringBuffer();

        readInputJson(sb, request);

        JsonObject jobj = gson.fromJson(String.valueOf(sb), JsonObject.class);
        int id = jobj.get("id").getAsInt();


        if(model.getFromList().containsKey(id)){
            model.getFromList().remove(id);
            pw.print(gson.toJson(model.getFromList()));
        } else{
            pw.print(gson.toJson("Пользователя с id " + id +
                    " не существует"));
        }
    }
}
