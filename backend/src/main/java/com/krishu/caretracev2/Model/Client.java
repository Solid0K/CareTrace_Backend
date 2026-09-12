package com.krishu.caretracev2.Model;

import com.krishu.caretracev2.ClientRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection="user")
public class Client {

    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private ClientRole role;
}
