"use strict";

// workitem scripts

IMIXS.namespace("org.imixs.workflow.monitor");

var overallData;		// dataset over all workflow groups
var groupData;  		// all datasets for each group

/**
 * Init Method for the workitem page
 * 
 *  - set history nav
 * 
 */
$(document).ready(function() {
	

});





// define core module
IMIXS.org.imixs.workflow.monitor = (function() {
	if (!IMIXS.org.imixs.core) {
		console.error("ERROR - missing dependency: imixs-core.js");
	}

	var imixs = IMIXS.org.imixs.core,



	
	// This method rebuilds a chart diagramm
	refreshChartDiagramm = function(reportid,data) {
		//console.log("building chart.js diagramm '" + reportid + "'...");
		// update h1 with label
		$("#" + reportid + " h1").text(data.title);
		
		// clear old data (http://stackoverflow.com/questions/24815851/how-to-clear-a-chart-from-a-canvas-so-that-hover-events-cannot-be-triggered#25064035)
		$('#canvas-'+reportid).remove(); // this is my <canvas> element
		$("#canvas-container-"+reportid).append("<canvas id=\"canvas-"+reportid+"\"></canvas>");
		
		var ctx = document.getElementById("canvas-" + reportid)
				.getContext("2d");
				
				
        var testOptions=  {
			    responsive: true,
			    plugins: {
			      legend: {
			        position: 'left',
			      },
			      title: {
			        display: true,
			        text: 'Chart.js Doughnut Chart'
			      }
			    }
			};			
	
		if (data.type) {
			alert('go: '+testOptions);
			// line chart
			 window.report1 = new Chart(ctx,  {
	                type: data.type,
	                data: data,
	                //options: data.options
                    options: testOptions
	        });
		} else {
			alert('chart type for report ' + data.datasets[0].label +' is undefined!');
		}
		
	};
	

	// public API
	return {
		
		refreshChartDiagramm : refreshChartDiagramm
	};

}());	
	
// Define public namespace
var imixsOfficeMonitor = IMIXS.org.imixs.workflow.monitor;	
			
	
	