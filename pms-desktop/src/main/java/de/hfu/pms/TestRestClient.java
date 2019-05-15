package de.hfu.pms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hfu.pms.shared.dto.DoctoralStudentDTO;
import de.hfu.pms.shared.dto.PersonalDataDTO;
import de.hfu.pms.shared.dto.UserDTO;
import de.hfu.pms.shared.enums.UserRole;
import httpConector.RestClient;
import org.apache.http.client.HttpResponseException;

import java.io.IOException;

public class TestRestClient {

    public static void main(String[] args) {

        RestClient restClient = new RestClient();
        restClient.setLoginCredentials("admin", "1234");

        ObjectMapper mapper = new ObjectMapper();


        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("test");
        userDTO.setPassword("test1234");
        userDTO.setUserRole(UserRole.USER);
        userDTO.setEmail("test.email@example.de");
        userDTO.setForename("Max");
        userDTO.setLastname("Mustermann");

        String json = null;
        try {
            json = mapper.writeValueAsString(userDTO );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(json);

        try {
            String response = restClient.postJson("http://localhost:8080/user/create", json);
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            System.out.println("hier start");
            String response = restClient.get("http://localhost:8080/student/get/1");
            System.out.println(response);
            System.out.println("hier ende");
        } catch (HttpResponseException e) {
            e.printStackTrace();
            System.out.println("dasd");
            System.out.println(e.getStatusCode());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("das");
        }


        DoctoralStudentDTO dto = new DoctoralStudentDTO();
        dto.setId(5L);
        PersonalDataDTO personalData = new PersonalDataDTO();
        personalData.setLastName("Cassisi");
        dto.setPersonalData(personalData);

        try {
            String jsonDto = mapper.writeValueAsString(dto);
            System.out.println(jsonDto);
            String response = restClient.postJson("http://localhost:8080/student/create", jsonDto);
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
