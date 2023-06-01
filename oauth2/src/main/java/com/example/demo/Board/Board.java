package com.example.demo.Board;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import com.example.demo.Comment.CommentEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "board_table")
public class Board extends BaseEntity{ @Id // pk 컬럼 지정. 필수
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column(length = 20, nullable = false) // 크기 20, not null
    private String boardWriter;

    @Column // 크기 255, null 가능
    private String boardPass;

    @Column
    private String boardTitle;

    @Column(length = 500)
    private String boardContents;

    @Column
    private int boardHits;

    @Column
    private int fileAttached; // 1 or 0

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardFileEntity> boardFileEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    public static Board toSaveEntity(BoardDto boardDTO) {
    	Board board = new Board();
    	board.setBoardWriter(boardDTO.getBoardWriter());
    	board.setBoardPass(boardDTO.getBoardPass());
    	board.setBoardTitle(boardDTO.getBoardTitle());
    	board.setBoardContents(boardDTO.getBoardContents());
    	board.setBoardHits(0);
    	board.setFileAttached(0); // 파일 없음.
        return board;
    }

    public static Board toUpdateEntity(BoardDto boardDTO) {
    	Board board = new Board();
    	board.setId(boardDTO.getId());
    	board.setBoardWriter(boardDTO.getBoardWriter());
    	board.setBoardPass(boardDTO.getBoardPass());
    	board.setBoardTitle(boardDTO.getBoardTitle());
    	board.setBoardContents(boardDTO.getBoardContents());
    	board.setBoardHits(boardDTO.getBoardHits());
        return board;
    }

    public static Board toSaveFileEntity(BoardDto boardDTO) {
    	Board board = new Board();
    	board.setBoardWriter(boardDTO.getBoardWriter());
    	board.setBoardPass(boardDTO.getBoardPass());
    	board.setBoardTitle(boardDTO.getBoardTitle());
    	board.setBoardContents(boardDTO.getBoardContents());
    	board.setBoardHits(0);
    	board.setFileAttached(1); // 파일 있음.
        return board;
    }
}
