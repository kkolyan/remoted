package net.kkolyan.space;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.File;
import java.io.IOException;

/**
 * @author nplekhanov
 */
public class TestSave {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationConfig(mapper.getSerializationConfig()
//                .with(SerializationConfig.Feature.INDENT_OUTPUT)
                .withSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT)
        );
        PathNode node = PathTraversing.getTree(new File("D:/dev"));
        mapper.writeValue(new File("./TestSave.json"), node);
    }
}
