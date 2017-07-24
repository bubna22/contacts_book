package com.bubna.dao.stax;

import com.bubna.exceptions.NoSuchElementException;
import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.Group;

import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.StartElement;
import java.util.HashMap;
import java.util.function.Predicate;

class StAXGroupDAO extends StAXEntityAncestorDAO {

    @Override
    protected void writeNewElements(
            String curTagName,
            XMLStreamWriter writer,
            HashMap<String, EntityAncestor> values) throws XMLStreamException, NoSuchElementException {
        if (!curTagName.equals("groups")) return;
        String keys[] = new String[values.size()];
        values.keySet().toArray(keys);
        for (String key : keys) {
            Group inputG = (Group) values.get(key);
            if (inputG == null) throw new NoSuchElementException("no group " + key);
            writer.writeEmptyElement("", "group", "urn:Test.Namespace");
            writer.writeAttribute("gname", inputG.getName());
            writer.writeAttribute("gcolor", inputG.getColor() == null ? "-1" : inputG.getColor().toString());
        }
    }

    @Override
    protected void rewriteExistsElement(
            StartElement se,
            XMLStreamWriter writer,
            EntityAncestor inputAncestor) throws XMLStreamException {
        Group oldGroup = new Group(
                se.getAttributeByName(new QName("gname")).getValue(),
                new Integer(se.getAttributeByName(new QName("cnum")).getValue())
        );

        if (inputAncestor == null) return;

        Group group = (Group) inputAncestor;
        writer.writeEmptyElement("", "group", "urn:Test.Namespace");
        writer.writeAttribute("gname", group.getName());
        writer.writeAttribute("gcolor", group.getColor() == null?
                oldGroup.getColor()==null?"-1":Integer.toString(oldGroup.getColor())
                :group.getColor().toString());
    }

    @Override
    protected String getAncestorName(StartElement se) {
        return se.getAttributeByName(
                new QName("gname")
        )==null?null:se.getAttributeByName(new QName("gname")).getValue();
    }

    protected EntityAncestor getEntityByStartElement(
            StartElement startElement,
            Predicate<String> pKey,
            Predicate<EntityAncestor> pVal) {
        if (startElement.getName().toString().contains("group") &&
                !startElement.getName().toString().contains("groups")) {
            String key = startElement.getAttributeByName(new QName("gname")).getValue();
            if (pKey != null) if (!pKey.test(key)) return null;

            Group rv = new Group(key,
                    new Integer(startElement.getAttributeByName(new QName("gcolor")).getValue())
            );
            if (pVal != null) if (!pVal.test(rv)) return null;
            if (key == null) return null;
            return rv;
        }
        return null;
    }

}
