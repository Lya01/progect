package services;

import repositories.ClienteRepository;
import entities.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import support.MailClienteNonEsisteException;
@Service
public class AccountingService {
    @Autowired
    private ClienteRepository clienteRepository;

    public void registra(String[] pass, String[] users, String[] ems, Cliente cliente){
        String usernameAdmin="admin";
        String passwordAdmin="admin";
        String clientName="shop-client";
        String role = "admin";
        String[] email=ems;
        String[] password=pass;
        String[] lastName=users;
        String serverUrl="http://localhost:8080/auth";
        String realm="shop";
        String clientID="shop-client";
        String clientSecret="k7xwjrx81N16w3OnkQkq00NepKjlimhM";

        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(clientID)
                .clientSecret(clientSecret)
                .username(usernameAdmin)
                .password(passwordAdmin)
                .build();

        for(int i=0; i< email.length; i++){

            //Definisco l'cliente
            UserRepresentation user = new UserRepresentation();
            user.setEnabled(true);
            user.setUsername(lastName[i]);
            user.setEmail(email[i]);

            user.setAttributes(Collections.singletonMap("origin", Arrays.asList("demo")));

            //Get realm
            RealmResource realmResource = keycloak.realm(realm);
            UsersResource usersRessource = realmResource.users();

            //creazione cliente
            System.out.println("Creazione cliente");
            Response response=usersRessource.create(user);
            System.out.printf("Response: %s %s%n", response.getStatus(), response.getStatusInfo());
            System.out.println("response location"+response.getLocation());
            String userId = CreatedResponseUtil.getCreatedId(response);
            System.out.printf("User created with userId: %s%n", userId);

            // Define password credential
            CredentialRepresentation passwordCred = new CredentialRepresentation();
            passwordCred.setTemporary(false);
            passwordCred.setType(CredentialRepresentation.PASSWORD);
            passwordCred.setValue(password[i]);

            UserResource userResource = usersRessource.get(userId);

            // Set password credential
            userResource.resetPassword(passwordCred);

            // Get client
            ClientRepresentation app1Client = realmResource.clients().findByClientId(clientName).get(0);

            // Get client level role (requires view-clients role)
            RoleRepresentation userClientRole = realmResource.clients().get(app1Client.getId()).roles().get(role).toRepresentation();

            // Assign client level role to user
            userResource.roles().clientLevel(app1Client.getId()).add(Arrays.asList(userClientRole));

            // Send password reset E-Mail
            // VERIFY_EMAIL, UPDATE_PROFILE, CONFIGURE_TOTP, UPDATE_PASSWORD, TERMS_AND_CONDITIONS
            usersRessource.get(userId).executeActionsEmail(Arrays.asList("UPDATE_PASSWORD"));

            System.out.println("Registrato");
            clienteRepository.save(cliente);

        }
    }//registra





}
