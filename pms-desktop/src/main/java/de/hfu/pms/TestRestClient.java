package de.hfu.pms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.hfu.pms.client.RestClient;
import de.hfu.pms.shared.dto.*;
import de.hfu.pms.shared.enums.UserRole;
import org.apache.http.client.HttpResponseException;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;

public class TestRestClient {

    public static void main(String[] args) {

        RestClient restClient = new RestClient();
        restClient.setLoginCredentials("admin", "1234");

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

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
        personalData.setDateOfBirth(LocalDate.of(1997, 12, 19));
        dto.setPersonalData(personalData);

        // add some support
        SupportDTO supportDTO = new SupportDTO();
        dto.setSupport(supportDTO);
        TravelCostUniversityDTO cost1 = new TravelCostUniversityDTO();
        cost1.setSum(BigDecimal.valueOf(100.50));
        cost1.setDate(LocalDate.now());

        supportDTO.setTravelCostUniversities(new HashSet<>());
        supportDTO.getTravelCostUniversities().add(cost1);

        try {
            String jsonDto = mapper.writeValueAsString(dto);
            System.out.println("POST: " + jsonDto);
            String response = restClient.postJson("http://localhost:8080/student/create", jsonDto);
            System.out.println("RESPONSE: " + response);

            DoctoralStudentDTO responseDto = mapper.readValue(response, DoctoralStudentDTO.class);
            System.out.println(responseDto);

        } catch (IOException e) {
            e.printStackTrace();
        }


        // --------------

    }

}
