package az.webapp.colorbrain.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class SearchUtil {
    public static String trainingSearchPathBuilder(HttpServletRequest request) {
        String link = "";
        String header = request.getParameter("header");
        String trainer = request.getParameter("trainer");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String reg = request.getParameter("reg");
        String status = request.getParameter("status");

        if (Objects.nonNull(header)) {
            link += "&header=" + header;
        }
        if (Objects.nonNull(trainer)) {
            link += "&trainer=" + trainer;
        }
        if (Objects.nonNull(from)) {
            link += "&from=" + from;
        }
        if (Objects.nonNull(to)) {
            link += "&to=" + to;
        }
        if (Objects.nonNull(reg)) {
            link += "&reg=" + reg;
        }
        if (Objects.nonNull(status)) {
            link += "&status=" + status;
        }
        return link;
    }

    public static String projectSearchPathBuilder(HttpServletRequest request) {
        String link = "";
        String header = request.getParameter("header");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String status = request.getParameter("status");
/*
        String trainer = request.getParameter("trainer");
        String reg = request.getParameter("reg");

        if (Objects.nonNull(trainer)) {
            link += "&trainer=" + trainer;
        }
        if (Objects.nonNull(reg)) {
            link += "&reg=" + reg;
        }
*/
        if (Objects.nonNull(header)) {
            link += "&header=" + header;
        }
        if (Objects.nonNull(from)) {
            link += "&from=" + from;
        }
        if (Objects.nonNull(to)) {
            link += "&to=" + to;
        }
        if (Objects.nonNull(status)) {
            link += "&status=" + status;
        }
        return link;
    }

    public static String newsSearchPathBuilder(HttpServletRequest request) {
        String link = "";
        String header = request.getParameter("header");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
/*
        String status = request.getParameter("status");
        String trainer = request.getParameter("trainer");
        String reg = request.getParameter("reg");

        if (Objects.nonNull(trainer)) {
            link += "&trainer=" + trainer;
        }
        if (Objects.nonNull(reg)) {
            link += "&reg=" + reg;
        }
        if (Objects.nonNull(status)) {
            link += "&status=" + status;
        }
*/
        if (Objects.nonNull(header)) {
            link += "&header=" + header;
        }
        if (Objects.nonNull(from)) {
            link += "&from=" + from;
        }
        if (Objects.nonNull(to)) {
            link += "&to=" + to;
        }
        return link;
    }

    public static String mediaSearchPathBuilder(HttpServletRequest request) {
        String link = "";
        String header = request.getParameter("header");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
/*
        String status = request.getParameter("status");
        String trainer = request.getParameter("trainer");
        String reg = request.getParameter("reg");

        if (Objects.nonNull(trainer)) {
            link += "&trainer=" + trainer;
        }
        if (Objects.nonNull(reg)) {
            link += "&reg=" + reg;
        }
        if (Objects.nonNull(status)) {
            link += "&status=" + status;
        }
*/
        if (Objects.nonNull(header)) {
            link += "&header=" + header;
        }
        if (Objects.nonNull(from)) {
            link += "&from=" + from;
        }
        if (Objects.nonNull(to)) {
            link += "&to=" + to;
        }
        return link;
    }
}
