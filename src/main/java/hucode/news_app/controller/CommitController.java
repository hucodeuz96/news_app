package hucode.news_app.controller;

import hucode.news_app.dto.CommiitDTO;
import hucode.news_app.dto.CommiitResDTO;
import hucode.news_app.entity.Commiit;
import hucode.news_app.exception.ResourceNotFoundException;
import hucode.news_app.service.CommiitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

/**
 * @author "Husniddin Ulachov"
 * @created 8:51 AM on 7/26/2022
 * @project news_app
 */
@RestController
@RequestMapping("/commit")
@RequiredArgsConstructor
public class CommitController {
    private final CommiitService commiitService;

    @PreAuthorize(value = "hasAnyAuthority('USER','MODERATOR')")
    @PutMapping("/edit/{id}")
    public ResponseEntity<CommiitResDTO> edit(@PathVariable Long id, @RequestParam String text){
      return ResponseEntity.ok(commiitService.updateById(id,text));
    }
    @GetMapping("/getOne/{id}")
    public ResponseEntity<CommiitResDTO> getOne(@PathVariable Long id){
      return ResponseEntity.ok(commiitService.getOne(id));
    }
    @PreAuthorize(value = "hasAnyAuthority('USER')")
    @PostMapping("/create")
    public ResponseEntity<CommiitResDTO> create( @RequestBody CommiitDTO commit){
      return ResponseEntity.ok(commiitService.generateCommit(commit));
    }
    @PreAuthorize(value = "hasAuthority('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> edit(@PathVariable Long id){
        return ResponseEntity.ok(commiitService.deleteById(id));
    }
}
