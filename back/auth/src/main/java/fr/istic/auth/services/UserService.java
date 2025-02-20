package fr.istic.auth.services;

import fr.istic.auth.dto.AuthRequest;
import fr.istic.auth.dto.AuthResponse;
import fr.istic.auth.dto.UserDto;
import fr.istic.auth.dto.UserInfoDto;
import fr.istic.auth.enums.Roles;
import fr.istic.auth.exceptions.*;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;
import java.util.Random;
import javax.naming.directory.*;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import java.util.List;

@Service
public class UserService {

    private final LdapTemplate ldapTemplate;


    String[] weakPasswords = {
            "12345", "54321", "11111", "22222", "33333", "44444", "55555", "66666", "77777", "88888", "99999",
            "00000", "abcde", "edcba", "qwert", "asdfg", "zxcvb", "poiuy", "lkjhg", "mnbvc", "hello", "world",
            "test1", "pass1", "admin", "guest", "login", "apple", "peach", "grape", "lemon", "choco", "candy",
            "pizza", "tiger", "panda", "happy", "smile", "lucky", "sunny", "cloud", "storm", "light", "darky",
            "ghost", "spook", "magic", "trick", "treat", "skull", "bones", "money", "dolla", "bucks", "score",
            "power", "super", "great", "ninja", "samur", "sword", "arrow", "flame", "blaze", "frost", "earth",
            "water", "space", "alien", "cyber", "robot", "metal", "steel", "stone", "rocky", "happy", "globe",
            "ocean", "river", "white", "black", "green", "red01", "blue2", "smoke", "joker", "chaos", "night",
            "vivid", "brave", "storm", "angel", "demon", "quest", "trips", "block", "craft", "pixel", "hello",
            "teddy", "bunny", "poppy", "lucky", "venus", "marsy", "jumpy", "zappy"
    };

