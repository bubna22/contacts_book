package com.bubna.dao.stax;

import com.bubna.exceptions.NoSuchElementException;
import com.bubna.model.entities.Contact;
import com.bubna.model.entities.EntityAncestor;

import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.StartElement;
import java.util.HashMap;
import java.util.function.Predicate;

class StAXContactDAO extends StAXEntityAncestorDAO {

    @Override
    protected void writeNewElements(
            String curTagName,
            XMLStreamWriter writer,
            HashMap<String, EntityAncestor> values) throws XMLStreamException, NoSuchElementException {
        if (!curTagName.equals("contacts")) return;
        String[] keys = new String[values.size()];
        values.keySet().toArray(keys);
        for (String key : keys) {
            Contact inputC = (Contact) values.get(key);
            if (inputC == null) throw new NoSuchElementException("no contact " + key);
            writer.writeEmptyElement("", "contact", "urn:Test.Namespace");
            writer.writeAttribute("cname", inputC.getName());
            writer.writeAttribute("cemail", inputC.getEmail() == null ? "" : inputC.getEmail());
            writer.writeAttribute("cskype", inputC.getSkype() == null ? "" : inputC.getSkype());
            writer.writeAttribute("cnum", inputC.getNum() == null ? "-1" : inputC.getNum().toString());
            writer.writeAttribute("ctelegram", inputC.getTelegram() == null ? "" : inputC.getTelegram());
            writer.writeAttribute("gname", inputC.getGroupName() == null ? "" : inputC.getGroupName());
        }
    }

    @Override
    protected void rewriteExistsElement(
            StartElement se,
            XMLStreamWriter writer,
            EntityAncestor inputAncestor) throws XMLStreamException {
        Contact oldContact = new Contact(
                se.getAttributeByName(new QName("cname")).getValue(),
                se.getAttributeByName(new QName("cemail")).getValue(),
                new Integer(se.getAttributeByName(new QName("cnum")).getValue()),
                se.getAttributeByName(new QName("cskype")).getValue(),
                se.getAttributeByName(new QName("ctelegram")).getValue(),
                se.getAttributeByName(new QName("gname")).getValue()
        );

        if (inputAncestor == null) return;
        Contact contact = (Contact) inputAncestor;
        writer.writeEmptyElement("", "contact", "urn:Test.Namespace");
        writer.writeAttribute("cname", contact.getName());
        writer.writeAttribute("cemail",
                contact.getEmail()==null?
                        oldContact.getEmail()==null?"":oldContact.getEmail()
                        :contact.getEmail());
        writer.writeAttribute("cskype",
                contact.getSkype()==null?
                        oldContact.getSkype()==null?"":oldContact.getSkype()
                        :contact.getSkype());
        writer.writeAttribute("cnum",
                contact.getNum()==null?
                        oldContact.getNum()==null?"-1":Integer.toString(oldContact.getNum())
                        :contact.getNum().toString());
        writer.writeAttribute("ctelegram",
                contact.getTelegram()==null?
                        oldContact.getTelegram()==null?"":oldContact.getTelegram()
                        :contact.getTelegram());
        writer.writeAttribute("gname",
                contact.getGroupName()==null?
                        oldContact.getGroupName()==null?"":oldContact.getGroupName()
                        :contact.getGroupName());
    }

    @Override
    protected String getAncestorName(StartElement se) {
        return se.getAttributeByName(
                new QName("cname")
        )==null?null:se.getAttributeByName(new QName("cname")).getValue();
    }

    protected EntityAncestor getEntityByStartElement(
            StartElement startElement,
            Predicate<String> pKey,
            Predicate<EntityAncestor> pVal) {
        if (startElement.getName().toString().contains("contact") &&
                !startElement.getName().toString().contains("contacts")) {
            String key = startElement.getAttributeByName(new QName("cname")).getValue();
            if (pKey != null) if (!pKey.test(key)) return null;

            Contact rv = new Contact(key,
                    startElement.getAttributeByName(new QName("cemail")).getValue(),
                    new Integer(startElement.getAttributeByName(new QName("cnum")).getValue()),
                    startElement.getAttributeByName(new QName("cskype")).getValue(),
                    startElement.getAttributeByName(new QName("ctelegram")).getValue(),
                    startElement.getAttributeByName(new QName("gname")).getValue()
            );
            if (pVal != null) if (!pVal.test(rv)) return null;
            if (key == null) return null;
            return rv;
        }
        return null;
    }
}
