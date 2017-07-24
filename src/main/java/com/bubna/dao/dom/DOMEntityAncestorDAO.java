package com.bubna.dao.dom;

import com.bubna.dao.DAO;
import com.bubna.dao.TemplateDAO;
import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.exceptions.NoSuchElementException;
import com.bubna.model.entities.EntityAncestor;
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

abstract class DOMEntityAncestorDAO implements TemplateDAO<String, EntityAncestor, File> {

    private File source;
    Document document;

    @Override
    public final DAO setUpdatedSource(File source) throws InitException {
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
    public final HashMap<String, EntityAncestor> read(Predicate<String> pKey, Predicate<EntityAncestor> pValue) throws InitException {
        HashMap<String, EntityAncestor> dataReturned = new HashMap<>();
        NodeList nl = null;
        XPathExpression expr = null;

        try {
            expr = getXPath();
            nl = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            throw new InitException("");
        }

        for (int i = 0; i < nl.getLength(); i++) {
            Node n = nl.item(i);
            EntityAncestor entityAncestor = createByNode(n);
            if (pKey != null) if (!pKey.test(entityAncestor.getName())) continue;
            if (pValue != null) if (!pValue.test(entityAncestor)) continue;
            dataReturned.put(entityAncestor.getName(), entityAncestor);
        }
        return dataReturned;
    }

    @Override
    public final void write(HashMap<String, EntityAncestor> values) throws InitException, NoSuchElementException, IncorrectInputException {
        NodeList nodeList;
        Node cNode;
        XPathExpression mExpr = null;
        XPathExpression cExpr = null;

        try {
            mExpr = getXPath();
            cExpr = getXPathContainer();
        } catch (XPathExpressionException e) {
            throw new InitException(e.getMessage()==null?"":e.getMessage());
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
            EntityAncestor entityAncestor = createByNode(n);
            if (values.containsKey(entityAncestor.getName())) {
                cNode.removeChild(n);
                EntityAncestor inputAncestor = values.get(entityAncestor.getName());
                if (inputAncestor == null) continue;
                cNode.appendChild(getNode(entityAncestor, inputAncestor));
                values.remove(entityAncestor.getName());
            }
        }

        String[] keys = new String[values.size()];
        values.keySet().toArray(keys);
        for (int i = 0; i < keys.length; i++) {
            EntityAncestor entityAncestor = values.get(keys[i]);
            String key = keys[i];
            if (entityAncestor == null) throw new NoSuchElementException("no elem " + key);
            cNode.appendChild(getNode(null, entityAncestor));
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

    protected abstract XPathExpression getXPath() throws XPathExpressionException;
    protected abstract XPathExpression getXPathContainer() throws XPathExpressionException;
    protected abstract EntityAncestor createByNode(Node node);
    protected abstract Element getNode(EntityAncestor oldAncestor, EntityAncestor curAncestor);
}
