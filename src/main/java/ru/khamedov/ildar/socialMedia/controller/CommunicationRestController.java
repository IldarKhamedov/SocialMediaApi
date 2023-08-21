package ru.khamedov.ildar.socialMedia.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="Взаимодействие пользователей")
@SecurityRequirement(name = "JWT")
@SecurityRequirement(name = "BASIC")
public class CommunicationRestController {

    @Resource
    private CommunicationService communicationService;

    @Operation(summary = "Отправка заявки в друзья")
    @PostMapping("/send/proposal/{userName}")
    public ResponseEntity sendProposalToUser(@PathVariable @Parameter(description = "Имя пользователя, которого хотим добавтть в друзья",required = true)String userName){
        Proposal proposal=communicationService.createProposal(userName);
        return new ResponseEntity(proposal.getId(), HttpStatus.OK);
    }

    @Operation(summary = "Обработка заявки в друзья")
    @PutMapping("/process/proposal/{id}/{isAgreed}")
    public ResponseEntity getProposals(@PathVariable @Parameter(description = "Id подданой заявки",required = true) Long id,
                                       @PathVariable @Parameter(description = "Принять/не принять заявку",required = true) boolean isAgreed){
        return new ResponseEntity(communicationService.processProposal(id,isAgreed),HttpStatus.OK);
    }

    @Operation(summary = "Удалиться из подписчиков")
    @PutMapping("/refusal/proposal/{userName}")
    public ResponseEntity refusalFromProposalByUser(@PathVariable @Parameter(description = "Имя пользователя, у которого нужно удалиться из подписчиков",required = true) String userName){
        return new ResponseEntity<>(communicationService.deleteFromSubscribers(userName),HttpStatus.OK);
    }

    @Operation(summary = "Удалить из друзей пользователя")
    @PutMapping("/delete/friends/{userName}")
    public ResponseEntity deleteFromFriendsByUser(@PathVariable @Parameter(description = "Имя пользователя для удаления",required = true) String userName){
        return new ResponseEntity<>(communicationService.deleteFromFriends(userName),HttpStatus.OK);
    }

    @Operation(summary = "Отправка сообщению другу")
    @PostMapping("/send/message/{userName}")
    public ResponseEntity sendMessage(@PathVariable @Parameter(description = "Имя кому отправить сообщение",required = true) String userName,
                                      @RequestBody MessageDTO messageDTO){
    Message message=communicationService.createMessage(userName,messageDTO.getText());
        return new ResponseEntity<>(message.getId(),HttpStatus.OK);
    }

    @Operation(summary = "Просмотр всей переписки с другом")
    @GetMapping("/messages/{userName}")
    public ResponseEntity getMessagesByUser(@PathVariable @Parameter(description = "Имя с кем получить переписку",required = true) String userName){
        return new ResponseEntity<>(communicationService.getMessagesByUser(userName),HttpStatus.OK);
    }
}
