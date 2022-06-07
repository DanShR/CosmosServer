package com.dan.cosmos.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "UUID", nullable = false)
    private String UUID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser appUser;

    @Column(name = "ip")
    private String ip;

    @Column(name = "user_agent")
    private String userAgent;


    @Column(name ="expires_in")
    private Long expiresIn;

    @Column(name = "created")
    private Date created;

}
