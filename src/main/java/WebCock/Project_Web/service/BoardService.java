package WebCock.Project_Web.service;

import WebCock.Project_Web.entity.model.Board;
import WebCock.Project_Web.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;
    
    // 글 작성 (DB에 내용 삽입)
    public void write(Board board, MultipartFile file) throws Exception{

        // file올리기
        String projectPath = System.getProperty("user.dir")+ "\\src\\main\\resources\\static\\files";   // 경로지정

        UUID uuid = UUID.randomUUID();      // 랜덤으로 이름을 만들어 준다(식별자)
        String fileName = uuid + "_" + file.getOriginalFilename();  // 식별자 _ 파일이름 => 저장된 파일이름

        File saveFile = new File(projectPath, fileName);   //빈 껍데기 만들기 / 파일의 이름 정하기

        file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilepath("/files/"+ fileName);

        boardRepository.save(board);      // save는 entity를 넣어준다
    }

    // 글 리스트 처리 (불러오기)
    public List<Board> boardList() {
        return boardRepository.findAll();
    }
    
    // 특정 게시글 불러오기
    public Board boardView(Long id) {
        return boardRepository.findById(id).get();
    }

    // 특정 게시글 삭제
    public void boardDelete(Long id) { // void는 return type이 없다.
        boardRepository.deleteById(id);
    }
}
