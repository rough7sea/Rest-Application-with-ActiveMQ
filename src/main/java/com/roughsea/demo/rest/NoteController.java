package com.roughsea.demo.rest;

import com.roughsea.demo.entity.Note;
import com.roughsea.demo.dto.NoteDto;
import com.roughsea.demo.service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping("/notes")
@AllArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<Note> addNote(@RequestBody NoteDto dto){
        return ResponseEntity.ok(noteService.add(dto));
    }

    @GetMapping
    public ResponseEntity<Collection<Note>> getAllNotes(
            @RequestParam(required = false) String query){
        if (Objects.nonNull(query)){
            return ResponseEntity.ok(noteService.search(query));
        }
        return ResponseEntity.ok(noteService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id){
        Note note = noteService.getById(id);
        return Objects.nonNull(note) ?
                ResponseEntity.ok(note) :
                ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateNote(@PathVariable Long id, @RequestBody NoteDto dto){
        noteService.update(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> updateNote(@PathVariable Long id){
        noteService.delete(id);
        return ResponseEntity.ok().build();
    }

}