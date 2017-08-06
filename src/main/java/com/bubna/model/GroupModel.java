package com.bubna.model;

import com.bubna.dao.DAOFactory;
import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.exceptions.NoSuchElementException;
import com.bubna.model.entities.Group;
import java.net.URISyntaxException;

public class GroupModel extends AbstractModel<Group> {

    public GroupModel(DAOFactory daoFactory, ObservablePart observable) {
        this.daoFactory = daoFactory;
        this.observable = observable;
        try {
            this.dao = daoFactory.getDAO(daoFactory.getSource(), Group.class);
        } catch (InitException | URISyntaxException | IncorrectInputException e) {
            applyException(e);
        }
    }

    @Override
    public void prepare() {
        try {
            this.dao = daoFactory.getDAO(daoFactory.getSource(), Group.class);
        } catch (InitException | URISyntaxException | IncorrectInputException e) {
            applyException(e);
        }
    }

    @Override
    public void list(Group entity) {
        super.list(entity);
        try {
            observable.setChanged();
            observable.notifyObservers(dao.list(acc, o -> true));
        } catch (InitException | NoSuchElementException e) {
            applyException(e);
        }
    }
}
