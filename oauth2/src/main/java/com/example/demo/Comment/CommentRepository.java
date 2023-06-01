package com.example.demo.Comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Board.Board;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    // select * from comment_table where board_id=? order by id desc;
    List<CommentEntity> findAllByBoardOrderByIdDesc(Board board);
}