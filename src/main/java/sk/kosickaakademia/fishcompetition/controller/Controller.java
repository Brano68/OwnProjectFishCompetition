package sk.kosickaakademia.fishcompetition.controller;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.kosickaakademia.fishcompetition.database.Database;
import sk.kosickaakademia.fishcompetition.entity.Fishman;
import sk.kosickaakademia.fishcompetition.makejson.Jsonik;

import javax.xml.crypto.Data;
import java.util.List;

@RestController
public class Controller {

    //GET
    //getting all fishmen through localhost:8083/fishman/all
    //localhost:8083/fishman/all
    @GetMapping("/fishman/all")
    public ResponseEntity<String> allFishman(){
        String json = new Jsonik().makeJson(new Database().getAllFishman());
        if(json == null){
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("Something wrong");
        }
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
    }

    //POST
    //adding new fihman through localhost:8083/fishman/add
    //example of json
    // {"fname":"Janko","lname":"Usko","age":15,"nameOfTeam":"Chytim_A_Pustim"}
    @PostMapping("/fishman/add")
    public ResponseEntity<String> addNewFishman(@RequestBody String data){
        if(data == null){
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("Something wrong");
        }
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(data);
            String fname = (String) jsonObject.get("fname");
            String lname = (String) jsonObject.get("lname");
            Long age1 = (Long) jsonObject.get("age");
            int age = age1.intValue();
            String nameOfTeam = (String) jsonObject.get("nameOfTeam");
            if(fname == null || lname == null || age < 1 || nameOfTeam == null){
                return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("Something wrong");
            }
            Fishman fishman = new Fishman(fname, lname, age, nameOfTeam);
            boolean result = new Database().insertNewFishMan(fishman);
            if(result){
                return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body("Fishman has been added");
            }
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("Something Wrong!!!");
        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("Something wrong");
        }
    }

    //PUT
    //updating fishman name of team
    //localhost:8083/fishman/update/{nameOfTeam}
    //localhost:8083/fishman/update/ChytimAPustim
    @PutMapping("/fishman/update/{nameOfTeam}")
    public ResponseEntity<String> updateFishman(@PathVariable String nameOfTeam, @RequestBody String body){
        try {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(body);
            String newNameOfTeam = (String) jsonObject.get("newNameOfTeam");
            if(newNameOfTeam.equalsIgnoreCase("null")){
                return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("Something wrong");
            }
            boolean result = new Database().updateNameOfTeam(nameOfTeam, newNameOfTeam);
            if(result){
                return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body("Update successfully");
            }
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("Something wrong");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //DELETE
    //deleting fishman according to the id
    @DeleteMapping("/fishman/delete/{id}")
    public ResponseEntity<String> deleteFishman(@PathVariable int id){
        boolean result = new Database().deleteFishman(id);
        if(result){
            return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body("Delete successfully");
        }
        return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("Something wrong!!!");
    }

}
