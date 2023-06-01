package com.example.demo.Service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

import com.example.demo.Board.Board;
import com.example.demo.Board.BoardRepository;
import com.example.demo.Comment.CommentDTO;
import com.example.demo.Comment.CommentEntity;
import com.example.demo.Comment.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public Long save(CommentDTO commentDTO) {
        /* 부모엔티티(Board) 조회 */
        Optional<Board> optionalBoard = boardRepository.findById(commentDTO.getBoardId());
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, board);
            return commentRepository.save(commentEntity).getId();
        } else {
            return null;
        }
    }

    public List<CommentDTO> findAll(Long boardId) {
        Board board = boardRepository.findById(boardId).get();
        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardOrderByIdDesc(board);
        /* EntityList -> DTOList */
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity commentEntity: commentEntityList) {
            CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity, boardId);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }

}
