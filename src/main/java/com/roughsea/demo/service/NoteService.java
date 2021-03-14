package com.roughsea.demo.service;

import com.roughsea.demo.entity.Note;
import com.roughsea.demo.entity.NoteDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public interface NoteService {

    Note add(NoteDTO dto);

    Note getById(Long id);

    Collection<Note> getAll();

    void update(Long id, NoteDTO dto);

    void delete(Long id);

    Collection<Note> search(String query);
}
