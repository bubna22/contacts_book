package com.bubna.dao.dom;

import com.bubna.dao.DAO;
import com.bubna.dao.TemplateDAO;
import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.exceptions.NoSuchElementException;
import com.bubna.model.entities.Contact;
import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.Group;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.function.Predicate;

public enum DOMEntityAncestorDAO implements TemplateDAO<String, EntityAncestor, File> {

    CONTACT,
    GROUP;

    private File source;
    private Document document;

    @Override
    public DAO setUpdatedSource(File source) throws InitException {
        this.source = source;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            document = db.parse(source);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new InitException(e.getMessage()==null?"doc parse err":e.getMessage());
        }
        return this;
    }

    @Override
    public HashMap<String, EntityAncestor> read(Predicate<String> pKey, Predicate<EntityAncestor> pValue) throws InitException {
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        HashMap<String, EntityAncestor> dataReturned = new HashMap<>();
        NodeList nl = null;
        XPathExpression expr = null;

        switch (this) {
            case GROUP:
                try {
                    expr = xpath.compile("data/groups/group");
                } catch (XPathExpressionException e) {
                    throw new InitException("");
                }
                break;
            case CONTACT:
                try {
                    expr = xpath.compile("data/contacts/contact");
                } catch (XPathExpressionException e) {
                    throw new InitException("");
                }
                break;
        }
        try {
            nl = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            throw new InitException("");
        }

        for (int i = 0; i < nl.getLength(); i++) {
            Node n = nl.item(i);
            EntityAncestor entityAncestor = null;
            switch (this) {
                case CONTACT:
                    entityAncestor = new Contact(
                            n.getAttributes().getNamedItem("cname").getTextContent(),
                            n.getAttributes().getNamedItem("cemail").getTextContent(),
                            new Integer(n.getAttributes().getNamedItem("cnum").getTextContent()),
                            n.getAttributes().getNamedItem("cskype").getTextContent(),
                            n.getAttributes().getNamedItem("ctelegram").getTextContent(),
                            n.getAttributes().getNamedItem("gname").getTextContent()
                    );
                    break;
                case GROUP:
                    entityAncestor = new Group(
                            n.getAttributes().getNamedItem("gname").getTextContent(),
                            new Integer(n.getAttributes().getNamedItem("gcolor").getTextContent())
                    );
                    break;
            }
            if (pKey != null) if (!pKey.test(entityAncestor.getName())) continue;
            if (pValue != null) if (!pValue.test(entityAncestor)) continue;
            dataReturned.put(entityAncestor.getName(), entityAncestor);
        }
        return dataReturned;
    }

    private Element getNode(EntityAncestor entityAncestor) {
        Element rNode = null;
        switch (this) {
            case GROUP:
                Group inputGroup = (Group) entityAncestor;
                rNode = document.createElement("group");
                rNode.setAttribute("gname", inputGroup.getName());
                rNode.setAttribute("gcolor", inputGroup.getColor()==null?"-1":inputGroup.getColor().toString());
                break;
            case CONTACT:
                Contact c = (Contact) entityAncestor;
                rNode = document.createElement("contact");
                rNode.setAttribute("cname", c.getName());
                rNode.setAttribute("cemail", c.getEmail());
                rNode.setAttribute("cskype", c.getSkype());
                rNode.setAttribute("cnum", c.getNum()!=null?Integer.toString(c.getNum()):"-1");
                rNode.setAttribute("ctelegram", c.getTelegram());
                rNode.setAttribute("gname", c.getGroupName());
                break;
        }
        return rNode;
    }

    @Override
    public void write(HashMap<String, EntityAncestor> values) throws InitException, NoSuchElementException, IncorrectInputException {
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        NodeList nodeList = null;
        Node cNode = null;
        Element element;
        XPathExpression mExpr = null;
        XPathExpression cExpr = null;

        switch (this) {
            case GROUP:
                try {
                    mExpr = xpath.compile("data/groups/group");
                    cExpr = xpath.compile("data/groups");
                    break;
                } catch (XPathExpressionException e) {
                    throw new InitException(e.getMessage()==null?"":e.getMessage());
                }
            case CONTACT:
                try {
                    mExpr = xpath.compile("data/contacts/contact");
                    cExpr = xpath.compile("data/contacts");
                    break;
                } catch (XPathExpressionException e) {
                    throw new InitException(e.getMessage()==null?"":e.getMessage());
                }
        }

        try {
            nodeList = (NodeList) mExpr.evaluate(document, XPathConstants.NODESET);
            cNode = (Node) cExpr.evaluate(document, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            throw new InitException(e.getMessage()==null?"":e.getMessage());
        }

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node n = nodeList.item(i);
            if (n == null || n.getAttributes() == null ||
                    n.getAttributes().getNamedItem("cname") == null ||
                    n.getAttributes().getNamedItem("gname") == null) continue;
            EntityAncestor entityAncestor = null;
            switch (this) {
                case CONTACT:
                    entityAncestor = new Contact(
                            n.getAttributes().getNamedItem("cname").getTextContent(),
                            null,
                            null,
                            null,
                            null,
                            null
                    );
                    break;
                case GROUP:
                    entityAncestor = new Group(
                            n.getAttributes().getNamedItem("gname").getTextContent(),
                            null
                    );
                    break;
            }
            if (values.containsKey(entityAncestor.getName())) {
                cNode.removeChild(n);
                EntityAncestor inputAncestor = values.get(entityAncestor.getName());
                if (inputAncestor == null) continue;
                cNode.appendChild(getNode(inputAncestor));
                values.remove(entityAncestor.getName());
            }
        }

        String[] keys = new String[values.size()];
        values.keySet().toArray(keys);
        for (int i = 0; i < keys.length; i++) {
            EntityAncestor entityAncestor = values.get(keys[i]);
            String key = keys[i];
            if (entityAncestor == null) throw new NoSuchElementException("no elem " + key);
            cNode.appendChild(getNode(entityAncestor));
        }

        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.setOutputProperty(OutputKeys.METHOD, "xml");
            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            tr.transform(new DOMSource(document),
                    new StreamResult(new FileOutputStream(source)));

        } catch (TransformerException | IOException te) {
            throw new InitException("");
        }
    }
}
