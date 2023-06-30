package com.example.chat.socket.io.event;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String name;

    private String roomNumber;

    private String content;

}