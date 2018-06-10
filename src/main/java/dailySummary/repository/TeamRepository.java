package dailySummary.repository;


import dailySummary.model.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamRepository extends MongoRepository<Team, String> {
    void deleteTeamByTeamEmail(String teamEmail);
}
