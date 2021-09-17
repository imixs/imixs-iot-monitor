"use strict";

// workitem scripts

IMIXS.namespace("org.imixs.marty");

var autocompleteInputID;
var autcompleteSelectedElement;
var autocompleteSearchReady=false;
var currentSelectCallback;





// define core module
IMIXS.org.imixs.marty = (function() {
	

	// init user input fields...
	$(document).ready(function() {

		// add autocomplete feature to al user inputs...
		var creditorField= $("input[data-item='marty.user.input']");
		$(creditorField).each(function() {
			
			imixsMarty.userInputInit(this,martyUserSearch,'marty-userinput-resultlist'); // optional callback method allowed here!
		});
		
	
	});





	
	/*
	 * initializes an input element for autocompletion. 
	 * the param 'resultlistid' is optional and defines the element 
	 * containing the search result.
	 * The selectCallback method is optional and triggered when a new element was selected 
	 * from the suggest list
	 */
	var userInputInit = function(inputElement, searchCallback, resultlistId, selectCallback) {
		
		// should be hindden
		$(inputElement).hide();
		
		// set id for result list element
		if (!resultlistId || resultlistId==='') {
			resultlistId='autocomplete-resultlist'; // default name
		} 
		
		
		
		// add the user search  dummy field ...
		
		$(inputElement).after( '<input type="text" class="marty-userinput"  id="marty-userinput-'+inputElement.name+'" />' );
		// now we can select the new element with the next method
		var searchInputElement=$(inputElement).next();
		
		$(searchInputElement).attr('data-resultlist', resultlistId);
		
		
		// add a input event handler with delay to serach for suggestions....
		$(searchInputElement).on('input', delay(function() {
			if (!autocompleteSearchReady) {
				return; // start only after first key down! (see below)
			}
			// store the current input name
			autocompleteInputID = $(this).attr('id');
			
			//alert(autocompleteInputID);
			currentSelectCallback=selectCallback;
			searchCallback({ phrase: this.value });
		}, 500)).trigger('input');
	
	
		// hide the suggest list on blur event
		$(searchInputElement).on("blur", delay(function(event) {
			$("[id$=" + $(this).data('resultlist')  + "]").hide();
		}, 200));
	
	
		/*execute a function presses a key on the keyboard:*/
		$(searchInputElement).keydown(function(e) {
			
			autocompleteSearchReady=true; // init serach mode
			if (e.keyCode == 40) {
		        /*If the arrow DOWN key is pressed,
		        increase the currentFocus variable:*/
				autocompleteSelectNextElement(this);
			} else if (e.keyCode == 38) { //up
		        /*If the arrow UP key is pressed,
		        decrease the currentFocus variable:*/
				autocompleteSelectPrevElement(this);
			} else if (e.keyCode == 13) {
				/*If the ENTER key is pressed, prevent the form from being submitted,*/
				e.preventDefault();
				selectActiveElement(this);			
			}
		});
	
		// turn autocomplete of
		$(searchInputElement).attr('autocomplete', 'off');
	},


	/**
	 * This mehtod shows the search result panel and places it below the current input element
	 */
	userSearchShowResult = function(data) {
		autcompleteSelectedElement = -1;
		var status = data.status;
		if (status === "success") {
			// select the inital input element by its name...
			var inputElement = $('input[id ="' + autocompleteInputID + '"]');
			
			
			// now we pull the result html list to this input field.....
			$("[id$=" +  inputElement.data('resultlist')  + "]").insertAfter(inputElement).show();
		}
	},

	/* Helper method to select the current element in the result list */
	selectActiveElement = function(inputElement) {
		var id=$(inputElement).data('resultlist');
		var parent=$( "div[id$='" + id +"']" );
		var resultElementListActive = $(".marty-userinput-resultlist-element.active",parent);
		$("a",resultElementListActive).click();
		$(parent).hide();
	},
	
	
	/* Helper method to highligt the current element in the result list */
	autocompleteSelectNextElement = function(inputElement) {
		var id=$(inputElement).data('resultlist');
		var parent=$( "div[id$='" + id +"']" );
		var resultElementList = $(".marty-userinput-resultlist-element",parent);
		// remove acitve if set	
		$(resultElementList).removeClass("active");
		// next element...?
		autcompleteSelectedElement++;
		if (autcompleteSelectedElement >= resultElementList.length) {
			// reset if overflow...
			autcompleteSelectedElement = 0;
		} 
		// set new active
		$(resultElementList[autcompleteSelectedElement]).addClass("active");
	},
	
	
	autocompleteSelectPrevElement = function(inputElement) {
		var id=$(inputElement).data('resultlist');
		var parent=$( "div[id$='" + id +"']" );
		var resultElementList = $(".marty-userinput-resultlist-element",parent);
		// remove acitve if set	
		$(resultElementList).removeClass("active");
		autcompleteSelectedElement--;
		// next element...?
		if (autcompleteSelectedElement < 0) {
			autcompleteSelectedElement = resultElementList.length - 1;
		}
		// set new active
		$(resultElementList[autcompleteSelectedElement]).addClass("active");
	},





	selectUserID = function(userid,username) {
		// show the username in the serach field..
		var inputSearchField = $('input[id ="' + autocompleteInputID + '"]');
		inputSearchField.val(username);
		
		// find the value field to store the userid (type=hidden)
		var inputField = $(inputSearchField ).prev();
		inputField.val(userid);
		
		
		// optional callback method if defined...
		if (currentSelectCallback) {
			currentSelectCallback(userid,username);
		}
	},



	/*
	 * delay function
	 * see: https://stackoverflow.com/questions/1909441/how-to-delay-the-keyup-handler-until-the-user-stops-typing
	 */
	delay = function(callback, ms) {
		var timer = 0;
		return function() {
			var context = this, args = arguments;
			clearTimeout(timer);
			timer = setTimeout(function() {
				callback.apply(context, args);
			}, ms || 0);
		};
	};
	

	// public API
	return {
		
		userInputInit : userInputInit,
		userSearchShowResult : userSearchShowResult,
		selectUserID : selectUserID
	};

}());	
	
// Define public namespace
var imixsMarty = IMIXS.org.imixs.marty;	
			
	
	