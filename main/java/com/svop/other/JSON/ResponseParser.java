package com.svop.other.JSON;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ResponseParser {

    public ObjectMapper parseErrorFromId(ArrayList<Integer> integers)
    {
        ObjectMapper mapper=new ObjectMapper();
        JsonNode root=mapper.createObjectNode();
        JsonNode objectNode=mapper.createObjectNode();
        ((ObjectNode)objectNode).put("success",true);
        ArrayNode arrayNode=((ObjectNode)objectNode).putArray("id");

        for(Integer id:integers){arrayNode.add(id);}

        return mapper;
    }
    public ObjectMapper parseError()
    {
        ObjectMapper mapper=new ObjectMapper();
        ObjectNode objectNode=mapper.createObjectNode();
        objectNode.put("success",false);
        return mapper;
    }
    public ObjectMapper parseSycsess()
    {
        ObjectMapper mapper=new ObjectMapper();
        ObjectNode objectNode=mapper.createObjectNode();
        objectNode.put("success",true);
        return mapper;
    }

}
