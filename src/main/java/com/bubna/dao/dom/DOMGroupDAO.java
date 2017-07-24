package com.bubna.dao.dom;

import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.Group;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

final class DOMGroupDAO extends DOMEntityAncestorDAO {

    protected final Element getNode(EntityAncestor oldAncestor, EntityAncestor curAncestor) {
        Element rNode = null;

        Group inputGroup = (Group) curAncestor;
        Group oldGroup = (Group) oldAncestor;
        rNode = document.createElement("group");
        rNode.setAttribute("gname", inputGroup.getName());
        rNode.setAttribute("gcolor", inputGroup.getColor()==null?
                oldGroup!=null&&oldGroup.getColor()!=null?Integer.toString(oldGroup.getColor()):"-1"
                :Integer.toString(inputGroup.getColor()));

        return rNode;
    }

    @Override
    protected final XPathExpression getXPath() throws XPathExpressionException {
        return XPathFactory.newInstance().newXPath().compile("data/groups/group");
    }

    @Override
    protected final XPathExpression getXPathContainer() throws XPathExpressionException {
        return XPathFactory.newInstance().newXPath().compile("data/groups");
    }

    @Override
    protected final Group createByNode(Node node) {
        return new Group(
                node.getAttributes().getNamedItem("gname").getTextContent(),
                new Integer(node.getAttributes().getNamedItem("gcolor").getTextContent())
        );
    }
}
