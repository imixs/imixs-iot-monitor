# Marty User Input Feature

The Imixs-Marty provides an autocomplete feature for user input elements. This feature is based on a JavaScript library as on a JSF view.

## How to Integrate

To integrate and activate the feature you need at least to insert the marty-usersearch subview


	<ui:include src="marty-user-search.xhtml" />
											

## Activate an Input Field

To activate a JSF Input field with the Marty User Input feature you just need to add the data tag "marty.user.input". See the following example:

	<h:inputText value="#{value}"  pt:data-item="marty.user.input"/>	

## Optional Callback Method

You can define an optional callback method to be triggered when the user selects an element form the user search result list.

	