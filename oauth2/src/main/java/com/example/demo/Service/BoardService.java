package com.example.demo.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Board.BoardRepository;


import lombok.RequiredArgsConstructor;

import com.example.demo.Board.Board;
import com.example.demo.Board.BoardDto;
import com.example.demo.Board.BoardFileEntity;
import com.example.demo.Board.BoardFileRepository;
@Service
@RequiredArgsConstructor
public class BoardService {
	 private final BoardRepository boardRepository;
	    private final BoardFileRepository boardFileRepository;
	    public void save(BoardDto boardDTO) throws IOException {
	        // 파일 첨부 여부에 따라 로직 분리
	        if (boardDTO.getBoardFile().isEmpty()) {
	            // 첨부 파일 없음.
	            Board board = Board.toSaveEntity(boardDTO);
	            boardRepository.save(board);
	        } else {
	            // 첨부 파일 있음.
	            /*
	                1. DTO에 담긴 파일을 꺼냄
	                2. 파일의 이름 가져옴
	                3. 서버 저장용 이름을 만듦
	                // 내사진.jpg => 839798375892_내사진.jpg
	                4. 저장 경로 설정
	                5. 해당 경로에 파일 저장
	                6. board_table에 해당 데이터 save 처리
	                7. board_file_table에 해당 데이터 save 처리
	             */
	            MultipartFile boardFile = boardDTO.getBoardFile(); // 1.
	            String originalFilename = boardFile.getOriginalFilename(); // 2.
	            String storedFileName = System.currentTimeMillis() + "_" + originalFilename; // 3.
	            String savePath = "C:/springboot_img/" + storedFileName; // 4. C:/springboot_img/9802398403948_내사진.jpg
//	            String savePath = "/Users/사용자이름/springboot_img/" + storedFileName; // C:/springboot_img/9802398403948_내사진.jpg
	            boardFile.transferTo(new File(savePath)); // 5.
	            Board board = Board.toSaveFileEntity(boardDTO);
	            Long savedId = boardRepository.save(board).getId();
	            Board boards = boardRepository.findById(savedId).get();

	            BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(boards, originalFilename, storedFileName);
	            boardFileRepository.save(boardFileEntity);
	        }

	    }

	    @Transactional
	    public List<BoardDto> findAll() {
	        List<Board> boardList = boardRepository.findAll();
	        List<BoardDto> boardDTOList = new ArrayList<>();
	        for (Board board: boardList) {
	            boardDTOList.add(BoardDto.toBoardDTO(board));
	        }
	        return boardDTOList;
	    }

	    @Transactional
	    public void updateHits(Long id) {
	        boardRepository.updateHits(id);
	    }

	    @Transactional
	    public BoardDto findById(Long id) {
	        Optional<Board> optionalBoard = boardRepository.findById(id);
	        if (optionalBoard.isPresent()) {
	            Board board = optionalBoard.get();
	            BoardDto boardDTO = BoardDto.toBoardDTO(board);
	            return boardDTO;
	        } else {
	            return null;
	        }
	    }

	    public BoardDto update(BoardDto boardDTO) {
	        Board board = Board.toUpdateEntity(boardDTO);
	        boardRepository.save(board);
	        return findById(boardDTO.getId());
	    }

	    public void delete(Long id) {
	        boardRepository.deleteById(id);
	    }

	    public Page<BoardDto> paging(Pageable pageable) {
	        int page = pageable.getPageNumber() - 1;
	        int pageLimit = 3; // 한 페이지에 보여줄 글 갯수
	        // 한페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
	        // page 위치에 있는 값은 0부터 시작
	        Page<Board> boardEntities =
	                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

	        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청 페이지에 해당하는 글
	        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
	        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // DB로 요청한 페이지 번호
	        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
	        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한 페이지에 보여지는 글 갯수
	        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전 페이지 존재 여부
	        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫 페이지 여부
	        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지 여부

	        // 목록: id, writer, title, hits, createdTime
	        Page<BoardDto> boardDTOS = boardEntities.map(boards -> new BoardDto(boards.getId(), boards.getBoardWriter(), boards.getBoardTitle(), boards.getBoardHits(), boards.getCreatedTime()));
	        return boardDTOS;
	    }
}
