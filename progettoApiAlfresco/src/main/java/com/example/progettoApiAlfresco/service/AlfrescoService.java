package com.example.progettoApiAlfresco.service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class AlfrescoService {

        @Value("${alfresco.api.url}")
        private String alfrescoApiUrl;

        @Value("${alfresco.username}")
        private String username;

        @Value("${alfresco.password}")
        private String password;

        private RestTemplate restTemplate = new RestTemplate();

        public void AlfrescoService(RestTemplate restTemplate) {
                this.restTemplate = restTemplate;
        }

        public ObjectNode getFiles(String nodeId) {
                String url = UriComponentsBuilder.fromHttpUrl(alfrescoApiUrl)
                                .pathSegment("nodes", nodeId, "children")
                                .toUriString();
            
                HttpHeaders headers = new HttpHeaders();
                headers.setBasicAuth(username, password);
                HttpEntity<String> entity = new HttpEntity<>(headers);
            
                ResponseEntity<ObjectNode> response = restTemplate.exchange(url, HttpMethod.GET, entity, ObjectNode.class);
            
                ObjectNode body = response.getBody();
                if (body == null || !body.has("list")) {
                    return body;
                }
            
                ObjectNode filteredBody = new ObjectMapper().createObjectNode();
                filteredBody.putObject("list");
            
                JsonNode listNode = body.get("list");
                JsonNode entries = listNode.get("entries");
                
                for (JsonNode entry : entries) {
                    JsonNode entryNode = entry.get("entry");
                    if (entryNode != null && "cm:content".equals(entryNode.get("nodeType").asText())) {
                        ((ObjectNode) filteredBody.get("list")).withArray("entries").add(entry);
                    }
                }
            
                return filteredBody;
            }


        public ObjectNode getFile(String nodeId, String relativePath) {
                String url = UriComponentsBuilder.fromHttpUrl(alfrescoApiUrl)
                                .pathSegment("nodes", nodeId)
                                .queryParam("relativePath", relativePath)
                                .toUriString();

                HttpHeaders headers = new HttpHeaders();

                // headers.set("", "application/json");
                Map<String, String> params = new HashMap<String, String>();
                params.put("relativePath", relativePath);
                params.put("nodeId", nodeId);
                headers.setBasicAuth(username, password);
                HttpEntity<String> entity = new HttpEntity<>(headers);

                ResponseEntity<ObjectNode> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                                ObjectNode.class,
                                params);

                return response.getBody();

        }

 
        public ObjectNode createDirectory(String nodeId, Boolean autoRename, boolean majorVersion,
                        boolean versioningEnabled, JsonNode dto)
                        throws JsonMappingException, JsonProcessingException {
                String url = UriComponentsBuilder.fromHttpUrl(alfrescoApiUrl)
                                .pathSegment("nodes", nodeId, "children")
                                .queryParam("autoRename", autoRename)
                                .queryParam("majorVersion", majorVersion)
                                .queryParam("versioningEnabled", versioningEnabled)
                                .toUriString();
                ObjectMapper mapper = new ObjectMapper();
                HttpHeaders headers = new HttpHeaders();
                Map<String, String> params = new HashMap<String, String>();
                params.put("nodeId", nodeId);
                String jsonString = mapper.writeValueAsString(dto);
                headers.setBasicAuth(username, password);
                HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);

                ResponseEntity<ObjectNode> response = restTemplate.exchange(url, HttpMethod.POST, entity,
                                ObjectNode.class,
                                params);
                // List<DocFile> docFiles = mapper.readValue(response.getBody().toString(),
                // mapper.getTypeFactory().constructCollectionType(List.class, DocFile.class));
                return response.getBody();
        }

        public ObjectNode deleteFile(String nodeId, boolean permanent) {

                String url = UriComponentsBuilder.fromHttpUrl(alfrescoApiUrl)
                                .pathSegment("nodes", nodeId)
                                .queryParam("permanent", permanent)
                                .toUriString();

                HttpHeaders headers = new HttpHeaders();

                Map<String, String> params = new HashMap<String, String>();
                params.put("nodeId", nodeId);
                headers.setBasicAuth(username, password);
                HttpEntity<String> entity = new HttpEntity<>(headers);

                ResponseEntity<ObjectNode> response = restTemplate.exchange(url, HttpMethod.DELETE, entity,
                                ObjectNode.class,
                                params);

                return response.getBody();
        }

        public ObjectNode update(String nodeId, boolean majorVersion, String comment, String name, String content)
                        throws JsonProcessingException {
                String url = UriComponentsBuilder.fromHttpUrl(alfrescoApiUrl)
                                .pathSegment("nodes", nodeId, "content")
                                .queryParam("majorVersion", majorVersion)
                                .queryParam("comment", comment)
                                .queryParamIfPresent("name", Optional.ofNullable(name))
                                .toUriString();
                ObjectMapper mapper = new ObjectMapper();
               
                HttpHeaders headers = new HttpHeaders();
                headers.set("Content-Type", "application/octet-stream");
                Map<String, String> params = new HashMap<String, String>();
                byte[] bytesString = Base64.getDecoder().decode(content);
                params.put("nodeId", nodeId);
                headers.setBasicAuth(username, password);
                HttpEntity<byte[]> entity = new HttpEntity<>(bytesString,headers);

                ResponseEntity<ObjectNode> response = restTemplate.exchange(url, HttpMethod.PUT, entity,
                                ObjectNode.class,
                                params);
                // List<DocFile> docFiles = mapper.readValue(response.getBody().toString(),
                // mapper.getTypeFactory().constructCollectionType(List.class, DocFile.class));
                return response.getBody();
        }

        public ObjectNode move(String nodeId, JsonNode body) throws JsonProcessingException {

                String url = UriComponentsBuilder.fromHttpUrl(alfrescoApiUrl)
                                .pathSegment("nodes", nodeId, "move")
                                .toUriString();

                HttpHeaders headers = new HttpHeaders();

                ObjectMapper mapper = new ObjectMapper();
                String jsonString = mapper.writeValueAsString(body);
                Map<String, String> params = new HashMap<String, String>();
                
                params.put("nodeId", nodeId);
                headers.setBasicAuth(username, password);
                HttpEntity<String> entity = new HttpEntity<>(jsonString,headers);

                ResponseEntity<ObjectNode> response = restTemplate.exchange(url, HttpMethod.POST, entity,
                                ObjectNode.class,
                                params);

                return response.getBody();

        }

}
