package com.group.libraryapp.domain.user.loanhistory;

import com.group.libraryapp.domain.user.User;

import javax.persistence.*;

@Entity
public class UserLoanHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false)
    @ManyToOne
    private User user; // JPA는 user를 어떤 필드에 매핑해야하는지 알 수 없기 때문에 알려줘야한다.

    private String bookName;

    private boolean isReturn;

    protected UserLoanHistory(){

    }

    public UserLoanHistory(User user, String bookName) {
        this.user = user;
        this.bookName = bookName;
        this.isReturn = false;
    }
    public void doReturn(){
        this.isReturn = true;
    }

    public String getBookName() {
        return this.bookName;
    }
}
