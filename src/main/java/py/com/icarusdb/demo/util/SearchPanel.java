package py.com.icarusdb.demo.util;

import java.io.Serializable;
import java.util.List;

import py.com.icarusdb.entity.EntityInterface;

/**
 * @author rgamarra
 *
 */
public interface SearchPanel extends Serializable
{

    public abstract List<?extends EntityInterface> getResultList();

    public abstract void search();

    public abstract void clear();

    public abstract boolean isSelected();

    public void handleClose();
}