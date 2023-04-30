package WebCock.Project_Web.controller;

import WebCock.Project_Web.entity.model.Board;
import WebCock.Project_Web.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Controller
@RequestMapping(path="/auth")
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 게시글 작성폼
    @GetMapping("/board/write") // localhost:8080/board/write
    public String boardWriteForm() {
        return "boardwrite";
    }

    // 데이터 처리
    @PostMapping("/board/newPost")
    public String boardWritePro(Board board, Model model, MultipartFile file) throws Exception {

        boardService.write(board, file);

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

    // 보드 리스트 보기
    @GetMapping("/board/list")
    public String boardList(Model model){  // model = 데이터를 담아서 우리가 보는 페이지로 옮겨주는 것ㄴ
        model.addAttribute("list", boardService.boardList());

        return "boardlist";
    }

    // 보드 상세 페이지
    @GetMapping("/board/view")      // localhost:8080/board/view?id=id값
    public String boardView(Model model, Long id){
        model.addAttribute("board", boardService.boardView(id));

        return "boardview";
    }

    // 상세 페이지 삭제
    @GetMapping("/board/delete")
    public String boaardDelete(Long id){
        boardService.boardDelete(id);
        return "redirect:/board/list";      // --> 삭제하면 리스트 페이지로 다시 가야하기 때문에 redirect:/board/list로 함
    }

    // 상세 페이지 수정
    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Long id, Model model){
        model.addAttribute("board",boardService.boardView(id));
        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Long id, Board board, MultipartFile file) throws Exception{
        Board boardTamp = boardService.boardView(id);   // 기존의 데이터를 가지고온다
        boardTamp.setTitle(board.getTitle());           // 새로운 title를 boardTamp에 저장
        boardTamp.setContent(board.getContent());       // 새로운 content를 boardTamp에 저장

        boardService.write(boardTamp, file);

        return "redirect:/board/list";
    }
}
