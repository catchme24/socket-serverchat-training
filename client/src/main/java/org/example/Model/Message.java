package org.example.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Message {
    private String text;
    private Date date;
    private Sender sender;
}
