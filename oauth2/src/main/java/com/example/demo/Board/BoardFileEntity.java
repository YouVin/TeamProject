package com.example.demo.Board;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "board_file_table")
public class BoardFileEntity extends BaseEntity {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column
	    private String originalFileName;

	    @Column
	    private String storedFileName;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "board_id")
	    private Board board;

	    public static BoardFileEntity toBoardFileEntity(Board board, String originalFileName, String storedFileName) {
	        BoardFileEntity boardFileEntity = new BoardFileEntity();
	        boardFileEntity.setOriginalFileName(originalFileName);
	        boardFileEntity.setStoredFileName(storedFileName);
	        boardFileEntity.setBoard(board);
	        return boardFileEntity;
	    }
}
