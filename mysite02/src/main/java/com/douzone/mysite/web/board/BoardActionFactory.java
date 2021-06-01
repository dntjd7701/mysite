package com.douzone.mysite.web.board;

import com.douzone.web.Action;
import com.douzone.web.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;

		if ("writeform".equals(actionName)) {
			action = new WriteFormAction();
		} else if ("view".equals(actionName)) {
			action = new ViewAction();
		} else if ("write".equals(actionName)) {
			action = new WriteAction();
		} else if ("modifyform".equals(actionName)) {
			action = new ModifyFormAction();
		} else if ("modify".equals(actionName)) {
			action = new ModifyAction();
		} else if ("delete".equals(actionName)) {
			action = new DeleteAction();
		} else if ("replyform".equals(actionName)) {
			action = new ReplyFormAction();
		} else if ("reply".equals(actionName)) {
			action = new ReplyAction();
		} else { // default action
			action = new ListAction();
		}

		return action;

	}
}
