package com.esgi.controller;

import com.esgi.model.Casting;
import com.esgi.model.Person;
import com.esgi.services.PersonService;
import com.esgi.utils.PersonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Class PersonController
 */
@Controller
@RequestMapping("/person")
public class PersonController extends BaseController {

    /**
     * @param personService PersonService
     */
    @Autowired
    private PersonService personService;

    @RequestMapping(method = GET)
    public List<Person> all() {
        return personService.getAllPersons();
    }

    /**
     * @param namePerson String
     * @return Person
     */
    @RequestMapping(value = "/{namePerson:[A-z]*}", method = GET)
    public String retrieveByName(@ModelAttribute PersonUtils personUtils, Model model, @PathVariable("namePerson") String namePerson) {
        String queryValue = "";
        if (!personUtils.getResearch().equals("")) {
            queryValue = personUtils.getResearch();
        } else if (!namePerson.equals("")) {
            queryValue = namePerson;
        }

        if (!queryValue.equals("")) {
            try {
                InputStream is = new URL("http://api.allocine.fr/rest/v3/search?count=500&format=json&page=1&partner=YW5kcm9pZC12Mg&profile=medium&filter=person&q=" + queryValue).openStream();
                model.addAttribute("test", "http://api.allocine.fr/rest/v3/search?count=500&format=json&page=1&partner=YW5kcm9pZC12Mg&profile=medium&filter=person&q=");
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                JsonObject body = Json.createReader(new StringReader(parseJsonFromReader(rd))).readObject();
                ArrayList<Person> listPersons = parsePersonListFromAPI(body.getJsonObject("feed").getJsonArray("person"));
                model.addAttribute("listPersons", listPersons);
            } catch (IOException error) {
                System.out.println(error);
            }
        } else {
            model.addAttribute("test", URL_API + "&" + URL_GET_FILTER + FILTER_PERSON + "&" + URL_GET_SEARCH + personUtils.getResearch());
        }

        return "person";
    }

    private ArrayList<Person> parsePersonListFromAPI(JsonArray listPersonFromApi) {
        ArrayList<Person> listPersons = new ArrayList();
        for (int i = 0; i < listPersonFromApi.size(); i++) {
            JsonObject personJson = listPersonFromApi.getJsonObject(i);
            Person person = new Person();
            person.setName(personJson.getString("name"));
            //person.setBirthday(Date.valueOf(personJson.getString("birthday")));
            if (personJson.get("picture") != null) {
                person.setPicture(personJson.getJsonObject("picture").getString("href"));
            }
            /*if (personJson.get("link") != null) {
                person.setLinkBo(personJson.getJsonObject("link").getString("href"));
            }*/
            /*if (personJson.get("nationality") != null) {
                person.setNationality(personJson.getJsonObject("nationality").getString("$"));
            }*/

            if (personJson.get("activity") != null) {
                HashSet<Casting> castings = new HashSet<>();
                for (JsonValue activity : (JsonArray) personJson.get("activity")) {
                    Casting casting = new Casting();
                    casting.setRole(((JsonObject) activity).getString("$"));
                    castings.add(casting);
                }
                person.setRoles(castings);
            }
            listPersons.add(person);

        }

        return (listPersons);
    }

}