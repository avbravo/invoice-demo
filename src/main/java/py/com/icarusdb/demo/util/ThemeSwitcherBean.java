/*
 * Copyright 2009-2011 Prime Technology.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package py.com.icarusdb.demo.util;

import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.primefaces.examples.GuestPreferences;


/**
 * @author primefaces
 * 
 */
@ManagedBean
public class ThemeSwitcherBean
{
    private Map<String, String> themes = null;

//    private List<Theme> advancedThemes = null;

    private String theme = null;

    private GuestPreferences gp = null;

    
    
    public void setGp(GuestPreferences gp)
    {
        this.gp = gp;
    }

    public Map<String, String> getThemes()
    {
        return themes;
    }

    public String getTheme()
    {
        return theme;
    }

    public void setTheme(String theme)
    {
        this.theme = theme;
    }

    @PostConstruct
    public void init()
    {
        if(gp != null) {
            theme = gp.getTheme();
        }

//        advancedThemes = new ArrayList<Theme>();
//        advancedThemes.add(new Theme("aristo", "aristo.png"));
//        advancedThemes.add(new Theme("cupertino", "cupertino.png"));
//        advancedThemes.add(new Theme("trontastic", "trontastic.png"));

        themes = new TreeMap<String, String>();
        themes.put("afterdark", "afterdark");
        themes.put("afterwork", "afterwork");
        themes.put("bluesky", "bluesky");
        themes.put("bootstrap", "bootstrap");
        themes.put("dot-luv", "dot-luv");
        
//        themes.put("Aristo", "aristo");
//        themes.put("Black-Tie", "black-tie");
//        themes.put("Blitzer", "blitzer");
//        themes.put("Bluesky", "bluesky");
//        themes.put("Casablanca", "casablanca");
//        themes.put("Cupertino", "cupertino");
//        themes.put("Dark-Hive", "dark-hive");
//        themes.put("Dot-Luv", "dot-luv");
//        themes.put("Eggplant", "eggplant");
//        themes.put("Excite-Bike", "excite-bike");
//        themes.put("Flick", "flick");
//        themes.put("Glass-X", "glass-x");
//        themes.put("Hot-Sneaks", "hot-sneaks");
//        themes.put("Humanity", "humanity");
//        themes.put("Le-Frog", "le-frog");
//        themes.put("Midnight", "midnight");
//        themes.put("Mint-Choc", "mint-choc");
//        themes.put("Overcast", "overcast");
//        themes.put("Pepper-Grinder", "pepper-grinder");
//        themes.put("Redmond", "redmond");
//        themes.put("Rocket", "rocket");
//        themes.put("Sam", "sam");
//        themes.put("Smoothness", "smoothness");
//        themes.put("South-Street", "south-street");
//        themes.put("Start", "start");
//        themes.put("Sunny", "sunny");
//        themes.put("Swanky-Purse", "swanky-purse");
//        themes.put("Trontastic", "trontastic");
//        themes.put("UI-Darkness", "ui-darkness");
//        themes.put("UI-Lightness", "ui-lightness");
//        themes.put("Vader", "vader");
    }
}
