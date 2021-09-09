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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.imixs.workflow.engine.DocumentService;
import org.imixs.workflow.engine.ModelService;
import org.imixs.workflow.engine.index.Category;
import org.imixs.workflow.engine.index.SearchService;
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
	
	@Inject
	protected SearchService searchService; 
	
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
	
	
	public String getStats() {
		
		String result="status: ";
		List<Category> categories = searchService.getTaxonomy("device","$workflowstatus");
		
		for (Category category: categories) {
			result=result+category.getName() + " = " + category.getCount()  + "\n";
			Map<String, Integer> labels = category.getLabels();
			for (Map.Entry<String, Integer> entry : labels.entrySet()) {
			    result=result+"       "+ entry.getKey()+ " = " + entry.getValue()  + "\n";
			}
		}
		
		return result;
	}
	
	public Map<String, Integer> getLabels(String name) {
		List<Category> categories = searchService.getTaxonomy(name);
		
		if (categories!=null && categories.size()>0) {
			return categories.get(0).getLabels();
		}
		
		return null;
		
		
	}
   
	
	
	   /**
     * 
     * <pre>
     *  {
            labels : [ 'Red', 'Blue', 'Yellow' ],
            datasets : [ {
                label : 'My First Dataset',
                data : [ 300, 50, 100 ],
                backgroundColor : [
                        'rgb(255, 99, 132)',
                        'rgb(54, 162, 235)',
                        'rgb(255, 205, 86)' ]
            } ]
        }
     * </pre>
     * 
     * @return
     */
    public String buildChartData(String group) {
    	
    	List<Category> categories = searchService.getTaxonomy(group);
		
		if (categories==null|| categories.size()==0) {
			return "";
		}
		
        Map<String, Integer> labels = categories.get(0).getLabels();
        // build a list of all lables....
        
        
        String result = "{";

        // Lables
        result = result + "labels : [ ";
        result = result + labels.keySet().stream().collect(Collectors.joining("','", "'", "'"));
        result = result + "],";

        // Datasets
        result = result + " datasets : [ { ";
        result = result + " label : '" + group + "', ";

        // build array of overall count
        List<String> statusCount = new ArrayList<String>();
        for (Map.Entry<String, Integer> entry : labels.entrySet()) {
        	statusCount.add(""+ entry.getValue() );
		}
        result = result + " data: [ " + statusCount.stream().collect(Collectors.joining(",")) + "],";

        // colors...
        result = result + " backgroundColor : [\n" + "                        'rgb(255, 99, 132)',\n"
                + "                        'rgb(54, 162, 235)',\n" + "                        'rgb(255, 205, 86)' ]";

        result = result + " } ] }";
        return result;
    }

}
