package com.sandeep.demoemployee;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Test
public class StreamvarTest {
  // public static final String svcInstanceUrl =
  // "http://fwskapi.dms.local.usw2.ficoanalyticcloud.com/design/fn-t4cl5nkwlc-t4dhq1oup4-drd-1-ds-design1?logLevel=debug";
  public static final String svcInstanceUrl = "http://fn-42a137vynyr-42a17vq7ohj-drd-1-ds-design-design.dev.onprem.dmsuitecloud.com";
  public static final String inputFile1Path = "/Users/sandepkumar/IdeaProjects/emp-org-chart/src/test/java/com/sandeep/demoemployee/inputs/i0.json";
  public static final String inputFile2Path = "/Users/sandepkumar/IdeaProjects/emp-org-chart/src/test/java/com/sandeep/demoemployee/inputs/i1.json";
  public static final String inputFile3Path = "/Users/sandepkumar/IdeaProjects/emp-org-chart/src/test/java/com/sandeep/demoemployee/inputs/i2.json";
  //public static final String inputFile4Path = "C:/b/fwsd/20k.json";
  public static final String outputFile1Path = "/Users/sandepkumar/IdeaProjects/emp-org-chart/src/test/java/outputs/o0.json";
  public static final String outputFile2Path = "/Users/sandepkumar/IdeaProjects/emp-org-chart/src/test/java/outputs/o1.json";
  public static final String outputFile3Path = "/Users/sandepkumar/IdeaProjects/emp-org-chart/src/test/java/outputs/o2.json";
  //public static final String outputFile4Path = "C:/b/fwsd/out/o20k.json";

  @Test
  public void testStreamvarExecutionInit() throws Exception {
    testStreamvarExec(inputFile1Path, outputFile1Path, svcInstanceUrl);

  }

  @Test
  public void testStreamvarExecutionDay1() throws Exception {
    testStreamvarExec(inputFile2Path, outputFile2Path, svcInstanceUrl);

  }

  @Test
  public void testStreamvarExecutionDay2() throws Exception {
    testStreamvarExec(inputFile3Path, outputFile3Path, svcInstanceUrl);

  }

  @Test
  public void testStreamvarExecution20kTestModel() throws Exception {
    long startTime = System.nanoTime();
//    testStreamvarExec(inputFile4Path, outputFile4Path, svcInstanceUrl);
    long endTime = System.nanoTime();
    System.out.println("Took " + (endTime - startTime) + " ns");

  }

  private void testStreamvarExec(String inputFilePath, String outputFilePath, String svcInstanceUrl) throws IOException {
    ObjectMapper mapper = getObjectMapper();
    int failedRecordsCount = 0;
    File inputFile = new File(inputFilePath);
    File outputFile = new File(outputFilePath);
    outputFile.delete();
    outputFile.createNewFile();
    List<String> inputs = FileUtils.readLines(inputFile, "UTF-8");
//    try {
//      Executors.newFixedThreadPool(100).submit(() -> inputs.parallelStream().forEach(input -> {
//        try {
//          JsonNode svBkmInput = mapper.readTree(input);
//          Map<String, JsonNode> websvcInput = Collections.singletonMap("Input_Data1", svBkmInput);
//          JsonNode value = executeWebService(websvcInput, svcInstanceUrl);
//          if (value != null) {
//            String output = mapper.writeValueAsString(mapper.treeToValue(value.get("Decision1"), HashMap.class));
////            FileUtils.write(outputFile, output + "\n", "UTF-8", true);
//          }
//        } catch (Exception e) {
//          e.printStackTrace();
//        }
//      })).get();
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    } catch (ExecutionException e) {
//      e.printStackTrace();
//    }
     for (String input : inputs) {
     try {
     JsonNode svBkmInput = mapper.readTree(input);
     Map<String, JsonNode> websvcInput = Collections.singletonMap("Input_Data1", svBkmInput);
     JsonNode value = executeWebService(websvcInput, svcInstanceUrl);
     if (value != null) {
     String output = mapper.writeValueAsString(mapper.treeToValue(value.get("Decision1"),
     HashMap.class));
     FileUtils.write(outputFile, output + "\n", "UTF-8", true);
     }
     } catch (Exception e) {
     e.printStackTrace();
     failedRecordsCount++;
     }
     }
     System.out.println("Failed records:" + failedRecordsCount);
  }

  private JsonNode executeWebService(Map<String, JsonNode> input, String svcInstanceUrl) {
    RestTemplate restTemplate = new RestTemplate();
    JsonNode output = restTemplate.postForObject(svcInstanceUrl, new HttpEntity<>(input, getHeaders()), JsonNode.class);
//    try {
//      ObjectMapper mapper = getObjectMapper();
//      String x = mapper.writeValueAsString(mapper.treeToValue(output, HashMap.class));
//      // System.out.println(x);
//    } catch (JsonProcessingException e) {
//      throw new RuntimeException(e);
//    }
    return output;
  }

