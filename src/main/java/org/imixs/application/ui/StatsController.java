/*******************************************************************************
 *  Imixs Workflow Technology
 *  Copyright (C) 2003, 2008 Imixs Software Solutions GmbH,  
 *  http://www.imixs.com
 *  
 *  This program is free software; you can redistribute it and/or 
 *  modify it under the terms of the GNU General Public License 
 *  as published by the Free Software Foundation; either version 2 
 *  of the License, or (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful, 
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of 
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU 
 *  General Public License for more details.
 *  
 *  You can receive a copy of the GNU General Public
 *  License at http://www.gnu.org/licenses/gpl.html
 *  
 *  Contributors:  
 *  	Imixs Software Solutions GmbH - initial API and implementation
 *  	Ralph Soika
 *  
 *******************************************************************************/
package org.imixs.application.ui;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.imixs.workflow.engine.DocumentService;
import org.imixs.workflow.engine.ModelService;
import org.imixs.workflow.exceptions.AccessDeniedException;
import org.imixs.workflow.exceptions.ModelException;
import org.imixs.workflow.exceptions.QueryException;
import org.imixs.workflow.faces.data.WorkflowEvent;

/**
 * The StatsController collects statistics data for 
 * the active process instances.
 * 
 * @see workitem_chronicle.xhtml
 * @author rsoika,gheinle
 */
@Named
@ConversationScoped
public class StatsController implements Serializable {

	int eventCount=0;
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	protected DocumentService documentService;
	
	@Inject
	protected ModelService modelService;
	
	private static Logger logger = Logger.getLogger(StatsController.class.getName());

	
	
	@PostConstruct
	public void init() {
		
		try {
			eventCount=documentService.count("type:workitem");
		} catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	/**
     * WorkflowEvent listener to update the current FormDefinition.
     * 
     * @param workflowEvent
     * @throws AccessDeniedException
     * @throws ModelException
     */
    public void onWorkflowEvent(@Observes WorkflowEvent workflowEvent) {
        if (workflowEvent == null)
            return;


        int eventType = workflowEvent.getEventType();
        if (WorkflowEvent.WORKITEM_CHANGED == eventType || WorkflowEvent.WORKITEM_CREATED == eventType
                || WorkflowEvent.WORKITEM_AFTER_PROCESS == eventType) {
            //reset 
            init();
        }

    }


	public int getEventCount() {
		return eventCount;
	}


	public void setEventCount(int eventCount) {
		this.eventCount = eventCount;
	}

	public int getModelCount() {
		return modelService.getVersions().size();
	}
   
}
