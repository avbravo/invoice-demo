/**
 * 
 */
package py.com.icarusdb.demo.util;

import javax.annotation.PostConstruct;

import py.com.icarusdb.common.EntityInterface;


/**
 * @author mcrose
 *
 */
public interface EditController
{
    @PostConstruct public void init();
    public EntityInterface getSelectedRow();
    public String save();
}
