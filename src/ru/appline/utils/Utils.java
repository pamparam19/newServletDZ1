package ru.appline.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

public class Utils {
    public static void readInputJson(StringBuffer sb, HttpServletRequest request){
        String line;
        try{
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null){
                sb.append(line);
            }
        } catch (Exception e){
            System.out.printf("Error");
        }
    }
}


//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
