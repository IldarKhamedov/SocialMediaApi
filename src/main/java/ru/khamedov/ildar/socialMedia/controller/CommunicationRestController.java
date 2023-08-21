package ru.khamedov.ildar.socialMedia.controller;

import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.khamedov.ildar.socialMedia.dto.MessageDTO;
import ru.khamedov.ildar.socialMedia.model.communication.Message;
import ru.khamedov.ildar.socialMedia.model.communication.Proposal;
import ru.khamedov.ildar.socialMedia.service.CommunicationService;

@RestController
@RequestMapping("/api/social/communication")
public class CommunicationRestController {

    @Resource
    private CommunicationService communicationService;

    @PostMapping("/send/proposal/{userName}")
    public ResponseEntity sendProposalToUser(@PathVariable(required = true)String userName){
        Proposal proposal=communicationService.createProposal(userName);
        return new ResponseEntity(proposal.getId(), HttpStatus.OK);
    }

    @PutMapping("/process/proposal/{id}/{isAgreed}")
    public ResponseEntity getProposals(@PathVariable(required = true)Long id,@PathVariable(required = true)boolean isAgreed){
        return new ResponseEntity(communicationService.processProposal(id,isAgreed),HttpStatus.OK);
    }

    @PutMapping("/refusal/proposal/{userName}")
    public ResponseEntity refusalFromProposalByUser(@PathVariable(required = true)String userName){
        return new ResponseEntity<>(communicationService.deleteFromSubscribers(userName),HttpStatus.OK);
    }

    @PutMapping("/delete/friends/{userName}")
    public ResponseEntity deleteFromFriendsByUser(@PathVariable(required = true)String userName){
        return new ResponseEntity<>(communicationService.deleteFromFriends(userName),HttpStatus.OK);
    }

    @PostMapping("/send/message/{userName}")
    public ResponseEntity sendMessage(@PathVariable(required = true)String userName, @RequestBody MessageDTO messageDTO){
    Message message=communicationService.createMessage(userName,messageDTO.getText());
        return new ResponseEntity<>(message.getId(),HttpStatus.OK);
    }

    @GetMapping("/messages/{userName}")
    public ResponseEntity getMessagesByUser(@PathVariable(required = true)String userName){
        return new ResponseEntity<>(communicationService.getMessagesByUser(userName),HttpStatus.OK);
    }
}
