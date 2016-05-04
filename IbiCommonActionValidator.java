/**
 * Copyright (C) 2012 KPMG Business Advisory Singapore . All Rights Reserved. 
 * 
 * This software is the proprietary information of KPMG Business Advisory Singapore. 
 * Use is subjected to license terms. 
 *
 * @since Mar 29, 2012 4:38:42 PM
 * @author sandarwin
 * @fp-ibi-web
 *
 * Version Control Info
 *
 * $Id: codetemplates.xml,v 1.1 2008/08/21 01:32:51 sandarwin Exp $
 * $Revision: 1.1 $
 * $Date: 2008/08/21 01:32:51 $
 */
package sg.com.kpmg.factorpro.web.ibi.common.action;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;

import sg.com.kpmg.factorpro.common.ResourceKeys;
import sg.com.kpmg.factorpro.model.YesNoInd;
import sg.com.kpmg.factorpro.model.cntl.Cntl;
import sg.com.kpmg.factorpro.model.cntl.CntlKey;
import sg.com.kpmg.factorpro.web.common.action.FactorProAction;
import sg.com.kpmg.factorpro.web.ibi.common.handler.IbiCommonActionHandler;

public class IbiCommonActionValidator
{
	private FactorProAction action;
	
	public IbiCommonActionValidator(FactorProAction action)
	{
		this.action = action;
	}
	
	public boolean validatePaymentMode(String pymtMode, String chqNo, String currentAccount, IbiCommonActionHandler handler)
	{
		// If cheque payment mode, must have cheque no. Else, cheque no. must be empty.
		Cntl chequeIndCntl = handler.getCntlByPrimaryKey(CntlKey.CTL_TYPE_F4, pymtMode, CntlKey.CTL_PARAM_CHQ_IND);
		if(YesNoInd.Y.name().equals(chequeIndCntl.getCtlValue()))
		{
			if(StringUtils.isEmpty(chqNo))
				action.addActionError( action.getText(ResourceKeys.FMU0230.name()) );
		}else
		{
			if(!StringUtils.isEmpty(chqNo))
				action.addActionError( action.getText(ResourceKeys.FMU0231.name()) );
		}
		
		String currAccMandatorPayModes = handler.getCurrentAccMandatorPayModes();
		if(currAccMandatorPayModes.contains(pymtMode) && StringUtils.isBlank(currentAccount))
		{
			if(StringUtils.isEmpty(currentAccount))
				action.addActionError( action.getText("IE00053"), new String[]{action.getText("client.current.account"), pymtMode} );
		}
		
		return action.getActionErrors().size() > 0 ? false: true;
	}
	
	// Value Date 
	//		- must not be greater than current processing date, and
	//		- must be within current month and year 
	public boolean validateValueDate(String fieldName, Calendar valueDate, Calendar currentProcessingDate)
	{
		if(valueDate.after(currentProcessingDate))
		{
			action.addFieldError(  fieldName, action.getText(ResourceKeys.FMU0195.name()) );
			return false;
		}
		else
		{
			Calendar _1stDayOfMonth = (Calendar)currentProcessingDate.clone();
			_1stDayOfMonth.set(Calendar.DATE, 1);
			
			 if(valueDate.before(_1stDayOfMonth))
			 {
				 action.addFieldError(  fieldName, action.getText(ResourceKeys.FMU0358.name()) );
				 return false;
			 }
		}
		return true;
	}

	// Effective Date 
	//		- must be within current month and year
	public boolean validateEffectiveDate(String fieldName, Calendar effectiveDate, Calendar currentProcessingDate)
	{
		Calendar _1stDayOfMonth = (Calendar)currentProcessingDate.clone();
		_1stDayOfMonth.set(Calendar.DATE, 1);
		if(effectiveDate.before(_1stDayOfMonth))
		{
			action.addFieldError(  fieldName, action.getText(ResourceKeys.FMU0320.name()) );
			return false;
		}
		return true;
	}
}
