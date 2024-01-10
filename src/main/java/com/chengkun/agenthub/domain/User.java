package com.chengkun.agenthub.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class User {

    private String username;

    private String password;
}
