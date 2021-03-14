package com.roughsea.demo.service.impl;

import com.roughsea.demo.entity.Note;
import com.roughsea.demo.entity.NoteDTO;
import com.roughsea.demo.service.NoteService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NoteServiceImpl implements NoteService {

    private static final Map<Long, Note> notes = new HashMap<>();

    private static Long counter = 1L;

    @Value("${app.n.count}")
    private int N;

    @Override
    public Note add(NoteDTO dto){

        Note note = Note.builder()
                .id(counter)
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();

        notes.put(counter++, note);
        return note;
    }

    @Override
    public Note getById(Long id){
        return checkNote(notes.get(id));
    }

    @Override
    public Collection<Note> getAll(){
        return notes.values();
    }

    @Override
    public void update(Long id, NoteDTO dto){
        Note note = notes.get(id);
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
    }

    @Override
    public void delete(Long id){
        notes.remove(id);
    }

    @Override
    public Collection<Note> search(String query) {
        List<Note> result = new ArrayList<>();
        for (Map.Entry<Long, Note> entry : notes.entrySet()) {
            Note note = entry.getValue();
            if ((Objects.nonNull(note.getTitle()) && note.getTitle().contains(query)) ||
                    ((Objects.nonNull(note.getContent()) && note.getContent().contains(query)))){
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
