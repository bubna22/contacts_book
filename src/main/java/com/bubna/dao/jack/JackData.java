package com.bubna.dao.jack;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(namespace = "urn:Test.Namespace", localName = "data")
class JackData {
    @JacksonXmlProperty(isAttribute = true, localName = "schemaLocation")
    String xsdschemaLocation;
    @JacksonXmlProperty(localName = "contacts")
    Contacts contacts;
    @JacksonXmlProperty(localName = "groups")
    Groups groups;
}

class Contacts {
    @JacksonXmlProperty(localName = "contact")
    @JacksonXmlElementWrapper(useWrapping = false)
    Contact[] contact;
}

class Groups {
    @JacksonXmlProperty(localName = "group")
    @JacksonXmlElementWrapper(useWrapping = false)
    Group[] group;
}

class Contact {
    @JacksonXmlProperty(isAttribute = true)
    String cname;

    @JacksonXmlProperty(isAttribute = true)
    String cemail;

    @JacksonXmlProperty(isAttribute = true)
    String cskype;

    @JacksonXmlProperty(isAttribute = true)
    Integer cnum;

    @JacksonXmlProperty(isAttribute = true)
    String ctelegram;

    @JacksonXmlProperty(isAttribute = true)
    String gname;
}

class Group {
    @JacksonXmlProperty(isAttribute = true)
    String gname;

    @JacksonXmlProperty(isAttribute = true)
    Integer gcolor;
}