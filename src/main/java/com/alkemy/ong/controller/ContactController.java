package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.service.impl.ContactServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/contacts")
public class ContactController {

    @Autowired
    private ContactServiceImpl contactService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid ContactDto dto) throws Exception {
       return ResponseEntity.status(CREATED).body(contactService.create(dto));

    }

    @GetMapping
    public ResponseEntity<?> getContactList(){
        try{
            return ResponseEntity.status(OK).body(contactService.getContactList());
        }catch (Exception e){
            return ResponseEntity.status(NO_CONTENT).body(e.getMessage());
        }
    }
}
