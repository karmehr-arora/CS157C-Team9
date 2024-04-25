package edu.team9.fitconnect.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Table
public class User implements UserDetails {

    @PrimaryKey
    private String email;
    private String firstName;
    private String lastName;
    private String displayname;
    private int weight;
    private int heightInInches;
    private String password;
    private Role role;

    boolean isEnabled;
    boolean isLocked;

    LocalDateTime dateJoined;

    // profile picture
    @JsonIgnore
    private ByteBuffer pfpData;
    private String pfpFileType;
    private String fileName;

    public User(String displayname, String firstName, String lastName, String email, int weight, int height, String password, Role role, boolean isEnabled, boolean isLocked) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayname = displayname;
        this.weight = weight;
        this.heightInInches = height;
        this.password = password;
        this.role = role;
        this.isLocked = isLocked;
        this.isEnabled = isEnabled;
        dateJoined = LocalDateTime.now();
    }

    public void setProfilePhoto(ByteBuffer pfpData, String pfpFileType, String fileName){
        this.pfpData = pfpData;
        this.pfpFileType = pfpFileType;
        this.fileName = fileName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public enum Role{
        ADMIN,
        USER
    }
}
