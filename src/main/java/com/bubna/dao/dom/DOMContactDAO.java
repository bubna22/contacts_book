package com.bubna.dao.dom;

import com.bubna.model.entities.Contact;
import com.bubna.model.entities.EntityAncestor;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

final class DOMContactDAO extends DOMEntityAncestorDAO {

    protected final Element getNode(EntityAncestor oldAncestor, EntityAncestor curAncestor) {
        Element rNode = null;

        Contact inputContact = (Contact) curAncestor;
        Contact oldContact = (Contact) oldAncestor;
        rNode = document.createElement("contact");
        rNode.setAttribute("cname", inputContact.getName());
        rNode.setAttribute("cemail", inputContact.getEmail()==null?
                oldContact!=null&&oldContact.getEmail()!=null?oldContact.getEmail():""
                :inputContact.getEmail());
        rNode.setAttribute("cskype", inputContact.getSkype()==null?
                oldContact!=null&&oldContact.getSkype()!=null?oldContact.getSkype():""
                :inputContact.getSkype());
        rNode.setAttribute("cnum", inputContact.getNum()==null?
                oldContact!=null&&oldContact.getNum()!=null?Integer.toString(oldContact.getNum()):"-1"
                :Integer.toString(inputContact.getNum()));
        rNode.setAttribute("ctelegram", inputContact.getTelegram()==null?
                oldContact!=null&&oldContact.getTelegram()!=null?oldContact.getTelegram():""
                :inputContact.getTelegram());
        rNode.setAttribute("gname", inputContact.getGroupName()==null?
                oldContact!=null&&oldContact.getGroupName()!=null?oldContact.getGroupName():""
                :inputContact.getGroupName());

        return rNode;
    }

    @Override
    protected final XPathExpression getXPath() throws XPathExpressionException {
        return XPathFactory.newInstance().newXPath().compile("data/contacts/contact");
    }

    @Override
    protected final XPathExpression getXPathContainer() throws XPathExpressionException {
        return XPathFactory.newInstance().newXPath().compile("data/contacts");
    }

    @Override
    protected final Contact createByNode(Node node) {
        return new Contact(
                node.getAttributes().getNamedItem("cname").getTextContent(),
                node.getAttributes().getNamedItem("cemail").getTextContent(),
                new Integer(node.getAttributes().getNamedItem("cnum").getTextContent()),
                node.getAttributes().getNamedItem("cskype").getTextContent(),
                node.getAttributes().getNamedItem("ctelegram").getTextContent(),
                node.getAttributes().getNamedItem("gname").getTextContent()
        );
    }
}
