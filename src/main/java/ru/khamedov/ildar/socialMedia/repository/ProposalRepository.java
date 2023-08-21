package ru.khamedov.ildar.socialMedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.khamedov.ildar.socialMedia.model.UserProfile;
import ru.khamedov.ildar.socialMedia.model.communication.Proposal;

public interface ProposalRepository extends JpaRepository<Proposal,Long> {

    @Query("SELECT COUNT(p)>0 FROM Proposal p WHERE p.sender=:sender AND p.receiver=:receiver AND p.activeBySender=TRUE AND p.processed=FALSE")
    boolean existsProposal(@Param("sender")UserProfile sender,@Param("receiver")UserProfile receiver);

    @Query("SELECT p FROM Proposal p WHERE p.id=:id AND p.activeBySender=TRUE AND p.processed=FALSE")
    Proposal findProposalById(@Param("id")Long id);

    @Query("SELECT p FROM Proposal p WHERE p.sender=:sender AND p.receiver=:receiver AND p.activeBySender=TRUE AND p.processed=FALSE")
    Proposal findProposalByActiveAndNotProcessed(@Param("sender")UserProfile sender, @Param("receiver")UserProfile receiver);
}
