/**
 * 
 */
package py.com.icarusdb.demo.util;

import javax.annotation.PostConstruct;

import py.com.icarusdb.entity.EntityInterface;


/**
 * @author rgamarra
 *
 */
public interface ListController
{
    @PostConstruct public void init();
    public EntityInterface getSelectedRow();
    public void search();
    public void print();
}
