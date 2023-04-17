package com.group.libraryapp.service.service.book;

import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.domain.book.BookRepository;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository;
import com.group.libraryapp.dto.book.request.BookCreateRequest;
import com.group.libraryapp.dto.book.request.BookLoanRequest;
import com.group.libraryapp.dto.book.request.BookReturnRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserLoanHistoryRepository userLoanHistoryRepository;
    private final UserRepository userRepository;


    @Transactional
    public void saveBook(BookCreateRequest request){
        bookRepository.save(new Book(request.getName()));
    }

    @Transactional
    public void loanBook(BookLoanRequest request) {
        // 1. 책 정보를 가져온다
        Book book = bookRepository.findByName(request.getBookName()).orElseThrow(IllegalArgumentException::new);

        // 2. 대출 기록을 확인해서 대출 여부를 확인한다.
        // 3. 확인 후, 대출 중이라면 예외 발생시킨다.
        if (userLoanHistoryRepository.existsByBookNameAndIsReturn(book.getName(), false)){
            throw new IllegalArgumentException("이미 대출된 책입니다.");
        }
        // 4. 유저 정보를 가져온다.
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);
        user.loanBook(book.getName());

        // 5. 유저 정보와 책 정보를 기반으로 UserLaonHistory를 저장
        //    생성자에서 isReturn을 false로 지정해주면 생략할 수 있다.
        //userLoanHistoryRepository.save(new UserLoanHistory(user, book.getName()));


    }
    @Transactional
    public void returnBook(BookReturnRequest request) {
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);

//        UserLoanHistory history = userLoanHistoryRepository.findByUserIdAndBookName(user.getId(),request.getBookName())
//                .orElseThrow(IllegalArgumentException::new);
//        history.doReturn(); // @Transactional -> dirty-check로 인해 변경값을 인식하고 자동으로 업데이트한다.
        System.out.println("hello");
        user.returnBook(request.getBookName());
    }
}
