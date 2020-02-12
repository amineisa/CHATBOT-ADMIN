package com.chatbot.admin.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatbot.admin.entities.BotButton;
import com.chatbot.admin.services.BotButtonService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
@RestController()
@RequestMapping("/buttons")
@Api(value="Buttons Controller" , description="All Buttons operations ")
public class BotButtonController {

	@Autowired
	private BotButtonService botButtonService;
	
	@ApiOperation(value="Get all existing buttos" , response=ResponseEntity.class)
	@GetMapping(produces="application/json")
	public ResponseEntity<List<BotButton>> getAllButtons(){
		return botButtonService.getAllButtons(); 	
	}

	@ApiOperation(value="Get specific button by its id " , response=ResponseEntity.class)
	@GetMapping(value="/{id}" , produces="application/json")
	public ResponseEntity<BotButton> getButtonById(@ApiParam(value="button id " , required=true ) @Valid @PathVariable Long id){
		return botButtonService.getButtonById(id);
	}
	
	@ApiOperation(value="Get list of buttons by button type id ", response=ResponseEntity.class)
	@GetMapping(value="/types/{id}" , produces="application/json")
	public ResponseEntity<List<BotButton>> getAllButtonsByTypeId(@ApiParam(value="Button's type id ",required=true )@Valid @PathVariable Long id){
		return botButtonService.getButtonsByType(id); 	
	}
	
	@ApiOperation(value="Get list of buttons which belongs to specific element by element's id ",response=ResponseEntity.class)
	@GetMapping(value="/elements/{elementId}" , produces="application/json")
	public ResponseEntity<List<BotButton>> getButtonsByElement(@ApiParam(value="Element Id ", required=true) @Valid @PathVariable Long elementId){
		return botButtonService.getButtonsByElement(elementId);
	}
	
	@ApiOperation(value="Get list of buttons which belongs to specific quick reply template by its id " ,response=ResponseEntity.class)
	@GetMapping(value="/quickreplies/{quickId}",produces="application/json")
	public ResponseEntity<List<BotButton>> getButtonsByQuickReply(@ApiParam(value="Quick Reply id ",required=true) @Valid @PathVariable Long quickId){
		return botButtonService.getButtonsByQuickReply(quickId);
	}
	
	@ApiOperation(value="Get list of buttons which belongs to specific buttontemplate by its sid ",response=ResponseEntity.class)
	@GetMapping(value="/buttontemplets/{btnTemplateId}" , produces="application/json")
	public ResponseEntity<List<BotButton>> getButtonsByButtonTemplate(@ApiParam(value="Button template id ",required=true)@Valid @PathVariable Long btnTemplateId){
		return botButtonService.getButtonsByButtonTemplate(btnTemplateId);
	}
	
	@ApiOperation(value="Insert new Button ",response=ResponseEntity.class)
	@PostMapping(produces="application/json")
	public ResponseEntity<BotButton> saveButton(@ApiParam(value="Button Object" ,required=true )@Valid @RequestBody BotButton button){
		return botButtonService.saveButton(button);
		
	}

	@ApiOperation(value="update existing button ",response=ResponseEntity.class)
	@PutMapping(produces="application/json")
	public ResponseEntity<BotButton> updateButton(@ApiParam(value="Button object with new value to be updated ",required=true)@RequestBody BotButton button){
		return botButtonService.updateButton(button);
	}
	@ApiOperation(value="Delete an existing button ",response=ResponseEntity.class)
	@DeleteMapping(value= "/{id}" , produces="application/json")
	public  ResponseEntity<Void> deleteButton(@PathVariable Long id){
		return botButtonService.deleteButton(id);
	}
	
	
	/*@GetMapping("my/{companyId}/{userId}")
    public ResponseEntity<?> findByCompanyId(@PathVariable("companyId") String companyId, @PathVariable("userId") String userId) {
        List<NoteFrais> notes = noteFraisService.findUserNotesFrais(companyId, userId);
        if (notes.isEmpty()) {
            createNewNoteFrais(companyId, userId);
            notes = noteFraisService.findUserNotesFrais(companyId, userId);
        } else {
            List<LocalDate> dates = notes.stream().map(note -> note.getPeriode()).collect(Collectors.toList());
                if (!dates.get(0).getMonth().equals(LocalDate.now().getMonth())) {
                    System.out.println(dates);
                    createNewNoteFrais(companyId, userId);
                notes = noteFraisService.findUserNotesFrais(companyId, userId);
            }
        }
        return ResponseEntity.ok().body(notes);
    }*/
	
/*	
	@GetMapping("my/{companyId}/{userId}")
    public ResponseEntity<?> findByCompanyId(@PathVariable("companyId") String companyId, @PathVariable("userId") String userId) {
        List<NoteFrais> notes = noteFraisService.findUserNotesFrais(companyId, userId);
        if (notes == null || notes.isEmpty()) {
            createNewNoteFrais(companyId, userId);
            notes = noteFraisService.findUserNotesFrais(companyId, userId);
        } else{
            List<LocalDate> dates = notes.stream().map(note -> note.getPeriode()).collect(Collectors.toList());
                if (!dates.get(0).getMonth().equals(LocalDate.now().getMonth())) {
                    System.out.println(dates);
                    createNewNoteFrais(companyId, userId);
                notes = noteFraisService.findUserNotesFrais(companyId, userId);
            }
        }

        return ResponseEntity.ok().body(notes);
    }
	*/
}


