<%@ taglib prefix = "sql" uri = "http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!%
    import com.bubna.model.ObservablePart;
    import com.bubna.model.Model;
    import com.bubna.dao.DAO;
    import com.bubna.dao.DBDAOFactory;
    import com.bubna.model.entity.User;
    import com.bubna.controller.AbstractController;
    import com.bubna.controller.Controller;
    import com.bubna.util.Answer;

    import java.util.Observer;
    import java.util.Observable;
    import java.util.HashMap;
%>

<html>
    <head>
        <title>DB Test</title>
    </head>
    <body>

        <p>user count:
            <%
                Controller controller;
                HashMap answers;

                answers = new HashMap();
                Observer observer = new Observer() {
                    public void update(Observable o, Object arg) {
                        Answer answer = (Answer) arg;
                        answers.put(answer.getKey(), answer.getVal());
                    }
                };
                ObservablePart observable = new ObservablePart();
                observable.addObserver(observer);
                Model userModel = new UserModel(observable, DBDAOFactory.INSTANCE.getDAO(User.class));
                controller = new AbstractController(userModel);
                controller.listen("count");
                out.println(this.answers.get("count"));
            %>
        </p>

    </body>
</html>
