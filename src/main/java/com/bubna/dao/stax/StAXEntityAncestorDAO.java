package com.bubna.dao.stax;

import com.bubna.dao.DAO;
import com.bubna.dao.TemplateDAO;
import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.exceptions.NoSuchElementException;
import com.bubna.model.entities.Contact;
import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.Group;
import com.bubna.utils.Utils;

import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Predicate;

public enum StAXEntityAncestorDAO implements TemplateDAO<String, EntityAncestor, File> {

    CONTACT,
    GROUP;

    protected File source;

    @Override
    public DAO setUpdatedSource(File source) {
        this.source = source;
        return this;
    }

    private ByteArrayInputStream getFreshInStream() throws InitException {
        Path path = Paths.get(source.getAbsolutePath());
        byte[] data;
        try {
            data = Files.readAllBytes(path);
        } catch (IOException e) {
            throw new InitException("");
        }
        return new ByteArrayInputStream(data);
    }

    private FileOutputStream getFreshOutStream() throws InitException {
        try {
            return new FileOutputStream(source);
        } catch (FileNotFoundException e) {
            throw new InitException("");
        }
    }

    public HashMap<String, EntityAncestor> read(Predicate<String> pKey, Predicate<EntityAncestor> pVal) throws NoSuchElementException, InitException {
        ByteArrayInputStream inputSource = getFreshInStream();
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader xmlEventReader = null;
        HashMap<String, EntityAncestor> dataReturned = new HashMap<>();

        try {
            xmlEventReader = xmlInputFactory.createXMLEventReader(inputSource);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

        while (xmlEventReader.hasNext()) {
            XMLEvent xmlEvent = null;
            try {
                xmlEvent = xmlEventReader.nextEvent();
            } catch (XMLStreamException e) {
                throw new InitException(e.getMessage()==null?"":e.getMessage());
            }
            if (xmlEvent.isStartElement()) {
                StartElement startElement = xmlEvent.asStartElement();
                EntityAncestor entityAncestor = null;
                String key = null;
                switch (this) {
                    case GROUP:
                        if (startElement.getName().toString().contains("group") &&
                                !startElement.getName().toString().contains("groups")) {
                            key = startElement.getAttributeByName(new QName("gname")).getValue();
                            if (pKey != null) if (!pKey.test(key)) continue;

                            entityAncestor = new Group(key,
                                    new Integer(startElement.getAttributeByName(new QName("gcolor")).getValue())
                            );
                        }
                        break;
                    case CONTACT:
                        if (startElement.getName().toString().contains("contact") &&
                                !startElement.getName().toString().contains("contacts")) {
                            key = startElement.getAttributeByName(new QName("cname")).getValue();
                            if (pKey != null) if (!pKey.test(key)) continue;

                            entityAncestor = new Contact(key,
                                    startElement.getAttributeByName(new QName("cemail")).getValue(),
                                    new Integer(startElement.getAttributeByName(new QName("cnum")).getValue()),
                                    startElement.getAttributeByName(new QName("cskype")).getValue(),
                                    startElement.getAttributeByName(new QName("ctelegram")).getValue(),
                                    startElement.getAttributeByName(new QName("gname")).getValue()
                            );
                        }
                        break;
                }
                if (pVal != null) if (!pVal.test(entityAncestor)) continue;
                if (key == null) continue;
                dataReturned.put(key, entityAncestor);
            }
        }
        return dataReturned;
    }

    public void write(HashMap<String, EntityAncestor> values)
            throws InitException, NoSuchElementException, IncorrectInputException {
        ByteArrayInputStream inputSource = getFreshInStream();
        FileOutputStream outputSource = getFreshOutStream();

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLOutputFactory factory = XMLOutputFactory.newInstance();

        XMLEventReader reader;
        XMLStreamWriter writer;
        try {
            reader = xmlInputFactory.createXMLEventReader(inputSource);
            writer = factory.createXMLStreamWriter(outputSource);
        } catch (XMLStreamException e) {
            throw new InitException(e.getMessage()==null?"":e.getMessage());
        }

        String curTagName = null;
        String curAncestorName = null;
        boolean rem = false;

        while (reader.hasNext()) {
            try {
                XMLEvent event = reader.nextEvent();
                if (event.isStartDocument()) {
                    writer.writeStartDocument("UTF-8", "1.0");
                } else if (event.isEndDocument()) {
                    writer.writeEndDocument();
                } else if (event.isStartElement()) {
                    StartElement se = event.asStartElement();
                    curTagName = se.getName().getLocalPart();
                    switch (this) {
                        case GROUP:
                            curAncestorName =
                                    se.getAttributeByName(
                                            new QName("gname")
                                    )==null?null:se.getAttributeByName(new QName("gname")).getValue();
                            break;
                        case CONTACT:
                            curAncestorName =
                                    se.getAttributeByName(
                                            new QName("cname")
                                    )==null?null:se.getAttributeByName(new QName("cname")).getValue();
                            break;
                    }
                    if (curTagName.equals("contact") || curTagName.equals("group")) {
                        if (!values.containsKey(curAncestorName)) {
                            writer.writeEmptyElement("", se.getName().getLocalPart(), "");
                            Iterator<Attribute> attrs = se.getAttributes();

                            while (attrs.hasNext()) {
                                Attribute attr = attrs.next();
                                writer.writeAttribute(attr.getName().getLocalPart(), attr.getValue());
                            }
                        } else {
                            switch (this) {
                                case CONTACT:
                                    Contact oldContact = new Contact(
                                            se.getAttributeByName(new QName("cname")).getValue(),
                                            se.getAttributeByName(new QName("cemail")).getValue(),
                                            new Integer(se.getAttributeByName(new QName("cnum")).getValue()),
                                            se.getAttributeByName(new QName("cskype")).getValue(),
                                            se.getAttributeByName(new QName("ctelegram")).getValue(),
                                            se.getAttributeByName(new QName("gname")).getValue()
                                    );
                                    EntityAncestor inputAncestor = values.get(curAncestorName);
                                    values.remove(curAncestorName);
                                    if (inputAncestor == null) continue;
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
                                    continue;
                                case GROUP:
                                    Group oldGroup = new Group(
                                            se.getAttributeByName(new QName("gname")).getValue(),
                                            new Integer(se.getAttributeByName(new QName("cnum")).getValue())
                                    );
                                    inputAncestor = values.get(curAncestorName);
                                    values.remove(curAncestorName);
                                    if (inputAncestor == null) continue;
                                    Group group = (Group) inputAncestor;
                                    writer.writeEmptyElement("", "group", "urn:Test.Namespace");
                                    writer.writeAttribute("gname", group.getName());
                                    writer.writeAttribute("gcolor", group.getColor() == null?
                                            oldGroup.getColor()==null?"-1":Integer.toString(oldGroup.getColor())
                                            :group.getColor().toString());
                            }
                        }
                    } else {
                        writer.writeStartElement("", se.getName().getLocalPart(), "");
                        if (curTagName.equals("data")) {
                            writer.writeAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema-instance");
                            writer.writeAttribute("xmlns", "urn:Test.Namespace");
                            writer.writeAttribute("xsd:schemaLocation", "urn:Test.Namespace ../schemas/data.xsd");
                        } else {
                            Iterator<Attribute> attrs = se.getAttributes();

                            while (attrs.hasNext()) {
                                Attribute attr = attrs.next();
                                writer.writeAttribute(attr.getName().getLocalPart(), attr.getValue());
                            }
                        }

                    }
                } else if (event.isCharacters()) {
                    writer.writeCharacters(event.asCharacters().getData());
                } else if (event.isEndElement()) {
                    EndElement ee = event.asEndElement();
                    curTagName = ee.getName().getLocalPart();
                    if (curTagName != null) if (curTagName.equals("contacts") || curTagName.equals("groups")) {
                        switch (this) {
                            case GROUP:
                                if (!curTagName.equals("groups")) break;
                                String keys[] = new String[values.size()];
                                values.keySet().toArray(keys);
                                for (String key : keys) {
                                    Group inputG = (Group) values.get(key);
                                    if (inputG == null) throw new NoSuchElementException("no group " + key);
                                    writer.writeEmptyElement("", "group", "urn:Test.Namespace");
                                    writer.writeAttribute("gname", inputG.getName());
                                    writer.writeAttribute("gcolor", inputG.getColor() == null ? "-1" : inputG.getColor().toString());
                                }
                                break;
                            case CONTACT:
                                if (!curTagName.equals("contacts")) break;
                                keys = new String[values.size()];
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
                                break;
                        }
                    }
                    if (!curTagName.equals("contact") && !curTagName.equals("group")) writer.writeEndElement();
                }
            } catch (XMLStreamException e) {
                throw new InitException(e.getMessage()==null?"":e.getMessage());
            }
        }
        try {
            writer.close();
        } catch (XMLStreamException e) {
            throw new InitException(e.getMessage()==null?"":e.getMessage());
        }
    }
}
