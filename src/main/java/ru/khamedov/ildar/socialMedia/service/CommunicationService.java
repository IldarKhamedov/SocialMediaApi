package ru.khamedov.ildar.socialMedia.service;

import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import ru.khamedov.ildar.socialMedia.model.UserProfile;
import ru.khamedov.ildar.socialMedia.model.communication.Message;
import ru.khamedov.ildar.socialMedia.model.communication.Proposal;
import ru.khamedov.ildar.socialMedia.model.communication.Sending;
import ru.khamedov.ildar.socialMedia.repository.ProposalRepository;
import ru.khamedov.ildar.socialMedia.repository.UserProfileRepository;
import ru.khamedov.ildar.socialMedia.util.Constant;

public class CommunicationService {

    @Resource
    private ProposalRepository proposalRepository;

    @Resource
    private AuthService authService;

    @Resource
    private UserProfileService userProfileService;

    @Resource
    private UserProfileRepository userProfileRepository;

    private static final boolean IS_APPLIED=true;

    private static final boolean IS_ACTIVE_BY_SENDER=false;

    public Proposal createProposal(String receiver){
        Proposal proposal=new Proposal();
        fillSenderReceiver(proposal,receiver);
        if(!proposalRepository.existsProposal(proposal.getSender(),proposal.getReceiver())){
            addToSubscribers(proposal.getSender(),proposal.getReceiver());
            proposalRepository.save(proposal);
        }
        return proposal;
    }

    private void addToSubscribers(UserProfile who, UserProfile whom){
        whom.getSubscribers().add(who);
        userProfileRepository.save(whom);
    }

    private void addingToFriends(Proposal proposal){
        if(proposal.isProcessed() && proposal.isApprovedByReceiver()) {
            proposal.getReceiver().getFriends().add(proposal.getSender());
            proposal.getSender().getFriends().add(proposal.getReceiver());
            addToSubscribers(proposal.getReceiver(),proposal.getSender());
            userProfileRepository.save(proposal.getReceiver());
            userProfileRepository.save(proposal.getSender());
        }
    }

    public boolean processProposal(Long id, boolean isAgreed){
        Proposal proposal=proposalRepository.findProposalById(id);
        proposal.setProcessed(IS_APPLIED);
        proposal.setApprovedByReceiver(isAgreed);
        addingToFriends(proposal);
        proposalRepository.save(proposal);
        return proposal.isProcessed() && proposal.isApprovedByReceiver();
    }

    public void deleteFromSubscribers(String userName){
        Proposal proposal=proposalRepository.findProposalByActiveAndNotProcessed(authService.getUserProfile(),authService.getUserByName(userName));
        proposal.setProcessed(IS_APPLIED);
        proposal.setActiveBySender(IS_ACTIVE_BY_SENDER);
        proposalRepository.save(proposal);
        proposal.getReceiver().getSubscribers().remove(proposal.getSender());
        userProfileRepository.save(proposal.getReceiver());
    }

    public void deleteFromFriends(String userName){
        UserProfile who=authService.getUserProfile();
        UserProfile whom=authService.getUserByName(userName);
        who.getFriends().remove(whom);
        whom.getFriends().remove(who);
        whom.getSubscribers().remove(who);
        userProfileRepository.save(whom);
        userProfileRepository.save(who);
    }

    private void fillSenderReceiver(Sending sending,String receiverName){
        UserProfile sender=authService.getUserProfile();
        UserProfile receiver=authService.getUserByName(receiverName);
        sending.setSender(sender);
        sending.setReceiver(receiver);
    }

    public Message createMessage(String receiver, String text){
        Message message=new Message();
        fillSenderReceiver(message,receiver);
        message.setText(text);
        if(!message.getSender().getFriends().contains(message.getReceiver())){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,receiver+Constant.ERROR_MESSAGE_NOT_FRIEND);
        }
        return message;
    }
}
