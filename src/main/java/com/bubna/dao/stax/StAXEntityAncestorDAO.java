package com.bubna.dao.stax;

import com.bubna.dao.DAO;
import com.bubna.dao.TemplateDAO;
import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.exceptions.NoSuchElementException;
import com.bubna.model.entities.EntityAncestor;

import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Predicate;

abstract class StAXEntityAncestorDAO implements TemplateDAO<String, EntityAncestor, File> {

    protected File source;
    protected String tag;

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

    protected abstract EntityAncestor getEntityByStartElement(
            StartElement startElement,
            Predicate<String> pKey,
            Predicate<EntityAncestor> pVal);

    protected abstract String getAncestorName(StartElement se);
    protected abstract void rewriteExistsElement(StartElement se, XMLStreamWriter writer, EntityAncestor inputAncestor) throws XMLStreamException;
    protected abstract void writeNewElements(String curTagName, XMLStreamWriter writer, HashMap<String, EntityAncestor> values) throws XMLStreamException, NoSuchElementException;
    protected abstract String getTag();

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
                EntityAncestor entityAncestor = getEntityByStartElement(startElement, pKey, pVal);
                if (entityAncestor == null) continue;
                dataReturned.put(entityAncestor.getName(), entityAncestor);
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
                    curAncestorName = getAncestorName(se);
                    if (curTagName.equals(getTag())) {
                        if (!values.containsKey(curAncestorName)) {
                            writer.writeEmptyElement("", se.getName().getLocalPart(), "");
                            Iterator<Attribute> attrs = se.getAttributes();

                            while (attrs.hasNext()) {
                                Attribute attr = attrs.next();
                                writer.writeAttribute(attr.getName().getLocalPart(), attr.getValue());
                            }
                        } else {
                            EntityAncestor inputAncestor = values.get(curAncestorName);
                            values.remove(curAncestorName);
                            rewriteExistsElement(se, writer, inputAncestor);
                        }
                    } else {
                        if (curTagName.equals("contact") || curTagName.equals("group")) writer.writeEmptyElement("", se.getName().getLocalPart(), "");
                        else writer.writeStartElement("", se.getName().getLocalPart(), "");
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
                        writeNewElements(curTagName, writer, values);
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
