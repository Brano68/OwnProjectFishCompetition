package sk.kosickaakademia.fishcompetition.makejson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sk.kosickaakademia.fishcompetition.entity.Fishman;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Jsonik {

    public String makeJson(List<Fishman> list){
        if(list == null || list.isEmpty()){
            return "{}";
        }
        JSONObject jsonObject = new JSONObject();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        jsonObject.put("Date AND Time:", formatter.format(calendar.getTime()));
        JSONArray jsonArray = new JSONArray();
        for(Fishman fishman : list){
            JSONObject js = new JSONObject();
            js.put("ID", fishman.getId());
            js.put("fname", fishman.getFname());
            js.put("lname", fishman.getLname());
            js.put("age",fishman.getAge());
            js.put("nameOfTeam", fishman.getNameOfTeam());
            jsonArray.add(js);
        }
        jsonObject.put("Registrated fishman", jsonArray);
        return jsonObject.toJSONString();
    }
}
