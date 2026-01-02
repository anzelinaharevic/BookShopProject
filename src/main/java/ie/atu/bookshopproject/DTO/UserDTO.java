package ie.atu.bookshopproject.DTO;

public class UserDTO {
    private Long loginId;
    private String username;
    private String password;
    private Boolean admin;

    public UserDTO() {}

    public UserDTO(Long loginId, String username, String password, Boolean admin) {
        this.loginId = loginId;
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword (String password){
        this.password = password;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}
