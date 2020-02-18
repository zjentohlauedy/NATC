package natc.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class GamesForm extends ActionForm {

	static final long serialVersionUID = 0;
	
	private Integer operation = null;

	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		
		this.operation = null;
	}

	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		
		return null;
	}

	public Integer getOperation() {
		return operation;
	}

	public void setOperation(Integer operation) {
		this.operation = operation;
	}

}
