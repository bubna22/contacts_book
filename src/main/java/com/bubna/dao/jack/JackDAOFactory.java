package com.bubna.dao.jack;

import com.bubna.dao.DAO;
import com.bubna.dao.DAOFactory;
import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.model.entities.Contact;
import com.bubna.model.entities.EntityAncestor;
import com.bubna.model.entities.Group;
import com.bubna.utils.Utils;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.net.URISyntaxException;

/**
 * Created by test on 17.07.2017.
 */
public enum JackDAOFactory implements DAOFactory<File> {

    INSTANCE;

    @Override
    public DAO getDAO(File source, Class<? extends EntityAncestor> daoType) throws IncorrectInputException, InitException {
        if (daoType.equals(Contact.class)) {
            return JackEntityAncestorDAO.CONTACT.setUpdatedSource(source);
        } else if (daoType.equals(Group.class)) {
            return JackEntityAncestorDAO.GROUP.setUpdatedSource(source);
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
        try {
            XmlMapper xmlMapper = new XmlMapper();
            StringBuilder sb = new StringBuilder();
            String line;
            JackData value;
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(Utils.getDataXml())));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                value = xmlMapper.readValue(sb.toString(), JackData.class);
            } catch (IOException e) {
                throw new InitException(e.getMessage()==null?"":e.getMessage());
            };
        } catch (URISyntaxException e) {
            throw new InitException(e.getMessage()==null?"":e.getMessage());
        }
    }
}
