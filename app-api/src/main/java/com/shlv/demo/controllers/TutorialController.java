package com.shlv.demo.controllers;

import com.shlv.demo.exception.ResourceNotFoundException;
import com.shlv.demo.models.Tutorial;
import com.shlv.demo.repositories.TutorialRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8082")
@RestController
@RequestMapping("/api")
public class TutorialController {

    private TutorialRepository tutorialRepository;

    public TutorialController(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
        List<Tutorial> tutorials = new ArrayList<Tutorial>();

        if (title == null || title == "") {
            tutorialRepository.findAll().forEach(tutorials::add);
        } else {
            try {
                tutorialRepository.findByTitleContaining(title.toString());
                //.forEach(tutorials::add)
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (tutorials.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {
        Tutorial tutorial = tutorialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));

        return new ResponseEntity<>(tutorial, HttpStatus.OK);
    }

    @PostMapping("/tutorials")
    public ResponseEntity<Tutorial> createTutorial(@Valid @RequestBody Tutorial tutorial) {
        Tutorial response = tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), tutorial.getPublished()));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorialInput) {
        Tutorial tutorial = tutorialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));
        tutorial.setTitle(tutorialInput.getTitle());
        tutorial.setDescription(tutorialInput.getDescription());
        tutorial.setPublished(tutorialInput.getPublished());

        return new ResponseEntity<>(tutorialRepository.save(tutorial), HttpStatus.OK);
    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
        tutorialRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/tutorials")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        tutorialRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/tutorials/published")
    public ResponseEntity<List<Tutorial>> findByPublished() {
        List<Tutorial> tutorials = tutorialRepository.findByPublished(true);

        if (tutorials.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }
}
