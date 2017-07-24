package com.bubna.dao.dom;

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
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by test on 17.07.2017.
 */
public enum DOMDAOFactory implements DAOFactory<File> {

    INSTANCE;

    @Override
    public DAO getDAO(File source, Class<? extends EntityAncestor> daoType) throws IncorrectInputException, InitException {
        if (daoType.equals(Contact.class)) {
            return new DOMContactDAO().setUpdatedSource(source);
        } else if (daoType.equals(Group.class)) {
            return new DOMGroupDAO().setUpdatedSource(source);
        }
        throw new IncorrectInputException("Invalid format of requested data");
    }

    @Override
    public File getSource() throws InitException, URISyntaxException {
        File f = Utils.getDataXml();
        return f;
    }

    @Override
    public void validateSource() throws InitException {
        File f = null;
        try {
            f = Utils.getDataXml();
        } catch (URISyntaxException e) {
            throw new InitException("init error");
        }
        if (!f.exists()) throw new InitException("data.xml not exists in path " + f.getAbsolutePath());
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = null;
        try {
            schema = sf.newSchema(Utils.getDataXsd());
        } catch (SAXException e) {
            throw new InitException(e.getMessage()==null?"schema initialize error":e.getMessage());
        } catch (URISyntaxException e) {
            throw new InitException("init error");
        }
        Validator validator = schema.newValidator();
        try {
            StreamSource streamSources = new StreamSource(getClass().getResourceAsStream("/storage/data.xml"),
                    getClass().getResource("/storage/data.xml").toString());
            validator.validate(streamSources);
        } catch (SAXException | IOException e) {
            throw new InitException(e.getMessage()==null?"validation error":e.getMessage());
        }
    }
}
