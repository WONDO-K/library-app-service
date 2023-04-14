package com.group.libraryapp.temp;

import javax.persistence.*;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    private String name;

    @OneToOne
    private Address address;

    public void setAddress(Address address) {
        this.address = address;
        this.address.setPerson(this); // 나 자신과 연결시켜 주면 setter 한번에 두 곳 모두 연결된다.
    }
}
