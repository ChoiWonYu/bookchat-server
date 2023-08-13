package bc.bookchat.common.response;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {
  public static ResponseEntity<Object> generateResponse(String message, HttpStatus status,Object data){
    Map<String,Object> map=new HashMap<>();

    map.put("message",message);
    map.put("status",status);
    map.put("data",data);

    return new ResponseEntity<Object>(map,status);
  }

  public static ResponseEntity<Object> generateResponseWithoutMsg(HttpStatus status,Object data){
    Map<String,Object> map=new HashMap<>();

    map.put("status",status);
    map.put("data",data);

    return new ResponseEntity<Object>(map,status);
  }

  public static ResponseEntity<Object> generateResponseWithoutData(HttpStatus status,String message){
    Map<String,Object> map=new HashMap<>();

    map.put("status",status);
    map.put("message",message);

    return new ResponseEntity<Object>(map,status);
  }
}
