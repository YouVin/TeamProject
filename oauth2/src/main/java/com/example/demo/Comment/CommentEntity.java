package com.example.demo.Comment;

import com.example.demo.Board.Board;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CommentEntity {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(length = 20, nullable = false)
	    private String commentWriter;

	    @Column
	    private String commentContents;

	    /* Board:Comment = 1:N */
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "board_id")
	    private Board board;


	    public static CommentEntity toSaveEntity(CommentDTO commentDTO, Board board) {
	        CommentEntity commentEntity = new CommentEntity();
	        commentEntity.setCommentWriter(commentDTO.getCommentWriter());
	        commentEntity.setCommentContents(commentDTO.getCommentContents());
	        commentEntity.setBoard(board);
	        return commentEntity;
	    }
}
