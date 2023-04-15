package com.group.libraryapp.domain.user;



import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id = null;
    @Column(nullable = false, length = 20/* name = "name"*/) // == name varchar(20) 필드 이름과 테이블 컬럼이름이 동일한 경우 생략가능하다.
    private String name; // name은 null이 허용되지 않고 글자 제한이 있다는 것을 알려주어야 하기 때문에 어노테이션 생략 불가
    private Integer age; // age는 null이어도 되고 제한이 없고 Integer와 int는 동일하기 때문에 생략가능

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLoanHistory> userLoanHistoies = new ArrayList<>();

    protected User() {

    }

    public User(String name, Integer age) {
        if (name == null || name.isBlank()){
            throw new IllegalStateException(String.format("잘못된 name(%s)이 들어왔습니다.",name));
        }
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Long getId() {
        return id;
    }

    public void updateName(String name){
        this.name = name;
    }

    public void removeOneHisory() {
        userLoanHistoies.removeIf(history -> "책1".equals(history.getBookName()));
    }
    public void loanBook(String bookName){
        this.userLoanHistoies.add(new UserLoanHistory(this,bookName));
    }

    public void returnBook(String bookName){
        UserLoanHistory targetHistory = this.userLoanHistoies.stream()
                .filter(history -> history.getBookName().equals(bookName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        targetHistory.doReturn();
    }
}
