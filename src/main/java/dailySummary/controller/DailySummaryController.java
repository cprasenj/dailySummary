package dailySummary.controller;

import dailySummary.contract.*;
import dailySummary.contract.DailySummary;
import dailySummary.dto.DailySummaryContractToModelDTO;
import dailySummary.error.NotAMemberError;
import dailySummary.model.*;
import dailySummary.service.DailySummaryService;
import dailySummary.service.MailService;
import dailySummary.validator.AdminUserValidator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.FileReader;;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
public class DailySummaryController {

    @Autowired
    private DailySummaryService dailySummaryService;

    @Autowired
    private MailService mailService;

    @Autowired
    private DailySummaryContractToModelDTO dailySummaryContractToModelDTO;

    @Autowired
    private AdminUserValidator validator;

    @RequestMapping(value = "/dailySummary/save", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseEntity getCountryPage(@RequestBody DailySummary summary) {
        dailySummaryService.persistSummary(dailySummaryContractToModelDTO.toDailySummaryModel(summary));
        return new ResponseEntity(summary, HttpStatus.OK);
    }

    @RequestMapping(value = "/addMember", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseEntity addMember(@RequestBody AddMemberRequest addMemberRequest) {
        dailySummaryService.addMember(dailySummaryContractToModelDTO.toMemberModel(addMemberRequest));
        return new ResponseEntity(addMemberRequest, HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteMember", method = RequestMethod.POST)
    public ResponseEntity deleteMember(@RequestBody DeleteMemberRequest deleteMemberRequest) {
        dailySummaryService.deleteMember(deleteMemberRequest);
        return new ResponseEntity(deleteMemberRequest, HttpStatus.OK);
    }

    @RequestMapping(value = "/createTeam", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseEntity createTeam(@RequestBody AddTeamRequest addTeamRequest) {
        dailySummaryService.createTeam(dailySummaryContractToModelDTO.toTeamModel(addTeamRequest));
        return new ResponseEntity(addTeamRequest, HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteTeam", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseEntity deleteTeam(@RequestBody DeleteTeamRequest deleteTeamRequest) {
        dailySummaryService.deleteTeam(deleteTeamRequest);
        return new ResponseEntity(deleteTeamRequest, HttpStatus.OK);
    }

    @RequestMapping("/team/getAll")
    @CrossOrigin
    public ResponseEntity getAllTeams() {
        return new ResponseEntity(TeamResponse.builder().teams(dailySummaryService.getAllTeams()).build(), HttpStatus.OK);
    }

    @RequestMapping(value = "/preview", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseEntity getMailPreviewForATeam(@RequestBody RequestById requestById) {
        return new ResponseEntity(mailService.preview(requestById.getId()), HttpStatus.OK);
    }


    @RequestMapping(value = "/team", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseEntity getTeamForAUser(@RequestBody RequestById requestById) throws NotAMemberError {
        Member member = dailySummaryService.getTeamForAUser(requestById.getId());
        return new ResponseEntity(member, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllMembers", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseEntity getAllMembersForATeam(@RequestBody RequestByEmail requestByEmail) throws NotAMemberError {
        List<Member> members = dailySummaryService.getAllUsersForATeam(requestByEmail.getEmailId());
        return new ResponseEntity(members, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllJob", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseEntity getAllJobForATeam(@RequestBody RequestById requestById) throws NotAMemberError {
        List<dailySummary.model.DailySummary> jobs = mailService.getAllJobForATeam(requestById.getId());
        return new ResponseEntity(jobs, HttpStatus.OK);
    }

    @RequestMapping(value = "/upDateJob", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseEntity upDateAllJobForATeam(@RequestBody JobUpdateRequest jobUpdateRequest) throws NotAMemberError {
        dailySummaryService.updateAllJobForATeam(jobUpdateRequest.getDailySummaryList());
        return new ResponseEntity(jobUpdateRequest, HttpStatus.OK);
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    @CrossOrigin
    public ResponseEntity getOrders() {

        JSONParser jsonParser = new JSONParser();
        Object obj = "";
        try (FileReader reader = new FileReader("output.json"))
        {
            //Read JSON file
            obj = jsonParser.parse(reader);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

        return new ResponseEntity(obj, HttpStatus.OK);
    }

}
