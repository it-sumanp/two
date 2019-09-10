package me.ifelse.two.rest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TwoController {

    public static final String serverUrl = "https://9090-dot-8790715-dot-devshell.appspot.com";

    @GetMapping("/")
    public static String Hello(){
        return "I'M YOUR CONVECTOR";
    }

    @GetMapping("/codeToState")
    public static String CodeToState(@RequestParam("code") String code){
        String state = null;
        try {
            String response = requestProcessedData(serverUrl+"/readDataForCode");
            JSONObject jsonObject = new JSONObject(response);
            state = jsonObject.getString(code.toUpperCase());
        } catch (Exception e) {
            System.out.println("[ERROR] : [CUSTOM_LOG] : " + e);
        }

        if(state == null){
            state = "No Match Found";
        }
        return state;
    }

    @GetMapping("/stateToCode")
    public static String StateToCode(@RequestParam("state") String state){
        String value = "";
        try {
            String response = requestProcessedData(serverUrl+"/readDataForState");
            JSONArray jsonArray = new JSONArray(response);

            for(int n = 0; n < jsonArray.length(); n++)
            {
                JSONObject object = jsonArray.getJSONObject(n);
                String name = object.getString("name");

                if(state.equalsIgnoreCase(name)){
                    value = object.getString("abbreviation");
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[ERROR] : [CUSTOM_LOG] : " + e);
        }

        if(value == null){
            value = "No Match Found";
        }
        return value;
    }

    public static String requestProcessedData(String url){
        RestTemplate request = new RestTemplate();
        String result = request.getForObject(url, String.class);
        System.out.print(url);
        return (result);
    }

}
