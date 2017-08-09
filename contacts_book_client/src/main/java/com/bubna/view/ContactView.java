package com.bubna.view;

import com.bubna.model.entities.Contact;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class ContactView extends AbstractView<Contact> {

    private String htmlTemplate =
            "<tr id=\"contact_$$pKey$$\">" +
            "<td><div class=\"input-group input-group-sm\" disabled>" +
            "<input id=\"pKey_contact_$$pKey$$\" disabled type=\"text\" class=\"form-control\" value=\"$$pKey$$\" placeholder=\"name\">" +
            "</div></td>" +
            "<td onclick=\"$(\\'#controls_contact_$$pKey$$\\').removeClass(\\'hidden\\')\">" +
            "<div class=\"input-group input-group-sm\">" +
            "  <input onchange=\"onEdit(document.getElementById(\\'email_contact_$$email$$\\'))\" id=\"email_contact_$$email$$\" type=\"text\" class=\"form-control\" value=\"$$email$$\" placeholder=\"email\">" +
            "</div></td>" +
            "<td onclick=\"$(\\'#controls_contact_$$pKey$$\\').removeClass(\\'hidden\\')\">" +
            "<div class=\"input-group input-group-sm\">" +
            "<input pattern=\"[0-9]{10}\" onchange=\"onEdit(document.getElementById(\\'num_contact_$$num$$\\'))\" id=\"num_contact_$$num$$\" type=\"text\" class=\"form-control\" value=\"$$num$$\" placeholder=\"number\">" +
            "</div></td>" +
            "<td onclick=\"$(\\'#controls_contact_$$pKey$$\\').removeClass(\\'hidden\\')\">" +
            "<div class=\"input-group input-group-sm\">" +
            "<input onchange=\"onEdit(document.getElementById(\\'skype_contact_$$skype$$\\'))\" id=\"skype_contact_$$skype$$\" type=\"text\" class=\"form-control\" value=\"$$skype$$\" placeholder=\"skype\">" +
            "</div></td>" +
            "<td onclick=\"$(\\'#controls_contact_$$pKey$$\\').removeClass(\\'hidden\\')\">" +
            "<div class=\"input-group input-group-sm\">" +
            "<input onchange=\"onEdit(document.getElementById(\\'telegram_contact_$$telegram$$\\'))\" id=\"telegram_contact_$$telegram$$\" type=\"text\" class=\"form-control\" value=\"$$telegram$$\" placeholder=\"telegram\">" +
            "</div></td>" +
            "<td onclick=\"$(\\'#controls_contact_$$pKey$$\\').removeClass(\\'hidden\\')\">" +
            "<div class=\"input-group input-group-sm\">" +
            "<input onchange=\"onEdit(document.getElementById(\\'gName_contact_$$gName$$\\'))\" id=\"gName_contact_$$gName$$\" type=\"text\" class=\"form-control\" value=\"$$gName$$\" placeholder=\"group name\">" +
            "</div></td>"+
            "<td colspan=\"1\" class=\"hidden\" id=\"controls_contact_$$pKey$$\">"+
            "<div class=\"btn-group-vertical\">"+
            "<div class=\"btn-group\">"+
            "<button type=\"button\" class=\"btn btn-warning\" onclick=\"$$jsName$$.actionJS(\\'modify\\', document.getElementById(\\'contact_$$pKey$$\\').outerHTML)\">"+
            "<span class=\"glyphicon glyphicon-pencil\"></span>"+
            "</button>"+
            "</div>"+
            "<div class=\"btn-group\">"+
            "<button type=\"button\" class=\"btn btn-danger\" onclick=\"$$jsName$$.actionJS(\\'rem\\', document.getElementById(\\'contact_$$pKey$$\\').outerHTML);" +
                    "remElem(\\'div.col-md-7.bubna-col>div.table-responsive>table.table-hover>tbody\\', \\'contact_$$pKey$$\\');\">"+
            "<span class=\"glyphicon glyphicon-minus\"></span>"+
            "</button>"+
            "</div>"+
            "<div class=\"btn-group\">"+
            "<button type=\"button\" class=\"btn btn-info\" onclick=\"$(\\'#controls_contact_$$pKey$$\\').addClass(\\'hidden\\')\">"+
            "<span class=\"glyphicon glyphicon-chevron-right\"></span>"+
            "</button>"+
            "</div>"+
            "</div>"+
            "</td>"+
            "</tr>";

    ContactView(String jsName) {
        super(jsName);
    }

    @Override
    protected Contact fromHtml(String html) {
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
        return new Contact(data[0], data[1], (data[2]==null||data[2].equals(""))?-1:new Integer(data[2]), data[3], data[4], data[5]);
    }

    @Override
    protected String toHtml(Contact entity) {
        return htmlTemplate
                .replace("$$jsName$$", jsName)
                .replace("$$pKey$$", entity.getName())
                .replace("$$email$$", entity.getEmail()==null?"":entity.getEmail())
                .replace("$$skype$$", entity.getSkype()==null?"":entity.getSkype())
                .replace("$$telegram$$", entity.getTelegram()==null?"":entity.getTelegram())
                .replace("$$num$$", entity.getNum()==null?"-1":entity.getNum().toString())
                .replace("$$gName$$", entity.getGroupName()==null?"":entity.getGroupName());
    }

    @Override
    protected boolean checkType(Object obj) {
        return (obj instanceof Contact) || (obj instanceof Exception) || (obj.getClass().equals(new ArrayList<Contact>().getClass()));
    }

}
