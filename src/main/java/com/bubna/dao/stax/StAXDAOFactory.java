package com.bubna.dao.stax;

import com.bubna.dao.DAO;
import com.bubna.dao.DAOFactory;
import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.model.entities.Contact;
import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.Group;
import com.bubna.utils.Utils;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stax.StAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.net.URISyntaxException;

/**
 * Created by test on 17.07.2017.
 */
public enum StAXDAOFactory implements DAOFactory<File> {

    INSTANCE;

    @Override
    public DAO getDAO(File source, Class<? extends EntityAncestor> daoType) throws IncorrectInputException {
        if (daoType.equals(Contact.class)) {
            return new StAXContactDAO().setUpdatedSource(source);
        } else if (daoType.equals(Group.class)) {
            return new StAXGroupDAO().setUpdatedSource(source);
        }
        throw new IncorrectInputException("Invalid format of requested data");
    }

    @Override
    public File getSource() throws InitException, URISyntaxException {
        return Utils.getDataXml();
    }

    @Override
    public void validateSource() throws InitException {
        File f = null;
        try {
            f = Utils.getDataXml();
        } catch (URISyntaxException e) {
            throw new InitException("init error");
        }
        if (!f.exists())
            throw new InitException("data.xml not exists in path " + f.getAbsolutePath());
        try {
            XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(new FileInputStream(Utils.getDataXml()));
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(Utils.getDataXsd());

            Validator validator = schema.newValidator();
            validator.validate(new StAXSource(reader));
        } catch (SAXException | IOException | URISyntaxException | XMLStreamException e) {
            throw new InitException(e.getMessage()==null?"validation error":e.getMessage());
        }
    }
}
