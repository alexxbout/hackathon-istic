package fr.istic.app.authApi.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.istic.app.authApi.api.dtos.AuthResponse;
import fr.istic.app.authApi.api.dtos.AuthRequest;
import fr.istic.app.authApi.api.dtos.UserDto;
import fr.istic.app.authApi.api.dtos.UserInfoDto;
import fr.istic.app.authApi.api.utils.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
public class LdapUserService {

    @Value("${ldap.user.service.base-url}")
    private String BASE_URL;
    private final RestTemplate restTemplate = new RestTemplate();

    //  Create User
    public ResponseEntity<UserDto> createUser(UserDto userDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserDto> request = new HttpEntity<>(userDto, headers);
        try {
            // Perform the POST request to create the user
            return restTemplate.exchange(BASE_URL, HttpMethod.POST, request, UserDto.class);  // Return the response if the creation is successful
        } catch (HttpClientErrorException e) {
            // Handle client-side errors (e.g., 400 Bad Request, 404 Not Found)
            if (e.getStatusCode() == HttpStatus.CONFLICT) { // if user already exists
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);  // Invalid data sent
            } else if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Not found if resource is unavailable
            }else if(e.getStatusCode() == HttpStatus.BAD_REQUEST){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            // Handle other 4xx client errors
            return ResponseEntity.status(e.getStatusCode()).body(null);
        } catch (Exception e) {
            // Handle server-side errors or unexpected errors (e.g., 5xx, network issues)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    // Get All Users (Authenticated Request)
    public ResponseEntity<List<UserInfoDto>> getAllUsers(HttpServletRequest request) {


        return restTemplate.exchange(BASE_URL + "/all",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<UserInfoDto>>() {}
        );
    }

    //  Update Password
    public ResponseEntity<String> updatePassword(HttpServletRequest request, Map<String, String> password) {

        UserInfoDto userInfoDto = SessionUtils.getInformationUser(request);

        if(userInfoDto == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }

        String url = BASE_URL + "/updatePassword/" + userInfoDto.getMail();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        UserDto userDto = new UserDto();
        userDto.setPassword(password.get("password"));
        HttpEntity<UserDto> entity = new HttpEntity<>(userDto, headers);
        try {
            return restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
        } catch (HttpClientErrorException e) {
            String error = extractErrorMessageFromException(e);
            // If the error status code is 404, return it as part of the response
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
            // For other HTTP client errors, return a generic error message
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            // Catch other potential exceptions and return a generic error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    public ResponseEntity<String> reinitializePasswordUser(String mail ,HttpServletRequest request) {

        UserInfoDto userInfoDto = SessionUtils.getInformationUser(request);

        if(userInfoDto == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }

        if(!userInfoDto.getRole().equals("admin")){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not an Admin");
        }
        String url = BASE_URL + "/reinitializePassword/" + mail;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        try {
            return restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
        } catch (HttpClientErrorException e) {
            String error = extractErrorMessageFromException(e);
            // If the error status code is 404, return it as part of the response
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
            // For other HTTP client errors, return a generic error message
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            // Catch other potential exceptions and return a generic error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    // Update Profile Picture
    public ResponseEntity<String> updatePicture(HttpServletRequest request, Map<String, String> imagePath) {

        UserInfoDto userInfoDto = SessionUtils.getInformationUser(request);

        if(userInfoDto == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }

        String url = BASE_URL + "/updatePicture/" + userInfoDto.getMail();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        UserDto userDto = new UserDto();
        userDto.setImage(imagePath.get("image"));

        HttpEntity<UserDto> entity = new HttpEntity<>(userDto, headers);
        try {
            // Perform the exchange request
            return restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);  // Return the response if successful
        } catch (HttpClientErrorException e) {
            String error = extractErrorMessageFromException(e);
            // Handle client-side error (e.g., 404 Not Found)
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
            // Handle other 4xx errors
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            // Handle other general errors (e.g., 5xx, timeouts)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    // Delete User
    public ResponseEntity<String> deleteUser(String email,HttpServletRequest request) {

        UserInfoDto userInfoDto = SessionUtils.getInformationUser(request);

        if(userInfoDto == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
        if(!userInfoDto.getRole().equals("admin")){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not an Admin");
        }

        String url = BASE_URL + "/" + email;
        try {
            // Perform the DELETE request
            return restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);  // Return the response if successful
        } catch (HttpClientErrorException e) {
            // Handle client-side errors (e.g., 404 Not Found)
            String error = extractErrorMessageFromException(e);

            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
            // Handle other 4xx errors
            return ResponseEntity.status(e.getStatusCode()).body(error);
        } catch (Exception e) {
            // Handle server-side errors or any other unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }


    // Login User & Store Session
    public ResponseEntity<AuthResponse> loginUser(AuthRequest authRequest, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AuthRequest> entity = new HttpEntity<>(authRequest, headers);


        try {

            ResponseEntity<AuthResponse> authResponseEntity = restTemplate.exchange(BASE_URL + "/login", HttpMethod.POST, entity, AuthResponse.class);

            //  Create a persistent session
            HttpSession session = request.getSession(true);
            AuthResponse authResponse = authResponseEntity.getBody();
            session.setAttribute("USER_ROLE", authResponse.getRole()); // Save user details
            session.setAttribute("USER_IMAGE", authResponse.getImage()); // Save user details
            session.setAttribute("USER_FIRST_LOGIN", authResponse.isFirstLogin()); // Save user details
            session.setAttribute("USER_EMAIL", authResponse.getMail()); // Save user details
            session.setAttribute("USER_NOM", authResponse.getNom()); // Save user details
            session.setAttribute("USER_PRENOM", authResponse.getPrenom()); // Save user details
            session.setMaxInactiveInterval(30 * 60); // 30 minutes timeout
            return ResponseEntity.ok(authResponse);

        } catch (HttpClientErrorException e) {
            // Handle client-side errors (e.g., 401 Unauthorized, 400 Bad Request)
            AuthResponse authResponse = new AuthResponse();

            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                authResponse.setMessage(extractErrorMessageFromException(e));
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authResponse);  // Invalid credentials
            } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                authResponse.setMessage(extractErrorMessageFromException(e));

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(authResponse);  // Invalid request format
            }else if(e.getStatusCode() == HttpStatus.NOT_FOUND) {
                authResponse.setMessage(extractErrorMessageFromException(e));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(authResponse);  // Invalid request format

            }
            // For other client errors (e.g., 404 Not Found)
            return ResponseEntity.status(e.getStatusCode()).body(null);
        } catch (Exception e) {
            // Handle server-side errors or unexpected issues (e.g., 500 Internal Server Error)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    public ResponseEntity<String> logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Delete session from PostgreSQL
        }
        return ResponseEntity.ok("Logged out successfully.");
    }



    private String extractErrorMessageFromException(HttpClientErrorException e) {
        try {
            // Convert the error response body to a string, typically in JSON format.
            String responseBody = e.getResponseBodyAsString();

            // Use a simple JSON parser or manually parse the message depending on the response format.
            // Assuming the response contains a "message" field.
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.get("message").asText(); // Assuming the JSON has a "message" field
        } catch (Exception ex) {
            // If there is an issue parsing the error response, return the generic message.
            return "Error parsing the error response.";
        }
    }
}