    public UserService(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    // CREATE a new user
    public UserDto createUser( UserDto userDto) throws Exception {
        // Validate role if provided
        if (userDto.getRole() != null && !userDto.getRole().isEmpty()) {
            if (!Roles.contains(userDto.getRole())) {
                throw new InvalidRoleException("This role does not exist: " + userDto.getRole());
            }
        }

        if(userDto.getEmail() == null || userDto.getEmail().isEmpty())
        {
            throw new UserNotCompleteException("User email not given");
        }

        // Build the DN for the new user: e.g., cn=<email>,ou=users,dc=example,dc=com
        LdapName dn = new LdapName("");
        dn.add(new Rdn("ou", "users"));
        dn.add(new Rdn("cn", userDto.getEmail()));

        Random random = new Random();
        String password = weakPasswords[random.nextInt(weakPasswords.length)];
        userDto.setPassword(password);

        // Check if a user with this email already exists
        try {
            ldapTemplate.lookup(dn);
            // If lookup doesn't throw an exception, the entry exists.
            throw new UserAlreadyExistsException("User with email " + userDto.getEmail() + " already exists.");
        } catch (NameNotFoundException ex) {
            // If not found, proceed to create the user.
            Attributes attrs = getAttributes(userDto);
            ldapTemplate.bind(dn, null, attrs);

            // Optionally, add the user to a group for role-based authorization:
            if (userDto.getRole() != null && !userDto.getRole().isEmpty()) {
                LdapName groupDn = new LdapName("");
                groupDn.add(new Rdn("ou", "groups"));
                groupDn.add(new Rdn("cn", userDto.getRole()));
                BasicAttribute memberAttr = new BasicAttribute("member", dn.toString());
                ModificationItem mod = new ModificationItem(DirContext.ADD_ATTRIBUTE, memberAttr);
                ldapTemplate.modifyAttributes(groupDn, new ModificationItem[]{mod});

            }
        }

        return userDto;
    }

    private static Attributes getAttributes(UserDto userDto) {
        Attributes attrs = new BasicAttributes();
        if(isNotValidUser(userDto)){
            throw new UserNotCompleteException("all fields of user must be complete!");
        }
        // Object Classes (inetOrgPerson for general user attributes)
        BasicAttribute ocAttr = new BasicAttribute("objectClass");
        ocAttr.add("inetOrgPerson");
        attrs.put(ocAttr);

        // Required user attributes
        attrs.put("cn", userDto.getEmail());
        attrs.put("sn", userDto.getNom());
        attrs.put("givenName", userDto.getPrenom());
        attrs.put("mail", userDto.getEmail());
        attrs.put("uid", userDto.getEmail());
        attrs.put("userPassword", userDto.getPassword());
        attrs.put("labeledURI", userDto.getImage());
        // Repurpose the employeeType attribute to store first authentication status
        attrs.put("employeeType", "true");

        return attrs;
    }


    private static boolean isNotValidUser(UserDto userDto) {
        return userDto.getImage() == null || userDto.getImage().isEmpty() ||
                userDto.getEmail() == null || userDto.getEmail().isEmpty() ||
                userDto.getRole() == null || userDto.getRole().isEmpty() ||
                userDto.getNom() == null || userDto.getNom().isEmpty() ||
                userDto.getPrenom() == null || userDto.getPrenom().isEmpty();
    }

    // UPDATE an existing users password
    public String updatePasswordUser(String userId,UserDto userDto) throws Exception {
        LdapName dn = new LdapName("");
        dn.add(new Rdn("ou", "users"));
        dn.add(new Rdn("cn", userId));


        if(userDto.getPassword() == null || userDto.getPassword().isEmpty()){
            throw new EmptyPasswordException("The password is not provided");
        }

        try {
            ldapTemplate.lookup(dn);
            ModificationItem[] mods = {
                    new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                            new BasicAttribute("userPassword", userDto.getPassword())),
                    new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                            new BasicAttribute("employeeType", "false"))
            };
            ldapTemplate.modifyAttributes(dn, mods);
            return "User Password updated: " + userId + "password : "+userDto.getPassword();
        } catch (Exception ex) {
            throw new UserNotFoundException("User with email " + userId + " doesn't exist.");
        }
    }


    // REINITIALIZE an existing users password
    public String reinitializePasswordUser(String userId) throws Exception {
        LdapName dn = new LdapName("");
        dn.add(new Rdn("ou", "users"));
        dn.add(new Rdn("cn", userId));

        try {
            ldapTemplate.lookup(dn);
            Random random = new Random();
            String password = weakPasswords[random.nextInt(weakPasswords.length)];
            ModificationItem[] mods = {
                    new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                            new BasicAttribute("userPassword", password)),
                    new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                            new BasicAttribute("employeeType", "true"))
            };
            ldapTemplate.modifyAttributes(dn, mods);
            return "User updated: " + userId + "the new password is : "+ password;
        } catch (Exception ex) {
            throw new UserNotFoundException("User with email " + userId + " doesn't exist.");
        }
    }


    // UPDATE an existing users password
    public String updateProfilePictureUser(String userId,UserDto userDto) throws Exception {
        LdapName dn = new LdapName("");
        dn.add(new Rdn("ou", "users"));
        dn.add(new Rdn("cn", userId));

        if(userDto.getImage() == null || userDto.getImage().isEmpty()){
          throw new EmptyPictureException("The image is not provided");
        }

        try {
            ldapTemplate.lookup(dn);
            ModificationItem[] mods = {
                    new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                            new BasicAttribute("labeledURI", userDto.getImage()))
            };
            ldapTemplate.modifyAttributes(dn, mods);
            return "User updated: " + userId;
        } catch (Exception ex) {
            throw new UserNotFoundException("User with email " + userId + " doesn't exist.");
        }
    }

    // DELETE an existing user
    public String deleteUser(String userId) {
        try {
            LdapName dn = new LdapName("");
            dn.add(new Rdn("ou", "users"));
            dn.add(new Rdn("cn", userId));

            ldapTemplate.lookup(dn); // Check if user exists

            String userDn = "cn=" + userId + ",ou=users";
            String groupSearchBase = "ou=groups";
            String filter = "(member=" + userDn + ")";

            List<LdapName> groupDns = ldapTemplate.search(
                    groupSearchBase,
                    filter,
                    (ContextMapper<LdapName>) ctx -> new LdapName(((DirContextOperations) ctx).getDn().toString())
            );

            for (LdapName groupDn : groupDns) {
                BasicAttribute memberAttr = new BasicAttribute("member", userDn);
                ModificationItem mod = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, memberAttr);
                ldapTemplate.modifyAttributes(groupDn, new ModificationItem[]{mod});
            }

            ldapTemplate.unbind(userDn);
            return "User deleted: " + userId;
        } catch (Exception e) {
            throw new UserNotFoundException("User not found: " + userId);
        }
    }

    // LOGIN (authenticate) a user
    public AuthResponse login(AuthRequest authRequest) {
        String searchBase = "ou=users";
        String filter = "(cn=" + authRequest.getMail() + ")";

        boolean authenticated = ldapTemplate.authenticate(searchBase, filter, authRequest.getPassword());

        if (authenticated) {
            String userDn = "cn=" + authRequest.getMail() + ",ou=users";
            UserInfoDto userInfo = getUserDetails(authRequest.getMail());

            List<String> groupRoles = ldapTemplate.search(
                    "ou=groups",
                    "(member=" + userDn + ")",
                    (AttributesMapper<String>) attrs -> (String) attrs.get("cn").get()
            );

            String role = groupRoles.isEmpty() ? "unknown" : groupRoles.get(0);
            return new AuthResponse("Login successful for user: " + authRequest.getMail(), role,
                    userInfo.getNom(), userInfo.getPrenom(), userInfo.getMail(), userInfo.getImage(), userInfo.isFirstLogin());
        } else {
            throw new UserNotFoundException("Invalid credentials");
        }
    }


    public UserInfoDto getUserDetails(String email) {
        try {
            // Construct the correct DN
            LdapName dn = new LdapName("");
            dn.add(new Rdn("ou", "users"));
            dn.add(new Rdn("cn", email));

            // Fetch user attributes
            DirContextOperations ctx = ldapTemplate.lookupContext(dn);
            String nom = ctx.getStringAttribute("sn") != null ? ctx.getStringAttribute("sn") : "";
            String prenom = ctx.getStringAttribute("givenName") != null ? ctx.getStringAttribute("givenName") : "";
            String mail = ctx.getStringAttribute("mail") != null ? ctx.getStringAttribute("mail") : "";
            String image = ctx.getStringAttribute("labeledURI") != null ? ctx.getStringAttribute("labeledURI") : "";
            boolean firstLogin = ctx.getStringAttribute("employeeType") != null
                    && Boolean.parseBoolean(ctx.getStringAttribute("employeeType"));

            return new UserInfoDto(nom, prenom, mail, image, firstLogin);
        }
        catch (Exception e) {
            throw new RuntimeException("An error occurred while fetching details for: " + email, e);
        }
    }
    public List<UserInfoDto> getAllUsersDetails() {
        String searchBase = "ou=users";
        String filter = "(objectClass=inetOrgPerson)";

        return ldapTemplate.search(
                searchBase,
                filter,
                (AttributesMapper<UserInfoDto>) attrs -> {
                    String nom = attrs.get("sn") != null ? (String) attrs.get("sn").get() : "";
                    String prenom = attrs.get("givenName") != null ? (String) attrs.get("givenName").get() : "";
                    String mail = attrs.get("mail") != null ? (String) attrs.get("mail").get() : "";
                    String image = attrs.get("labeledURI") != null ? (String) attrs.get("labeledURI").get() : "";
                    boolean firstLogin = false;
                    if (attrs.get("employeeType") != null) {
                        firstLogin = Boolean.parseBoolean((String) attrs.get("employeeType").get());
                    }

                    String userDn = "cn=" + mail + ",ou=users";

                    // Search for groups in which this user is a member
                    // Groups are located under "ou=groups,dc=istic,dc=fr" and have the 'member' attribute.
                    List<String> groupRoles = ldapTemplate.search(
                            "ou=groups",
                            "(member=" + userDn + ")",
                            (AttributesMapper<String>) groupAttrs -> (String) groupAttrs.get("cn").get()
                    );

                    // If the user belongs to at least one group, pick the first role; otherwise "unknown"
                    String role = groupRoles.isEmpty() ? "unknown" : groupRoles.get(0);

                    return new UserInfoDto(nom, prenom, mail, image, firstLogin,role);
                }
        );
    }


}
