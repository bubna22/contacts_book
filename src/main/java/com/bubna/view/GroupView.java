package com.bubna.view;

import com.bubna.model.entities.Group;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class GroupView extends AbstractView<Group> {

    private String htmlTemplate =
            "<tr id=\"group_$$pKey$$\">" +
            "<td><div class=\"input-group input-group-sm\" disabled>" +
            "<input id=\"pKey_group_$$pKey$$\" disabled type=\"text\" class=\"form-control\" value=\"$$pKey$$\" placeholder=\"name\">" +
            "</div></td>" +
            "<td onclick=\"$(\\'#controls_group_$$pKey$$\\').removeClass(\\'hidden\\')\">" +
            "<div class=\"input-group input-group-sm\">" +
            "  <input pattern=\"[0-9]{10}\" onchange=\"onEdit(document.getElementById(\\'color_group_$$color$$\\'))\" id=\"color_group_$$color$$\" type=\"text\" class=\"form-control\" value=\"$$color$$\" placeholder=\"color\">" +
            "</div></td>" +
            "<td colspan=\"1\" class=\"hidden\" id=\"controls_group_$$pKey$$\">"+
            "<div class=\"btn-group-vertical\">"+
            "<div class=\"btn-group\">"+
            "<button type=\"button\" class=\"btn btn-warning\" onclick=\"$$jsName$$.actionJS(\\'modify\\', document.getElementById(\\'group_$$pKey$$\\').outerHTML)\">"+
            "<span class=\"glyphicon glyphicon-pencil\"></span>"+
            "</button>"+
            "</div>"+
            "<div class=\"btn-group\">"+
            "<button type=\"button\" class=\"btn btn-danger\" onclick=\"$$jsName$$.actionJS(\\'rem\\', document.getElementById(\\'group_$$pKey$$\\').outerHTML);" +
                    "remElem(\\'div.col-md-7.bubna-col>div.table-responsive>table.table-hover>tbody\\', \\'group_$$pKey$$\\');\">"+
            "<span class=\"glyphicon glyphicon-minus\"></span>"+
            "</button>"+
            "</div>"+
            "<div class=\"btn-group\">"+
            "<button type=\"button\" class=\"btn btn-info\" onclick=\"$(\\'#controls_group_$$pKey$$\\').addClass(\\'hidden\\')\">"+
            "<span class=\"glyphicon glyphicon-chevron-right\"></span>"+
            "</button>"+
            "</div>"+
            "</div>"+
            "</td>"+
            "</tr>";

    GroupView(String jsName) {
        super(jsName);
    }

    @Override
    protected Group fromHtml(String html) {
        String[] data = new String[6];
        if (html != null) {
            html = "<html><head></head><body><table>" + html + "</table></body></html>";
            Document doc = Jsoup.parse(html);
            Elements tds = doc.select("td>div>input");
            for (Element e : tds) {
                int i = tds.indexOf(e);
                if (i < data.length) data[i] = e.val();
            }
        }
        return new Group(data[0], (data[1]==null||data[1].equals(""))?-1:Integer.parseInt(data[1]));
    }

    @Override
    protected String toHtml(Group entity) {
        return htmlTemplate
                .replace("$$jsName$$", jsName)
                .replace("$$pKey$$", entity.getName())
                .replace("$$color$$", entity.getColor()==null?"-1":entity.getColor().toString());
    }

    @Override
    protected boolean checkType(Object obj) {
        return (obj instanceof Group) || (obj instanceof Exception) || (obj.getClass().equals(new ArrayList<Group>().getClass()));
    }
}