  private ObjectMapper getObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
    return mapper;
  }
    HttpHeaders getHeaders(){
        return new HttpHeaders() {{
            String auth = "jwt eyJraWQiOiJJYW1Kd2tLZXkiLCJhbGciOiJFUzI1NiJ9.eyJqdGkiOiJZWDdpN3dDV1JYVEdiQ21zNTFKblF3IiwiaWF0IjoxNTc3OTY0MjE1LCJpc3MiOiJJQU0iLCJhdWQiOiI0MmExMzd2eW55ciIsInN1YiI6IjQyYTEzN3Z5bnlyIiwiZXhwIjoxNTc3OTY3ODE1LCJ0a25mbyI6IntcImFjY2Vzc190b2tlblwiOlwiYmRiY2NjNzktMjZkMC00YTMwLTgyNDYtMmY1NGRiODQ0M2VhXCIsXCJzY29wZVwiOltcInNlcnZpY2VJbnN0YW5jZTovY29tLmZpY28uZG1wLnMzL3Jlc3Qvc2VydmljZS9zb2x1dGlvbmluc3RhbmNlcy80MmExM2Q0Nmhnby9hcGlcIixcInNlcnZpY2VJbnN0YW5jZTovY29tLmZpY28uZG1wLnMzL3Jlc3Qvc2VydmljZS9zb2x1dGlvbmluc3RhbmNlcy80MmExM2ZoZThhMy9hcGlcIixcIm89RklDTy1QVE8tVEVOQU5UXCIsXCJzZXJ2aWNlSW5zdGFuY2U6L2NvbS5maWNvLmRtcC5zMy9yZXN0L3NlcnZpY2Uvc29sdXRpb25pbnN0YW5jZXMvNDJhMTNhejl2OG8vYXBpXCIsXCJzZXJ2aWNlSW5zdGFuY2U6L2NvbS5maWNvLmRtcC5zMy9yZXN0L3NlcnZpY2Uvc29sdXRpb25pbnN0YW5jZXMvNDJhMTNscWN4cHgvYXBpXCIsXCJzZXJ2aWNlSW5zdGFuY2U6L2NvbS5maWNvLmRtcC5zMy9yZXN0L3NlcnZpY2Uvc29sdXRpb25pbnN0YW5jZXMvNDJhMTNocnpyYnYvYXBpXCIsXCJzZXJ2aWNlSW5zdGFuY2U6L2NvbS5maWNvLmRtcC5jaGFuZ2Vsb2cvcmVzdC9zZXJ2aWNlL3NvbHV0aW9uaW5zdGFuY2VzLzQyYTEzOGh2Z3EzL2FwaVwiLFwiY2xpZW50X2lkOjQyYTEzN3Z5bnlyXCIsXCJzZXJ2aWNlSW5zdGFuY2U6L2NvbS5maWNvLmRtcC5tZXNzYWdpbmcva2Fma2EvcmVzdC9zZXJ2aWNlL3NvbHV0aW9uaW5zdGFuY2VzLzQyYTEzOXZtOWZ4L2FwaVwiLFwiREVMRVRFOm1hbmFnZXIuZGV2Lm9ucHJlbS5kbXN1aXRlY2xvdWQuY29tXCIsXCJQT1NUOm1hbmFnZXIuZGV2Lm9ucHJlbS5kbXN1aXRlY2xvdWQuY29tXCIsXCJzZXJ2aWNlSW5zdGFuY2U6L2NvbS5maWNvLmRtcC5zMy9yZXN0L3NlcnZpY2Uvc29sdXRpb25pbnN0YW5jZXMvNDJhMTNqc2M1dngvYXBpXCIsXCJHRVQ6bWFuYWdlci5kZXYub25wcmVtLmRtc3VpdGVjbG91ZC5jb21cIixcInNlcnZpY2VJbnN0YW5jZTovY29tLmZpY28uZG1wLmZ3c2QvcmVzdC9zZXJ2aWNlL3NvbHV0aW9uaW5zdGFuY2VzLzQzdXJuYmUwaGM5L2FwaVwiLFwiUFVUOm1hbmFnZXIuZGV2Lm9ucHJlbS5kbXN1aXRlY2xvdWQuY29tXCJdLFwicmVzdHJpY3Rpb25zXCI6e1wiZW5hYmxlbGNtXCI6XCJ0cnVlXCIsXCJtYXhjb21wb25lbnRzXCI6XCIxNVwiLFwiZW5hYmxlZmFzdFwiOlwidHJ1ZVwifSxcInJlYWxtXCI6XCIvRmljb0FuYWx5dGljQ2xvdWRcIixcInRva2VuX3R5cGVcIjpcIkJlYXJlclwiLFwiZXhwaXJlc19pblwiOjM1OTksXCJvXCI6XCJGSUNPLVBUTy1URU5BTlRcIn0iLCJraWQiOiJGaWNvQW5hbHl0aWNDbG91ZCJ9.hBAIzkLF4AGEThY8oOVbbfjJGc0cutEBhktkFsBAkvBiEDzsFeGlidnOmkvjtxiFV-4Dzft3YO1IhDh-hXmtxg";
            set( "Authorization", auth );
        }};
    }
}
