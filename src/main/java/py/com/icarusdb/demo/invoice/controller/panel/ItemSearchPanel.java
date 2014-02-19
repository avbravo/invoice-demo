/**
 * 
 */
package py.com.icarusdb.demo.invoice.controller.panel;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import py.com.icarusdb.demo.invoice.data.DatabaseManager;
import py.com.icarusdb.demo.invoice.model.Item;
import py.com.icarusdb.demo.util.AppHelper;
import py.com.icarusdb.demo.util.MessageUtil;
import py.com.icarusdb.demo.util.SearchPanel;
import py.com.icarusdb.demo.util.SearchPanelHelper;

/**
 * @author rgamarra
 *
 */
@Model
@SessionScoped
public class ItemSearchPanel extends SearchPanelHelper implements SearchPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = -566051125986985800L;

    @Inject
    private EntityManager em;
    
    @Inject
    private DatabaseManager manager;
    
    
    private final static String qlString = "select o from Item o ";
    
    private String description = null;
    private String code = null;
    
    private List<Item> resultList = null;
    
    private Item selectedEntity = null;

    
    @Override
    public List<Item> getResultList()
    {
        return resultList;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public Item getSelectedEntity()
    {
        return selectedEntity;
    }
    
    public void setSelectedEntity(Item selected)
    {
        this.selectedEntity = selected;
    }
    
    @Override
    public void search()
    {
        String restrics = " where ";
        boolean addAnd = false;
        
        if(description != null && !description.isEmpty())
        {
            addAnd = true;
            restrics += "lower(description) like '%"+description.trim().toLowerCase()+"%' ";
        }
        
        if(code != null && !code.isEmpty())
        {
            if (addAnd) restrics += "and ";
            restrics += "lower(code) like '%"+code.trim().toLowerCase()+"%' ";
        }
        
        if(restrics.trim().length() == 5)
        {
            restrics = "order by description asc";
        }
        else
        {
            restrics += "order by description asc";
        }
        
        
        resultList = em.createQuery(qlString + restrics, Item.class).getResultList();
    
        MessageUtil.showResults(resultList);
    }
    
    @Override
    public void clear()
    {
        selectedEntity = null;
        edition = false;
    }
    
    private void partcialClear()
    {
        code = null;
        resultList = null;
    }

    @Override
    public boolean isSelected()
    {
        return selectedEntity != null;
    }

    @Override
    public void handleClose()
    {
        partcialClear();
    }

    public void prepareNew()
    {
        selectedEntity = new Item();
    }

    public String save()
    {
        if (errors()) return null;
        
        try
        {
            manager.persist(selectedEntity);
        }
        catch (Exception e)
        {
            FacesMessage mess = new FacesMessage(e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, mess);
            e.printStackTrace();
        }
        
        return null;
    }

    private boolean errors()
    {
        FacesMessage message = null;
        
        String required = AppHelper.getBundleMessage("error.required.value");
        
        if(AppHelper.nothing(selectedEntity.getDescription()))
        {
            message = new FacesMessage(required +": " +AppHelper.getBundleMessage("item.description"));
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        
        if(AppHelper.nothing(selectedEntity.getCode()))
        {
            message = new FacesMessage(required +": " +AppHelper.getBundleMessage("item.code"));
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        
        //TODO check description existence !
        
        return (message != null);
    }
    
}
