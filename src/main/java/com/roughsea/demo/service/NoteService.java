package com.roughsea.demo.service;

import com.roughsea.demo.entity.Note;
import com.roughsea.demo.dto.NoteDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NoteService {

    private static final Map<Long, Note> notes = new HashMap<>();

    private static Long counter = 1L;

    @Value("${app.n}")
    private int N;

    public Note add(NoteDto dto){
        return notes.put(counter++,
                Note.builder()
                        .id(counter)
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .build());
    }

    public Note getById(Long id){
        return checkNote(notes.get(id));
    }

    public Collection<Note> getAll(){
        return notes.values();
    }

    public void update(Long id, NoteDto dto){
        Note note = notes.get(id);
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
    }

    public void delete(Long id){
        notes.remove(id);
    }

    public Collection<Note> search(String query) {
        List<Note> result = new ArrayList<>();
        for (Map.Entry<Long, Note> entry : notes.entrySet()) {
            Note note = entry.getValue();
            if ((Objects.nonNull(note.getTitle()) && note.getTitle().contains(query)) ||
                    (Objects.nonNull(note.getContent()) && note.getContent().contains(query))){
                result.add(checkNote(note));
            }
        }
        return result;
    }

    private Note checkNote(Note note){
        if (Objects.isNull(note.getTitle())){
            return Note.builder()
                    .id(note.getId())
                    .title(note.getContent().substring(0, N))
                    .content(note.getContent())
                    .build();
        }
        return note;
    }

}
