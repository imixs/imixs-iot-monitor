package org.imixs.application.ui.view;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.imixs.workflow.faces.data.ViewController;
import org.imixs.workflow.faces.util.LoginController;


@Named
@ViewScoped
public class SpaceViewController extends ViewController {

	private static final long serialVersionUID = 1L;

	@Inject
	LoginController loginController;
	
	@Override
	@PostConstruct
	public void init() {
		super.init();
		this.setQuery("(type:\"space\")");
		this.setSortBy("name");
		this.setSortReverse(false);
		this.setPageSize(999);
		this.setLoadStubs(false);
	}


}
