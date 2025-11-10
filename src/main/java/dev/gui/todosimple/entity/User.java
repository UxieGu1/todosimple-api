    package dev.gui.todosimple.entity;

    import com.fasterxml.jackson.annotation.JsonProperty;
    import dev.gui.todosimple.entity.enums.ProfileEnum;
    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.Size;
    import lombok.*;
    import java.util.ArrayList;
    import java.util.HashSet;
    import java.util.List;
    import java.util.Set;
    import java.util.stream.Collectors;

    @Entity
    @Table(name = "tb_user")
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @EqualsAndHashCode
    public class User {

        public interface CreateUser{

        }
        public interface UpdateUser{

        }

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id",unique = true)
        private Long id;

        @Column(name = "username", nullable = false, unique = true)
        @NotBlank(groups = CreateUser.class)
        @Size(groups = CreateUser.class, min = 4, max = 100)
        private String username;

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @Column(name = "password", nullable = false)
        @NotBlank(groups = {CreateUser.class, UpdateUser.class})
        @Size(groups = {CreateUser.class, UpdateUser.class}, min = 8, max = 60)
        private String password;

        @OneToMany(mappedBy = "user",  cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        private List<Task> tasks = new ArrayList<Task>();

        @ElementCollection(fetch = FetchType.EAGER)
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @CollectionTable(name = "user_profile")
        @Column(name = "profile", nullable = false)
        private Set<Integer> profiles = new HashSet<>();

        public Set<ProfileEnum> getProfiles() {
            return this.profiles.stream()
                    .map(ProfileEnum::getByCode)
                    .collect(Collectors.toSet());
        }

        public void addProfile(ProfileEnum profile) {
            this.profiles.add(profile.getCode());
        }

        public void setProfiles(Set<ProfileEnum> profileEnums) {
            this.profiles = profileEnums.stream()
                    .map(ProfileEnum::getCode)
                    .collect(Collectors.toSet());
        }

        public void setProfile(ProfileEnum profile) {
            this.profiles.clear();
            this.profiles.add(profile.getCode());
        }
    }
