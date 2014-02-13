package py.com.icarusdb.demo.util;

import java.util.Locale;

import javax.enterprise.inject.Produces;
import javax.inject.Named;

import py.com.icarusdb.util.GlobalParameters;

public class SessionParameters extends GlobalParameters
{
    public static final String BUNDLE_URL = "py.com.icarusdb.demo.resources.Messages";

    @Produces @Named final int getSearchMaxResutl()
    {
        return SEARCH_MAX_RESULT;
    }
    
    @Produces @Named final String getDatePattern()
    {
        return "dd/MM/yyyy";
    }
    
    @Produces @Named final String getDateTimePattern()
    {
        return "dd/MM/yyyy HH:mm:ss";
    }
    
    @Produces @Named final Locale getAppLocale()
    {
        return AppHelper.getLocale();
    }
    
    // User Setting
    public final static String ACTION_MENU_USERSETTING = "UserSettingAction";
    
    @Produces @Named final String getActionUserSetting()
    {
        return ACTION_MENU_USERSETTING;
    }
    
    
    
}
