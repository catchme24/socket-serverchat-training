package org.example.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.URL;

@Data
@AllArgsConstructor
public class Sender {

    private String nickname;
    private URL url;
}
